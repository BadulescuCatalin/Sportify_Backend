import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./LoginSection.css";
import axios from "axios";
import jwtDecode from "jwt-decode";
import Main from "./pages/Main";
import jwt from "jsonwebtoken";

export const LoginSection = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [done, setDone] = useState(false);
  const [error, setError] = useState(0);
  const secretKey =
    "D6C417240AF0429FA0DC45861F7A90B344BEEB2F3FA66328FF7B3AED0598E0D8";
  const jwt = require("jsonwebtoken");

  const login = async (e) => {
    e.preventDefault();

    await axios({
      url: "http://localhost:8080/login",
      method: "POST",
      data: {
        email: email,
        password: password,
      },
    })
      .then((res) => {
        switch (res.data) {
          case "Wrong credentials":
            setError(1);
            console.log(res.data);
            return;

          default:
            setDone(true);
            setError(0);
            const token = jwtDecode(res.data, secretKey);
            console.log(token.sub, token.role, token.username);
            localStorage.setItem("token", res.data);
            localStorage.setItem("email", token.sub);
            localStorage.setItem("username", token.username);
            localStorage.setItem("role", token.role);

            // TODO: verificare semnat
            return;
        }
      })
      .catch((err) => console.log(err));
  };

  const togglePassword = () => {
    setShowPassword(!showPassword);
  };

  if (done) {
    window.history.pushState({}, "", "/Main");
    window.location.reload();
    return;
  }
  return (
    <div className="loginSection">
      <div className="login-container">
        <h1>Log into your account</h1>
        <form className="login-form">
          <label className="labelField" for="emailLogin">
            {" "}
            E-mail{" "}
          </label>
          <input
            value={email}
            onChange={(e) => {
              setEmail(e.target.value);
            }}
            type="text"
            placeholder="Enter your e-mail"
            id="emailLogin"
            name="emailLogin"
          />
          <label className="labelField" for="passwordLogin">
            {" "}
            Password{" "}
          </label>
          <input
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
            }}
            type={showPassword ? "text" : "password"}
            placeholder="Enter your password"
            id="passwordLogin"
            name="passwordLogin"
          />
          <div className="checkbox-container">
            <input
              onClick={togglePassword}
              type="checkbox"
              id="showPasswordCheckbox"
              name="showPasswordCheckbox"
              value="show"
            />
            <label style={{ fontSize: "0.85rem" }} for="showPasswordCheckbox">
              {" "}
              Show Password
            </label>
          </div>
          <br></br>
          <button
            type="submit"
            className="btn btn--primary btn--medium"
            onClick={login}
          >
            Log in
          </button>
        </form>

        {error === 1 && (
          <div
            style={{
              alignSelf: "center",
              maxWidth: "200px",
              textAlign: "center",
            }}
          >
            <p
              style={{
                color: "red",
                alignSelf: "center",
                fontSize: "0.85rem",
              }}
            >
              Wrong credentials!{" "}
            </p>
          </div>
        )}
        <Link className="linkRegister" to={"/Register"}>
          Don't have an account? Click here to register!
        </Link>
      </div>
    </div>
  );
};
export default LoginSection;
