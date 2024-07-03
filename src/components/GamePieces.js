import React, { useEffect, useState } from "react";
import Canvas from "./Canvas";

const GamePieces = ({ playerName, score, setScore, onGameOver }) => {
  const SNAKE_SPEED = 10;
  const CANVAS_WIDTH = 750;
  const CANVAS_HEIGHT = 420;
  const BORDER_OFFSET = 10; // Offset to prevent items from being generated on the border
  const [apple, setApple] = useState({ x: 180, y: 100, type: "circle" }); // Tipo de comida por defecto
  const [snake, setSnake] = useState([{ x: 100, y: 50 }, { x: 95, y: 50 }]);
  const [direction, setDirection] = useState("right");
  const [powerFood, setPowerFood] = useState({ x: null, y: null, type: null });
  const [activePower, setActivePower] = useState(null);
  const [powerDuration, setPowerDuration] = useState(0);

  useEffect(() => {
    const handleKeyPress = (e) => {
      switch (e.key) {
        case "ArrowRight":
          setDirection("right");
          break;
        case "ArrowLeft":
          setDirection("left");
          break;
        case "ArrowUp":
          setDirection("up");
          break;
        case "ArrowDown":
          setDirection("down");
          break;
        default:
          break;
      }
    };
    window.addEventListener("keydown", handleKeyPress);
    return () => window.removeEventListener("keydown", handleKeyPress);
  }, []);

  useEffect(() => {
    const interval = setInterval(() => {
      moveSnake();
      updatePower();
    }, 100);
    return () => clearInterval(interval);
  }, [snake, direction, score, powerDuration]);

  useEffect(() => {
    const generatePowerFood = () => {
      if (powerFood.x === null && powerFood.y === null) {
        const newPowerFood = {
          x: Math.floor((Math.random() * (CANVAS_WIDTH - BORDER_OFFSET * 2)) / SNAKE_SPEED) * SNAKE_SPEED + BORDER_OFFSET,
          y: Math.floor((Math.random() * (CANVAS_HEIGHT - BORDER_OFFSET * 2)) / SNAKE_SPEED) * SNAKE_SPEED + BORDER_OFFSET,
          type: ["speed", "shrink", "immune"][Math.floor(Math.random() * 3)]
        };
        setPowerFood(newPowerFood);
      }
    };

    const interval = setInterval(generatePowerFood, 5000); // Generar comida especial cada 5 segundos
    return () => clearInterval(interval);
  }, [powerFood]);

  const moveSnake = () => {
    const speedMultiplier = activePower === "speed" ? 2 : 1;
    setSnake((prevSnake) => {
      const newSnake = [...prevSnake];
      const snakeHead = { ...newSnake[0] };

      switch (direction) {
        case "right":
          snakeHead.x += SNAKE_SPEED * speedMultiplier;
          break;
        case "left":
          snakeHead.x -= SNAKE_SPEED * speedMultiplier;
          break;
        case "up":
          snakeHead.y -= SNAKE_SPEED * speedMultiplier;
          break;
        case "down":
          snakeHead.y += SNAKE_SPEED * speedMultiplier;
          break;
        default:
          break;
      }

      newSnake.unshift(snakeHead);
      newSnake.pop();

      handleAppleCollision(newSnake);
      handleWallCollision(snakeHead);
      handleBodyCollision(newSnake);
      handlePowerFoodCollision(newSnake);

      return newSnake;
    });
  };

  const handlePowerFoodCollision = (newSnake) => {
    const snakeHead = newSnake[0];
    if (powerFood.x !== null && powerFood.y !== null && snakeHead.x === powerFood.x && snakeHead.y === powerFood.y) {
      setActivePower(powerFood.type);
      setPowerDuration(100);
      setPowerFood({ x: null, y: null, type: null });
    }
  };

  const handleBodyCollision = (newSnake) => {
    const snakeHead = newSnake[0];
    for (let i = 1; i < newSnake.length; i++) {
      if (snakeHead.x === newSnake[i].x && snakeHead.y === newSnake[i].y) {
        onGameOver("self");
      }
    }
  };

  const handleWallCollision = (snakeHead) => {
    if (snakeHead.x >= CANVAS_WIDTH || snakeHead.x < 0 || snakeHead.y >= CANVAS_HEIGHT || snakeHead.y < 0) {
      if (activePower !== "immune") {
        onGameOver("wall");
      }
    }
  };

  const handleAppleCollision = (newSnake) => {
    const snakeHead = newSnake[0];
    if (snakeHead.x === apple.x && snakeHead.y === apple.y) {
      setScore((prevScore) => prevScore + 1);
      setApple({
        x: Math.floor((Math.random() * (CANVAS_WIDTH - BORDER_OFFSET * 2)) / SNAKE_SPEED) * SNAKE_SPEED + BORDER_OFFSET,
        y: Math.floor((Math.random() * (CANVAS_HEIGHT - BORDER_OFFSET * 2)) / SNAKE_SPEED) * SNAKE_SPEED + BORDER_OFFSET,
        type: ["circle", "star", "triangle"][Math.floor(Math.random() * 3)] // Cambiar tipo de comida aleatoriamente
      });
      newSnake.push({
        x: newSnake[newSnake.length - 1].x,
        y: newSnake[newSnake.length - 1].y,
      });
    }
  };

  const updatePower = () => {
    if (powerDuration > 0) {
      setPowerDuration((prevDuration) => prevDuration - 1);
    } else {
      setActivePower(null);
    }
  };

  const draw = (ctx) => {
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
    drawWalls(ctx);
    drawSnake(ctx);
    drawApple(ctx);
    drawPowerFood(ctx);
    drawActivePower(ctx);
  };

  const drawWalls = (ctx) => {
    ctx.strokeStyle = "#afafaf"; /* Borde gris claro */
    ctx.lineWidth = 8;
    ctx.strokeRect(0, 0, ctx.canvas.width, ctx.canvas.height);
  };

  const drawSnake = (ctx) => {
    snake.forEach((snakePart, index) => {
      ctx.beginPath();
      ctx.rect(snakePart.x, snakePart.y, 14, 14);
      const gradient = ctx.createLinearGradient(snakePart.x, snakePart.y, snakePart.x + 14, snakePart.y + 14);
      gradient.addColorStop(0, "#1abc9c"); /* Verde claro */
      gradient.addColorStop(1, "#16a085"); /* Verde oscuro */
      ctx.fillStyle = gradient;
      ctx.shadowBlur = 10;
      ctx.shadowColor = "#1abc9c";
      ctx.fill();
      ctx.closePath();

      // Efecto de transición cuando la serpiente se mueve
      if (index === 0) {
        ctx.shadowBlur = 0;
        ctx.fillStyle = "#ffffff";
        ctx.fillRect(snakePart.x + 3, snakePart.y + 3, 8, 8);
      }
    });
  };

  const drawApple = (ctx) => {
    ctx.beginPath();
    ctx.fillStyle = "#e74c3c"; /* Rojo */
    switch (apple.type) {
      case "circle":
        ctx.arc(apple.x + 7, apple.y + 7, 7, 0, Math.PI * 2);
        break;
      case "star":
        ctx.moveTo(apple.x + 7, apple.y);
        ctx.lineTo(apple.x + 10, apple.y + 10);
        ctx.lineTo(apple.x + 14, apple.y + 10);
        ctx.lineTo(apple.x + 11, apple.y + 14);
        ctx.lineTo(apple.x + 12, apple.y + 18);
        ctx.lineTo(apple.x + 7, apple.y + 15);
        ctx.lineTo(apple.x + 2, apple.y + 18);
        ctx.lineTo(apple.x + 3, apple.y + 14);
        ctx.lineTo(apple.x, apple.y + 10);
        ctx.lineTo(apple.x + 4, apple.y + 10);
        ctx.closePath();
        break;
      case "triangle":
        ctx.moveTo(apple.x + 7, apple.y);
        ctx.lineTo(apple.x + 14, apple.y + 14);
        ctx.lineTo(apple.x, apple.y + 14);
        ctx.closePath();
        break;
      default:
        ctx.arc(apple.x + 7, apple.y + 7, 7, 0, Math.PI * 2);
        break;
    }
    ctx.fill();
    ctx.closePath();
  };

  const drawPowerFood = (ctx) => {
    if (powerFood.x !== null && powerFood.y !== null) {
      ctx.beginPath();
      ctx.fillStyle = "#f39c12"; /* Naranja */
      switch (powerFood.type) {
        case "circle":
          ctx.arc(powerFood.x + 7, powerFood.y + 7, 7, 0, Math.PI * 2);
          break;
        case "star":
          ctx.moveTo(powerFood.x + 7, powerFood.y);
          ctx.lineTo(powerFood.x + 10, powerFood.y + 10);
          ctx.lineTo(powerFood.x + 14, powerFood.y + 10);
          ctx.lineTo(powerFood.x + 11, powerFood.y + 14);
          ctx.lineTo(powerFood.x + 12, powerFood.y + 18);
          ctx.lineTo(powerFood.x + 7, powerFood.y + 15);
          ctx.lineTo(powerFood.x + 2, powerFood.y + 18);
          ctx.lineTo(powerFood.x + 3, powerFood.y + 14);
          ctx.lineTo(powerFood.x, powerFood.y + 10);
          ctx.lineTo(powerFood.x + 4, powerFood.y + 10);
          ctx.closePath();
          break;
        case "triangle":
          ctx.moveTo(powerFood.x + 7, powerFood.y);
          ctx.lineTo(powerFood.x + 14, powerFood.y + 14);
          ctx.lineTo(powerFood.x, powerFood.y + 14);
          ctx.closePath();
          break;
        default:
          ctx.arc(powerFood.x + 7, powerFood.y + 7, 7, 0, Math.PI * 2);
          break;
      }
      ctx.fill();
      ctx.closePath();
    }
  };

  const drawActivePower = (ctx) => {
    if (activePower) {
      ctx.font = "18px Arial";
      ctx.fillStyle = "#ffffff";
      ctx.fillText(`Active Power: ${activePower}`, 10, 30);
      ctx.fillText(`Duration: ${powerDuration} sec`, 10, 60);
    }
  };

  return (
    <div>
      <Canvas draw={draw} width={CANVAS_WIDTH} height={CANVAS_HEIGHT} className="gameCanvas" />
    </div>
  );
};

export default GamePieces;
