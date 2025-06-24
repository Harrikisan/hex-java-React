import { useState } from "react";
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
    const [category, setCategory] = useState("Doctor");
    const checkLogin = () => {


        if (token == null || token == undefined || token == "" || role !== "PATIENT") {
            navigate("/login");
        }
    }

    return (
        <div>
            <div className="container">
                <div className="col-lg-12 mb-3">
                    <div className="dropdown-container">
                        <label htmlFor="category" className="dropdown-label">Category</label>
                        <select
                            id="category"
                            value={category}
                            onChange={(e) => setCategory(e.target.value)}
                            className="dropdown-select"
                        >
                            <option value="Doctor">Doctor Appointment</option>
                            <option value="Bed">Bed Appointment</option>
                            <option value="Test">Test Appointment</option>
                        </select>
                    </div>
                </div>
                {
                    category=="Doctor"?<DoctorAppointment/>:""
                }
                {
                    category=="Bed"?<BedAppointment/>:""
                }
                {
                    category=="Test"?<TestAppointment/>:""
                }
            </div>
        </div>
    );

}

export default TrackAppointment;
