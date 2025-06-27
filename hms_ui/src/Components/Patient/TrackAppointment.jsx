import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import '../../Componentcss/Patient/BookAppointment.css'
import DoctorAppointment from "./MiniComponents/DoctorAppointment";
import BedAppointment from "./MiniComponents/BedAppointment";
import TestAppointment from "./MiniComponents/TestAppointment";

function TrackAppointment() {
    const navigate = useNavigate();
    const token = useSelector(state => state.user.token);
    const role = useSelector(state => state.user.role);
    const login=useSelector(state=>state.user.login);
    useEffect(()=>{
        checkLogin();
    },[])
    const checkLogin = () => {
        if ( !login|| role !== "PATIENT") {
            navigate("/login");
        }
    }

    return (
        <div>
            <div className="container">
                <div className="col-lg-12 mb-3">
                    
                </div>
                    <h2 style={{color:'#4A628A'}}>Doctor Appointments</h2>
                    <DoctorAppointment />
                
                    <h2 style={{color:'#4A628A'}}>Bed Appointments</h2>
                    <BedAppointment/>
                
                    <h2 style={{color:'#4A628A'}}>Test Appointments</h2>
                    <TestAppointment/>
                
            </div>
        </div>
    );

}

export default TrackAppointment;
