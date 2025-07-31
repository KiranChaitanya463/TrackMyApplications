import React, { useState } from "react";
import "./LoginPage.css"; 

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = (e) => {
    e.preventDefault();
    console.log("Email:", email, "Password:", password); 
    // Later, we'll call backend API here
  };

  return (
    <div className="login-container">
      {/* Left Section: App Info */}
      <div className="login-info">
        <h1>Track My Applications</h1>
        <p>Simplify your job application tracking and stay organized.</p>
      </div>

      {/* Right Section: Login Form */}
      <div className="login-form">
        <h2>Login</h2>
        <form onSubmit={handleLogin}>
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
          <button type="submit">Login</button>
        </form>
        <p>Don't have an account? <a href="/signup">Sign up</a></p>
      </div>
    </div>
  );
};

export default LoginPage;