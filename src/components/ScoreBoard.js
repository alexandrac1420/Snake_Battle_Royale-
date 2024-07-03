import React from "react";
import "./ScoreBoard.css"; // Asegúrate de importar tu archivo CSS donde tengas las clases definidas

const ScoreBoard = ({ playerName, score, highScore }) => (
  <div className="scoreboard-container">
    <div className="scoreboard">
      <p className="player-name">Player: {playerName}</p>
      <p className="score">Score: {score}</p>
      <p className="high-score">High Score: {highScore}</p>
    </div>
  </div>
);

export default ScoreBoard;
