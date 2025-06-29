import { useNavigate, useLocation } from 'react-router-dom';
import '../../Componentcss/Basic/Sidebar.css';
import { useSelector } from 'react-redux';
import { useEffect, useState } from 'react';

function Sidebar() {
    const [role, setRole] = useState("")
    const navigate = useNavigate();
    const location = useLocation();

    const isActive = (path) => location.pathname === path;

    useEffect(() => {
        setRole(localStorage.getItem('role'))
    })

    const getSidebar = () => {
        switch (role) {
            case "PATIENT":
                return (
                    <ul className="sidebar-menu">
                        <li className={`menu-item ${isActive("/patient") ? "active" : ""}`} 
                        onClick={() => navigate("/patient")}>Dashboard</li>
                        <li className={`menu-item ${isActive("/patient/bookAppointment") ? "active" : ""}`} 
                        onClick={() => navigate("/patient/bookAppointment")}>Book Appointment</li>
                        <li className={`menu-item ${isActive("/patient/trackAppointment") ? "active" : ""}`} 
                        onClick={() => navigate("/patient/trackAppointment")}>Track Appointment</li>
                        <li className={`menu-item ${isActive("/patient/medicalRecord") ? "active" : ""}`} 
                        onClick={() => navigate("/patient/medicalRecord")}>Medical Records</li>
                    </ul>
                );

            case "DOCTOR":
                return (
                    <ul className="sidebar-menu">
                        <li className={`menu-item ${isActive("/doctor") ? "active" : ""}`} 
                        onClick={() => navigate("/doctor")}>Dashboard</li>
                        <li className={`menu-item ${isActive("/doctor/appointments") ? "active" : ""}`} 
                        onClick={() => navigate("/doctor/appointments")}>Appointments</li>
                        <li className={`menu-item ${isActive("/doctor/edieSchedule") ? "active" : ""}`} 
                        onClick={() => navigate("/doctor/edieSchedule")}>Edit Schedule</li>
                        <li className={`menu-item ${isActive("/doctor/mypatients") ? "active" : ""}`} 
                        onClick={() => navigate("/doctor/mypatients")}>Mypatients</li>
                        <li className={`menu-item ${isActive("/doctor/todaysAppointments") ? "active" : ""}`} 
                        onClick={() => navigate("/doctor/todaysAppointments")}>TodaysAppointments</li>
                        
                    </ul>
                );

            case "ADMIN":
                return (
                    <ul className="sidebar-menu">
                        <li className={`menu-item ${isActive("/admin/addDoctor") ? "active" : ""}`} 
                        onClick={() => navigate("/admin/addDoctor")}>Add Doctor</li>
                        <li className={`menu-item ${isActive("/admin/addLab") ? "active" : ""}`} 
                        onClick={() => navigate("/admin/addLab")}>Add Lab</li>
                        <li className={`menu-item ${isActive("/admin/AddTest") ? "active" : ""}`} 
                        onClick={() => navigate("/admin/AddTest")}>Add test</li>
                        <li className={`menu-item ${isActive("/admin/addWard") ? "active" : ""}`} 
                        onClick={() => navigate("/admin/addWard")}>Add Ward</li>
                        <li className={`menu-item ${isActive("/admin/AddBed") ? "active" : ""}`} 
                        onClick={() => navigate("/admin/AddBed")}>Add Bed</li>
                    </ul>
                );

            default:
                return (
                    <ul className="sidebar-menu">
                    </ul>
                );
        }
    };

    return (
        <div className="sidebar">
            {getSidebar()}
        </div>
    );
}

export default Sidebar;
