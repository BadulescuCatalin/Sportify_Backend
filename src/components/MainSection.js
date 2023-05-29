import React from "react";
import { Link } from "react-router-dom";
import { Button } from "./Button";
import "./MainSection.css";
import TextComponent from "./TextComponent";

function MainSection() {
  const email = localStorage.getItem("email");
  const username = localStorage.getItem("username");
  const role = localStorage.getItem("role");

  return (
    <div className="main-container">
      <div className="section-container section-container1">
        <TextComponent
          textButon="Book it!"
          textMare="Do you want to reserve a  sport court?"
          textMic="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
          pathButon="/Main/Book"
        />
        <img
          style={{
            width: "300px",
            height: "300px",
            borderRadius: "20px",
            position: "relative",
            left: "-8%",
          }}
          src={require("../teren_fotbal.jpg")}
        />
      </div>

      <div className="section-container section-container2">
        <img
          style={{
            width: "300px",
            height: "300px",
            borderRadius: "20px",
            position: "relative",
            left: "8%",
          }}
          src={require("../team.jpg")}
        />
        <TextComponent
          textButon="Find a team!"
          textMare="Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit... "
          textMic="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
          pathButon="/Main/FindTeam"
        />
      </div>

      {role === "owner" && (
        <div className="section-container section-container3">
          <TextComponent
            textButon="Manage courts!"
            textMare="Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit... "
            textMic="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
            pathButon="/Main/ManageCourts"
          />
          <img
            style={{
              width: "300px",
              height: "300px",
              borderRadius: "20px",
              position: "relative",
              left: "-8%",
            }}
            src={require("../manage.jpg")}
          />
        </div>
      )}
    </div>
  );
}

export default MainSection;
