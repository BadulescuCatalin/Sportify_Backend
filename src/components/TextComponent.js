import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Button } from "./Button";
import "./TextComponent.css";

function TextComponent({ textMare, textMic, textButon, pathButon }) {
  const newPath = pathButon + "";
  return (
    <div className="texte-container">
      <p className="textMare">{textMare}</p>
      <p className="textMic">{textMic}</p>
      <Button destinationPage={newPath}>{textButon}</Button>
    </div>
  );
}

export default TextComponent;
