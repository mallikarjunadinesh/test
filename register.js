import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
// Assuming logo is available at this path
import logo from "../../assets/logo.png"; 

// Base URL for the Spring Boot backend
const API_BASE_URL = "http://localhost:8080/api/auth"; 

const RegisterPage = () => {
  // Default to subscriber as per your original code
  const [role, setRole] = useState("subscriber"); 
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    if (!username || !password) {
      setError("Please fill in all fields");
      return;
    }

    try {
      const response = await fetch(`${API_BASE_URL}/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password, role }),
      });

      // Backend returns 200 on successful registration
      if (response.ok) {
        setSuccess(`Registration successful! Redirecting to login...`);
        // Navigate to login after 1.5 seconds, as in your original logic
        setTimeout(() => navigate("/login"), 1500);
      } else {
        const errorMessage = await response.text();
        // Backend sends "Username already exists." or other errors
        setError(errorMessage || "Registration failed. Please try again.");
      }
    } catch (err) {
      // Handle network errors (e.g., backend is not running)
      console.error("Network error during registration:", err);
      setError("Could not connect to the server. Please check your backend.");
    }
  };

  const roles = ["admin", "ops", "subscriber"];

  return (
    <div className="d-flex justify-content-center align-items-center bg-light vh-100" 
         style={{ background: 'linear-gradient(to right, #ece9e6, #ffffff)' }}>
      <div className="card shadow-lg p-4 text-center" style={{ width: "700px", borderRadius: "10px", background: 'white' }}>
        <center>
          {/* Replace with a fallback image/logo if needed */}
          <img style={{ height: "75px", width: "40%" }} src={logo} alt="Logo" />
        </center>
        <h3 className="text-center text-primary mb-5">Register</h3>

        {/* Role selection buttons */}
        <div className="btn-group w-100 mb-3 justify-content-center">
          {roles.map((r) => (
            <button
              key={r}
              className={`btn btn-outline-primary text-center ${role === r ? "active" : ""}`}
              onClick={() => setRole(r)}
            >
              {r.charAt(0).toUpperCase() + r.slice(1)}
            </button>
          ))}
        </div>
        
        <form onSubmit={handleRegister}>
          {/* Username Input */}
          <div className="mb-3 text-start">
            <label className="form-label fw-semibold">Username</label>
            <input
              type="text"
              className="form-control"
              placeholder="Enter username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>

          {/* Password Input */}
          <div className="mb-4 text-start">
            <label className="form-label fw-semibold">Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="Enter password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          {/* Error & Success Messages */}
          {error && <p className="text-danger text-center py-2">{error}</p>}
          {success && <p className="text-success text-center py-2">{success}</p>}

          <button type="submit" className="btn btn-primary w-100 py-2">
            Register
          </button>
        </form>

        <div className="text-center mt-3">
          <small>
            Already have an account? (" ")
            <Link to="/" className="text-decoration-none text-primary fw-semibold">
              Login
            </Link>
          </small>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;
