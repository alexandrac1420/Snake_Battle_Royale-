import React from "react";
import "./App.css";
import GameState from "./components/GameState";

function App() {
  return (
    <div className="container">
      <div className="title">
        <h1>Snake Battle Royale</h1>
      </div>
      <GameState />
    </div>
  );
}

export default App;
