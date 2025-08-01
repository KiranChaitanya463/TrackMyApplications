import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Dashboard=()=>{
    const navigate = useNavigate();

    useEffect(()=>{
        const token = localStorage.getItem("token");
        if(!token){
            navigate("/");
        }
    },[navigate]);
    return <h1> Welcome to Dashboard</h1>
}
export default Dashboard;