import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import Dashboard from "./pages/Dashboard";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/dashboard" element = {<Dashboard />} />
        {/* Signup and Dashboard routes will be added later */}
      </Routes>
    </Router>
  );
}

export default App;
