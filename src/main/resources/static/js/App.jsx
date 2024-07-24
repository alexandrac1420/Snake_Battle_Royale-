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
                const optimizedGameState = JSON.parse(messageBufferRef.current);
                setGameState(optimizedGameState);
                messageBufferRef.current = "";
                console.log('Received game state:', optimizedGameState);

                if (!snakeId && optimizedGameState.snakes.length > 0) {
                    const newSnakeId = optimizedGameState.snakes[optimizedGameState.snakes.length - 1].id;
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
            socketRef.current.send(data);
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
        }, 700);

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

        if (newDirection && isValidDirectionChange(currentSnake.dir, newDirection)) {
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
        <div>
            <h1>Snake Battle Royale</h1>
            {gameState ? (
                <GameBoard gameState={gameState} />
            ) : (
                <p>Loading game...</p>
            )}
            {!isReady && <button onClick={handleReady}>Ready</button>}
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

        if (gameState && gameState.board) {
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            // Renderizar el tablero
            gameState.board.forEach((row, y) => {
                row.forEach((cell, x) => {
                    if (cell && cell.type) {
                        switch (cell.type) {
                            case 'snake':
                                ctx.fillStyle = 'green';
                                break;
                            case 'apple':
                                ctx.fillStyle = 'red';
                                break;
                            case 'powerFood':
                                ctx.fillStyle = 'blue';
                                break;
                            default:
                                ctx.fillStyle = 'white';
                        }
                        ctx.fillRect(x * 10, y * 10, 10, 10);
                    }
                });
            });
        }

        if (gameState.snakes) {
            gameState.snakes.forEach(snake => {
                ctx.fillStyle = 'green';
                snake.body.forEach(segment => {
                    ctx.fillRect(segment.x * 10, segment.y * 10, 10, 10);
                });
            });
        }

        if (gameState.apple) {
            ctx.fillStyle = 'red';
            ctx.fillRect(gameState.apple.x * 10, gameState.apple.y * 10, 10, 10);
        }

        if (gameState.powerFood) {
            ctx.fillStyle = 'blue';
            ctx.fillRect(gameState.powerFood.x * 10, gameState.powerFood.y * 10, 10, 10);
        }

    }, [gameState]);

    return (
        <canvas ref={canvasRef} width="400" height="400" style={{ border: '1px solid black' }} />
    );
};

const PlayerInfo = ({ snakeId, gameState }) => {
    const snake = gameState.snakes.find(s => s.id === snakeId);

    return (
        <div style={{ padding: '10px', border: '1px solid white', color: 'white' }}>
            <h3>Player Info</h3>
            <p><strong>Player:</strong> {snake.id}</p>
            <p><strong>Score:</strong> {snake.s}</p>
            {snake.p && (
                <React.Fragment>
                    <p><strong>Power:</strong> {snake.p}</p>
                    <p><strong>Time Left:</strong> {Math.max(snake.pd / 1000, 0)}s</p>
                </React.Fragment>
            )}
        </div>
    );
};

ReactDOM.render(<App />, document.getElementById('root'));
