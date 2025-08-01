import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import "./LoginPage.css";

const LoginPage = () => {
  const [isSignup, setIsSignup] = useState(false); // ✅ Toggle between Login & Signup
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setname] = useState(""); // ✅ For signup
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const response = await api.post("/api/auth/login", { email, password });
      localStorage.setItem("token", response.data.token);
      navigate("/dashboard");
    } catch (err) {
      setError(err.response?.data || "Invalid email or password");
    }
  };

  const handleSignup = async (e) => {
  e.preventDefault();
  setError("");
  try {
    await api.post("/api/auth/register", { name, email, password });
    alert("Signup successful! Please login.");
    setEmail("");
    setPassword("");
    setname("");
    setIsSignup(false); // ✅ Switch back to login form
  } catch (err) {
    setError(err.response?.data || "Signup failed, try again.");
  }
};

  return (
    <div className="login-container">
      {/* Left Section: App Info */}
      <div className="login-info">
        <h1>Track My Applications</h1>
        <p>Simplify your job application tracking and stay organized.</p>
      </div>

      {/* Right Section: Form */}
      <div className="login-form">
        <h2>{isSignup ? "Sign Up" : "Login"}</h2>
        {error && <p className="error">{error}</p>}
        <form onSubmit={isSignup ? handleSignup : handleLogin}>
          {isSignup && (
            <input
              type="text"
              placeholder="Username"
              value={name}
              onChange={(e) => setname(e.target.value)}
              required
            />
          )}
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button type="submit">{isSignup ? "Sign Up" : "Login"}</button>
        </form>
        <p>
          {isSignup ? (
            <>
              Already have an account?{" "}
              <span className="link" onClick={() => setIsSignup(false)}>Login</span>
            </>
          ) : (
            <>
              Don't have an account?{" "}
              <span className="link" onClick={() => setIsSignup(true)}>Sign Up</span>
            </>
          )}
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
