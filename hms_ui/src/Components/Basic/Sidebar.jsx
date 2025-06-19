import { useNavigate, useLocation } from 'react-router-dom';
import '../../Componentcss/Basic/Sidebar.css';
import { useSelector } from 'react-redux';
import { useEffect } from 'react';

function Sidebar() {
    const token=localStorage.getItem('token');
    const role =localStorage.getItem('role');
    const navigate = useNavigate();
    const location = useLocation();

    const isActive = (path) => location.pathname === path;

    useEffect(()=>{
     if(token!=null || token!=undefined){
        
     }   
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
                        <li className={`menu-item ${isActive("/patient/editPersonalInfo") ? "active" : ""}`} onClick={() => navigate("/patient/editPersonalInfo")}>Edit Personal Info</li>
                    </ul>
                );
            

            default:
                return <p>No Sidebar available for this role</p>;
        }
    };

    return (
        <div className="sidebar">
            {getSidebar()}
        </div>
    );
}

export default Sidebar;
