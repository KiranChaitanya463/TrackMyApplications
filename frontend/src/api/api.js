import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

// ✅ Attach token only for protected endpoints
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  // Skip token for login & register endpoints
  if (token && !config.url.includes("/api/auth/login") && !config.url.includes("/api/auth/register")) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
