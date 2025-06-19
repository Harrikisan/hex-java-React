import { useEffect } from "react";
import { useSelector } from "react-redux";
import { Outlet, useNavigate } from "react-router-dom";

function PatientDashBoard() {

    const navigate = useNavigate()

    const token =localStorage.getItem('token');
    const role = localStorage.getItem('role');
    useEffect(() => {


        if (token == null || token == undefined || token == "" || role !== "PATIENT") {
            navigate("/login");
        }

    }, []);
    return (
        <div>
            <div>Patient Dashboard</div>
            <Outlet />
        </div>
    )
}

export default PatientDashBoard