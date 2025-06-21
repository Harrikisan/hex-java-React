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
                        <li className={`menu-item ${isActive("/patient") ? "active" : ""}`} onClick={() => navigate("/patient")}>Dashboard</li>
                        <li className={`menu-item ${isActive("/patient/bookAppointment") ? "active" : ""}`} onClick={() => navigate("/patient/bookAppointment")}>Book Appointment</li>
                        <li className={`menu-item ${isActive("/patient/trackAppointment") ? "active" : ""}`} onClick={() => navigate("/patient/trackAppointment")}>Track Appointment</li>
                        <li className={`menu-item ${isActive("/patient/medicalRecord") ? "active" : ""}`} onClick={() => navigate("/patient/medicalRecord")}>Medical Records</li>
                        <li className={`menu-item ${isActive("/patient/appintmenthistory") ? "active" : ""}`} onClick={() => navigate("/patient/appointmenthistory")}>AppointmnetHistory</li>
                    </ul>
                );


            default:
                return (
                    <ul className="sidebar-menu">
                        <li className={`menu-item ${isActive("/") ? "active" : ""}`} onClick={() => navigate("/")}>Home</li>
                        <li className={`menu-item ${isActive("/patient/bookAppointment") ? "active" : ""}`} onClick={() => navigate("/patient/bookAppointment")}>Book Appointment</li>
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
