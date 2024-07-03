import React, { useState, useEffect } from "react";
import GamePieces from "./GamePieces";
import ScoreBoard from "./ScoreBoard";
import GameOverMessage from "./GameOverMessage";

const GameState = () => {
  const [playerName, setPlayerName] = useState("Jugador1");
  const [score, setScore] = useState(0);
  const [highScore, setHighScore] = useState(parseInt(localStorage.getItem("highScore")) || 0);
  const [gameOver, setGameOver] = useState(false);
  const [collision, setCollision] = useState("");

  const handleGameOver = (type) => {
    setGameOver(true);
    if (score > highScore) {
      setHighScore(score);
      localStorage.setItem("highScore", score.toString());
    }
    setCollision(type);
  };

  const handleResetGame = () => {
    setScore(0);
    setGameOver(false);
  };

  useEffect(() => {
    const handleKeyPress = (e) => {
      if (gameOver && e.key === "Enter") {
        handleResetGame();
      }
    };
    window.addEventListener("keydown", handleKeyPress);
    return () => window.removeEventListener("keydown", handleKeyPress);
  }, [gameOver]);

  return (
    <div className="container">
      <ScoreBoard playerName={playerName} score={score} highScore={highScore} />
      {gameOver ? (
        <GameOverMessage collision={collision} onReset={handleResetGame} />
      ) : (
        <GamePieces playerName={playerName} score={score} setScore={setScore} onGameOver={handleGameOver} />
      )}
    </div>
  );
};

export default GameState;
