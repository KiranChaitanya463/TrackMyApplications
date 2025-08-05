import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import "./LoginPage.css";

const LoginPage = () => {
  const [isSignup, setIsSignup] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const validateInputs = () => {
    if (!email.includes("@")) return "Please enter a valid email.";
    if (password.length < 6) return "Password must be at least 6 characters.";
    if (isSignup && name.trim().length < 3) return "Name must be at least 3 characters.";
    return null;
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");
    const validationError = validateInputs();
    if (validationError) return setError(validationError);

    try {
      const response = await api.post("/api/auth/login", { email, password });
      localStorage.setItem("token", response.data.token); //  Store fresh token
      navigate("/dashboard");
    } catch (err) {
      setError(err.response?.data?.message || "Invalid email or password");
    }
  };

  const handleSignup = async (e) => {
    e.preventDefault();
    setError("");
    const validationError = validateInputs();
    if (validationError) return setError(validationError);

    try {
      await api.post("/api/auth/register", { name, email, password });
      alert("Signup successful! Please login.");
      setName(""); setEmail(""); setPassword(""); setIsSignup(false);
    } catch (err) {
      setError(err.response?.data?.message || "Signup failed, try again.");
    }
  };

  return (
    <div className="login-container">
      <div className="login-info">
        <h1>Track My Applications</h1>
        <p>Simplify your job application tracking and stay organized.</p>
      </div>
      <div className="login-form">
        <h2>{isSignup ? "Sign Up" : "Login"}</h2>
        {error && <p className="error">{error}</p>}
        <form onSubmit={isSignup ? handleSignup : handleLogin}>
          {isSignup && (
            <input type="text" placeholder="Username" value={name} onChange={(e) => setName(e.target.value)} required />
          )}
          <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
          <button type="submit">{isSignup ? "Sign Up" : "Login"}</button>
        </form>
        <p>
          {isSignup ? (
            <>Already have an account? <span className="link" onClick={() => setIsSignup(false)}>Login</span></>
          ) : (
            <>Don't have an account? <span className="link" onClick={() => setIsSignup(true)}>Sign Up</span></>
          )}
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
