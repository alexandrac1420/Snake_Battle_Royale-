const { useState, useEffect, useRef } = React;

const App = () => {
    const [gameState, setGameState] = useState(null);
    const [snakeId, setSnakeId] = useState(null);
    const [snakeDirection, setSnakeDirection] = useState(null);
    const [isReady, setIsReady] = useState(false);
    const socketRef = useRef(null);
    const messageBufferRef = useRef("");

    function WShostURL() {
        var host = window.location.host;
        var url = 'ws://' + host;
        console.log("WebSocket URL Calculada: " + url);
        return url;
    }

    useEffect(() => {
        const wsUrl = WShostURL() + '/game';

        socketRef.current = new WebSocket(wsUrl);
        socketRef.current.onopen = () => {
            console.log('WebSocket connection established');
        };

        socketRef.current.onmessage = (event) => {
            messageBufferRef.current += event.data;
            try {
                const updatedGameState = JSON.parse(messageBufferRef.current);
                setGameState(updatedGameState);
                messageBufferRef.current = "";
                console.log('Received game state:', updatedGameState);

                // Solo establece el snakeId si aún no está establecido
                if (!snakeId && updatedGameState.snakes.length > 0) {
                    const newSnakeId = updatedGameState.snakes[updatedGameState.snakes.length - 1].id;
                    setSnakeId(newSnakeId);
                }
            } catch (e) {
                console.log('Waiting for more message fragments...');
            }
        };

        socketRef.current.onclose = (event) => {
            console.log('WebSocket connection closed:', event.reason);
        };

        socketRef.current.onerror = (error) => {
            console.log('WebSocket error:', error);
        };

        return () => {
            socketRef.current.close();
        };
    }, []);

    const sendGameUpdate = (moveRequest) => {
        if (socketRef.current && socketRef.current.readyState === WebSocket.OPEN) {
            console.log('Sending game state to server:', moveRequest);
            const data = JSON.stringify(moveRequest);
            const CHUNK_SIZE = 4096;
            for (let i = 0; i < data.length; i += CHUNK_SIZE) {
                socketRef.current.send(data.slice(i, i + CHUNK_SIZE));
            }
        }
    };

    const moveSnake = () => {
        if (!gameState || !snakeDirection || !snakeId) return;

        const moveRequest = {
            snakeId: snakeId,
            direction: snakeDirection
        };

        sendGameUpdate(moveRequest);
    };

    useEffect(() => {
        const interval = setInterval(() => {
            moveSnake();
        }, 100);

        return () => clearInterval(interval);
    }, [gameState, snakeDirection, snakeId]);

    const handleKeyDown = (e) => {
        if (!gameState || !snakeId) return;

        const currentSnake = gameState.snakes.find(snake => snake.id === snakeId);
        if (!currentSnake) return;

        let newDirection;
        switch (e.key) {
            case 'ArrowUp':
                newDirection = 'UP';
                break;
            case 'ArrowDown':
                newDirection = 'DOWN';
                break;
            case 'ArrowLeft':
                newDirection = 'LEFT';
                break;
            case 'ArrowRight':
                newDirection = 'RIGHT';
                break;
            default:
                return;
        }

        if (newDirection && isValidDirectionChange(currentSnake.lastValidDirection, newDirection)) {
            setSnakeDirection(newDirection);
        }
    };

    const isValidDirectionChange = (currentDirection, newDirection) => {
        return !((currentDirection === "UP" && newDirection === "DOWN") ||
                 (currentDirection === "DOWN" && newDirection === "UP") ||
                 (currentDirection === "LEFT" && newDirection === "RIGHT") ||
                 (currentDirection === "RIGHT" && newDirection === "LEFT"));
    };

    useEffect(() => {
        window.addEventListener('keydown', handleKeyDown);
        return () => window.removeEventListener('keydown', handleKeyDown);
    }, [gameState]);

    const handleReady = () => {
        setIsReady(true);
        sendGameUpdate({ snakeId, ready: true });
    };

    return (
        <div style={{ textAlign: 'center', backgroundColor: '#000', minHeight: '100vh', color: '#fff', fontFamily: 'Press Start 2P, cursive' }}>
            <h1 style={{ color: '#ffcc00', textShadow: '2px 2px #000' }}>Snake Battle Royale</h1>
            {gameState ? (
                <GameBoard gameState={gameState} />
            ) : (
                <p>Loading game...</p>
            )}
            {!isReady && <button onClick={handleReady} style={{ padding: '10px 20px', backgroundColor: '#ffcc00', color: '#000', border: 'none', cursor: 'pointer', fontFamily: 'Press Start 2P, cursive' }}>Ready</button>}
            {snakeId && <PlayerInfo snakeId={snakeId} gameState={gameState} />}
        </div>
    );
};

const GameBoard = ({ gameState }) => {
    const canvasRef = React.useRef(null);

    React.useEffect(() => {
        console.log("Current game state in GameBoard:", gameState);
        const canvas = canvasRef.current;
        const ctx = canvas.getContext('2d');
        const cellSize = canvas.width / gameState.boardWidth;

        if (gameState && gameState.snakes) {
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            gameState.snakes.forEach(snake => {
                if (Array.isArray(snake.body)) {
                    snake.body.forEach((segment, index) => {
                        ctx.fillStyle = index === 0 ? '#FFD700' : 'green'; // Head in gold, body in green
                        ctx.fillRect(segment.x * cellSize, segment.y * cellSize, cellSize, cellSize);
                    });
                }
            });

            if (gameState.apple) {
                ctx.fillStyle = 'red';
                ctx.beginPath();
                ctx.arc((gameState.apple.x + 0.5) * cellSize, (gameState.apple.y + 0.5) * cellSize, cellSize / 2, 0, 2 * Math.PI);
                ctx.fill();
            }

            if (gameState.powerFood && gameState.powerFood.type) {
                ctx.fillStyle = 'blue';
                ctx.beginPath();
                ctx.arc((gameState.powerFood.x + 0.5) * cellSize, (gameState.powerFood.y + 0.5) * cellSize, cellSize / 2, 0, 2 * Math.PI);
                ctx.fill();
            }
        }
    }, [gameState]);

    return (
        <div style={{ display: 'flex', justifyContent: 'center', margin: '20px 0' }}>
            <canvas ref={canvasRef} width="750" height="420" style={{ border: '3px solid #ffcc00', boxShadow: '0 0 10px #ffcc00' }} />
            {gameState && gameState.gameOver && (
                <div style={{ position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', color: '#ffcc00', textShadow: '2px 2px #000' }}>
                    <h2>Game Over! Winner is {gameState.winner}</h2>
                </div>
            )}
        </div>
    );
};

const PlayerInfo = ({ snakeId, gameState }) => {
    const snake = gameState.snakes.find(s => s.id === snakeId);

    return (
        <div id="player-info" style={{ position: 'absolute', top: '20px', right: '20px', padding: '10px', backgroundColor: 'rgba(0, 0, 0, 0.7)', border: '2px solid #fff', color: '#fff', fontFamily: 'Press Start 2P, cursive', textAlign: 'left' }}>
            <h3>Player Info</h3>
            <p><strong>Score:</strong> {snake.score}</p>
            {snake.activePower && (
                <React.Fragment>
                    <p><strong>Power:</strong> {snake.activePower}</p>
                    <p><strong>Time Left:</strong> {Math.max(snake.powerDuration / 1000, 0)}s</p>
                </React.Fragment>
            )}
        </div>
    );
};

ReactDOM.render(<App />, document.getElementById('root'));
