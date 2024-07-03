import React from "react";
import "./gameOver.css";

const GameOverMessage = ({ collision, onReset }) => (
  <div className="game-over">
    <p>Game Over!</p>
    <p>{collision === "wall" ? "You hit the wall" : "You ate yourself"}</p>
    <p>Please press Enter to reset the game</p>
  </div>
);

export default GameOverMessage;
