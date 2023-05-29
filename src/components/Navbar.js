import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Button } from "./Button";
import "./Navbar.css";

function Navbar() {
  const [click, setClick] = useState(false);
  const [button, setButton] = useState(true);

  const handleClick = () => setClick(!click);
  const closeMobileMenu = () => setClick(false);

  const showButton = () => {
    if (window.innerWidth <= 960) {
      setButton(false);
    } else {
      setButton(true);
    }
  };

  window.addEventListener("resize", showButton);

  useEffect(() => {
    showButton();
  }, []);

  return (
    <div>
      <>
        <nav className="navbar">
          <div className="navbar-container">
            <Link to="/" className="navbar-logo" onClick={closeMobileMenu}>
              <img
                style={{ width: "100px", height: "100px" }}
                src={require("../sportify_logo.png")}
                alt=""
              />{" "}
              SPORT
            </Link>
            <div className="menu-icon" onClick={handleClick}>
              <i className={click ? "fas fa-times" : "fas fa-bars"} />
            </div>
            <ul className={click ? "nav-menu active" : "nav-menu"}>
              <li className="nav-item">
                <Link to="/" className="nav-links" onClick={closeMobileMenu}>
                  Home
                </Link>
              </li>
              <li className="nav-item">
                <Link
                  to="/Login"
                  className="nav-links-mobile"
                  onClick={closeMobileMenu}
                >
                  Login
                </Link>
              </li>
              <li className="nav-item">
                <Link
                  to="/Register"
                  className="nav-links-mobile"
                  onClick={closeMobileMenu}
                >
                  Register
                </Link>
              </li>
            </ul>
            {button && (
              <Button destinationPage="/Login" buttonStyle="btn--outline">
                {" "}
                Login{" "}
              </Button>
            )}
            {button && (
              <Button destinationPage="/Register" buttonStyle="btn--outline">
                {" "}
                Register{" "}
              </Button>
            )}
          </div>
        </nav>
      </>
    </div>
  );
}

export default Navbar;
