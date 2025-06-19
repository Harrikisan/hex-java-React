import { useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import '../../Componentcss/Patient/BookAppointment.css'
import DoctorListing from "./MiniComponents/DoctorListing";
import BedBooking from "./MiniComponents/BedBooking";

function BookAppointment() {
    const navigate = useNavigate();
    const token = useSelector(state => state.user.token);
    const role = useSelector(state => state.user.role);
    const [category, setCategory] = useState("");

    return (
        <div>
            <div className="container">
                <div className="col-lg-12">
                    <div className="dropdown-container">
                        <label htmlFor="category" className="dropdown-label">Category</label>
                        <select
                            id="category"
                            value={category}
                            onChange={(e) => setCategory(e.target.value)}
                            className="dropdown-select"
                        >
                            <option value="">Select</option>
                            <option value="Doctor">Doctor Booking</option>
                            <option value="Bed">Bed Booking</option>
                            <option value="Test">Test Booking</option>
                        </select>
                    </div>
                </div>
                {
                    category=="Doctor"?<DoctorListing/>:""
                }
                {
                    category=="Bed"?<BedBooking/>:""
                }
            </div>
        </div>
    );

}

export default BookAppointment;
