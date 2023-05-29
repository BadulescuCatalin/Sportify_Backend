import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./RegisterSection.css";
import axios from "axios";

const ERRORS = [
  "",
  "All fields must be completed",
  "Password must be between 8 and 15 characters long",
  "The 2 passwords must be the same",
  "Email format is invalid",
  "Email already used",
  "Username already used",
];

export const RegisterSection = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [role, setRole] = useState("client");
  const [error, setError] = useState(0);
  const [done, setDone] = useState(false);

  const authenticate = async (e) => {
    e.preventDefault();

    console.log(username);
    console.log(email);
    console.log(password);
    console.log(confirmPassword);
    console.log(role);

    if (!username || !email || !password || !confirmPassword) {
      // Eroare de FE -> cel putin un camp e null
      setError(1);
      return;
    }

    if (password.length < 8 || password.length > 15) {
      // Parola sub/peste limita de lungime
      setError(2);
      return;
    }

    if (password != confirmPassword) {
      // Mismatch de parole
      setError(3);
      return;
    }

    if (
      !email.includes("@", 1) ||
      !(email.length >= 3) ||
      email.endsWith("@")
    ) {
      setError(4);
      return;
    }

    setError(0);
    //console.log("Before HTTP");

    await axios({
      url: "http://localhost:8080/accounts",
      method: "POST",
      data: {
        userName: username,
        email: email,
        password: password,
        role: role,
      },
    })
      .then((res) => {
        console.log(res);
        switch (res.data) {
          case "User registered":
            setDone(true);
            return;
          case "Email already used":
            setError(5);
            return;
          case "Username already used":
            setError(6);
            return;

          default:
            return;
        }
      })
      .catch((err) => console.log(err));

    console.log("After HTTP");
  };

  return (
    <div className="registerSection">
      <div className="register-container">
        <h1>Create a new account</h1>
        <form
          className="register-form"
          style={{
            display: done ? "none" : "auto",
          }}
        >
          <label className="labelField" for="usernameRegister">
            {" "}
            Username{" "}
          </label>
          <input
            value={username}
            onChange={(e) => {
              setUsername(e.target.value);
            }}
            type="text"
            placeholder="Choose your username"
            id="usernameRegister"
            name="usernameRegister"
          />

          <label className="labelField" for="emailRegister">
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
            id="emailRegister"
            name="emailRegister"
          />

          <label className="labelField" for="passwordRegister">
            {" "}
            Password{" "}
          </label>
          <input
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
            }}
            type="password"
            placeholder="Enter your password"
            id="passwordRegister"
            name="passwordRegister"
          />

          <label className="labelField" for="confirmPasswordRegister">
            {" "}
            Confirm Password{" "}
          </label>
          <input
            value={confirmPassword}
            onChange={(e) => {
              setConfirmPassword(e.target.value);
            }}
            type="password"
            placeholder="Rewrite your password"
            id="confirmPasswordRegister"
            name="confirmPasswordRegister"
          />

          <label className="labelField" for="roleRegister">
            {" "}
            Choose your account type{" "}
          </label>
          <select
            defaultValue={"client"}
            onChange={(e) => setRole(e.target.value)}
            id="cars"
            name="cars"
          >
            <option value="client">Client</option>
            <option value="owner">Owner</option>
          </select>

          <div className="checkbox-container"></div>
          <br></br>

          {done === false && (
            <button
              type="submit"
              className="btn btn--primary btn--medium"
              onClick={authenticate}
            >
              Register
            </button>
          )}

          {error !== 0 && (
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
                {ERRORS[error]}{" "}
              </p>
            </div>
          )}
        </form>
        {done && (
          <>
            <div
              style={{
                alignSelf: "center",
                maxWidth: "200px",
                textAlign: "center",
              }}
            >
              <p
                style={{
                  color: "green",
                  alignSelf: "center",
                  fontSize: "0.85rem",
                }}
              >
                Account succesfully registered!
              </p>
            </div>

            <Link style={{ alignSelf: "center" }} to="/Login">
              <button type="submit" className="btn btn--primary btn--medium">
                Go to Login
              </button>
            </Link>
          </>
        )}
        <Link
          className="linkRegister"
          to={"/Login"}
          style={{
            display: done ? "none" : "auto",
          }}
        >
          Already have an account? Click here to log in!
        </Link>
      </div>
    </div>
  );
};
export default RegisterSection;
