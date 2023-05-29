import React from "react";
import "../../App.css";
import Navigation from "../Navigation.js";
import MainSection from "../MainSection";

// TODO
/*
Trebuie primite e-mailul, usernameul si rolul ca argumente la 
aceasta pagina.
*/
function Main() {
  return (
    <>
      <Navigation />
      <MainSection />
    </>
  );
}

export default Main;
