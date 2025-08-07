import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import "./Dashboard.css";

const Dashboard = () => {
  const navigate = useNavigate();
  const [applications, setApplications] = useState([]);
  const [userName, setUserName] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
  const [currentApp, setCurrentApp] = useState(null);

  const [formData, setFormData] = useState({
    companyName: "",
    jobTitle: "",
    jobLocation: "",
    jobType: "",
    applicationLink: "",
    appliedDate: "",
    status: "",
    skills: "",
    notes: ""
  });

  //  Fetch apps and user info
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/");
      return;
    }
    fetchApplications();
    fetchUserInfo();
  }, [navigate]);

  const fetchApplications = async () => {
  console.log("🔄 Starting fetchApplications...");
  console.log("Token in localStorage:", localStorage.getItem("token"));

  try {
    const response = await api.get("/api/jobs"); //  No need to pass headers manually (interceptor handles it) 
    setApplications(response.data);
  } catch (err) {
    setApplications([]);
  }
};

  const fetchUserInfo = async () => {
    try {
      const response = await api.get("/api/auth/me", {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });
      setUserName(response.data); // Directly set the name (plain string)
    } catch (err) {
      setUserName("User");
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  const handleFormSubmit = async (e) => {
  e.preventDefault();

  const payload = {
    ...formData,
    appliedDate: formData.appliedDate + "T00:00:00" //  Convert date properly
  };

  try {
    if (currentApp) {
      await api.put(`/api/jobs/${currentApp.id}`, payload, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });
    } else {
      await api.post("/api/jobs", payload, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });
    }
    setShowForm(false);
    fetchApplications();
  } catch (err) {
    console.error("Error saving application:", err.response?.data || err);
  }
};

  const handleEdit = (app) => {
    setCurrentApp(app);
    setFormData(app);
    setShowForm(true);
  };

  const handleDelete = async () => {
    try {
      await api.delete(`/api/jobs/${currentApp.id}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });
      setShowDeleteConfirm(false);
      fetchApplications();
    } catch (err) {
      console.error("Error deleting application:", err);
    }
  };

  return (
    <div className="dashboard-container">
      {/*  Navbar */}
      <div className="dashboard-navbar">
        <h2>Welcome, {userName}</h2>
        <button onClick={handleLogout}>Logout</button>
      </div>

      <div className="dashboard-main">
        {/*  Left Section */}
        <div className="dashboard-left">
          <button className="add-btn" onClick={() => { 
            setCurrentApp(null); 
            setFormData({ companyName: "", jobTitle: "", jobLocation: "", jobType: "", applicationLink: "", appliedDate: "", status: "", skills: "", notes: "" });
            setShowForm(true); 
          }}>
            + Add Application
          </button>
          <div className="user-details">
            <h3>User Details</h3>
            <p><strong>Name:</strong> {userName}</p>
          </div>
        </div>

        {/* ✅ Right Section */}
        <div className="dashboard-right">
          <h3>Applications</h3>

          {/* ✅ Table Header */}
          <div className="applications-header">
            <span>Company</span>
            <span>Title</span>
            <span>Location</span>
            <span>Type</span>
            <span>Status</span>
            <span>Applied Date</span>
            <span>Skills</span>
            <span>Notes</span>
            <span>Link</span>
            <span>Actions</span>
          </div>

          {/* ✅ Application Rows */}
          <div className="applications-list">
            {applications.map((app) => (
              <div key={app.id} className="application-row">
                <span>{app.companyName}</span>
                <span>{app.jobTitle}</span>
                <span>{app.jobLocation}</span>
                <span>{app.jobType}</span>
                <span>{app.status}</span>
                <span>{app.appliedDate.split("T")[0]}</span>
                <span>{app.skills}</span>
                <span>{app.notes}</span>
                <span>
                  <a href={app.applicationLink} target="_blank" rel="noreferrer">
                    Link
                  </a>
                </span>
                <span className="row-actions">
                  <button className="icon-btn edit-btn" onClick={() => handleEdit(app)}>✎</button>
                  <button className="icon-btn delete-btn" onClick={() => { setCurrentApp(app); setShowDeleteConfirm(true); }}>🗑</button>
                </span>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/*  Popup Form */}
      {showForm && (
        <div className="popup-overlay">
          <div className="popup">
            <h3>{currentApp ? "Edit Application" : "Add Application"}</h3>
            <form onSubmit={handleFormSubmit}>
              <input type="text" placeholder="Company" value={formData.companyName} onChange={(e) => setFormData({ ...formData, companyName: e.target.value })} required />
              <input type="text" placeholder="Job Title" value={formData.jobTitle} onChange={(e) => setFormData({ ...formData, jobTitle: e.target.value })} required />
              <input type="text" placeholder="Location" value={formData.jobLocation} onChange={(e) => setFormData({ ...formData, jobLocation: e.target.value })} required />
              <input type="text" placeholder="Job Type" value={formData.jobType} onChange={(e) => setFormData({ ...formData, jobType: e.target.value })} required />
              <input type="url" placeholder="Application Link" value={formData.applicationLink} onChange={(e) => setFormData({ ...formData, applicationLink: e.target.value })} required />
              <input type="date" value={formData.appliedDate} onChange={(e) => setFormData({ ...formData, appliedDate: e.target.value })} required />
              <input type="text" placeholder="Status" value={formData.status} onChange={(e) => setFormData({ ...formData, status: e.target.value })} required />
              <input type="text" placeholder="Skills" value={formData.skills} onChange={(e) => setFormData({ ...formData, skills: e.target.value })} />
              <textarea placeholder="Notes" value={formData.notes} onChange={(e) => setFormData({ ...formData, notes: e.target.value })}></textarea>
              <div className="popup-buttons">
                <button type="submit">Save</button>
                <button type="button" onClick={() => setShowForm(false)}>Cancel</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/*  Delete Confirmation */}
      {showDeleteConfirm && (
        <div className="popup-overlay">
          <div className="popup">
            <h3>Are you sure you want to delete?</h3>
            <div className="popup-buttons">
              <button onClick={handleDelete}>Yes</button>
              <button onClick={() => setShowDeleteConfirm(false)}>No</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
