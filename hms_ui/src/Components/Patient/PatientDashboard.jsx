import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Outlet, useNavigate } from "react-router-dom";
import { getAppointments } from "../../store/Actions/DoctorAppointmentActions";

function PatientDashBoard() {
    const navigate = useNavigate();

    const token = useSelector(state => state.user.token);
    const role = useSelector(state => state.user.role);
    const login = useSelector(state => state.user.login);
    const [appointments, setAppointments] = useState([]);
    const dispatch = useDispatch();

    const allAppointments = useSelector(state => state.doctorAppointment.appointments);

    useEffect(() => {
        if (!login || role !== "PATIENT") {
            navigate("/login");
        } else {
            getAppointments(dispatch)({ page: 0, size: 100 }, token);
            setAppointments(allAppointments);
        }
    }, [dispatch]);

    const uniqueDoctors = [];
    const seenDoctorIds = new Set();

    for (const appt of appointments) {
        if (appt.doctorId && !seenDoctorIds.has(appt.doctorId)) {
            uniqueDoctors.push(appt);
            seenDoctorIds.add(appt.doctorId);
        }
    }

    return (
        <div className="container">
            <div className="appointment-table-container">
                <h2>Previously booked Doctors</h2>
                <table className="appointment-table">
                    <thead>
                        <tr>
                            <th>Doctor</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {uniqueDoctors.map((a, index) => (
                            <tr key={index}>
                                <td>{a.doctorName}</td>
                                <td>
                                    <button onClick={() => navigate(`/patient/doctorBooking/${a.doctorId}`)}>
                                        Book
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            <div className="mt-4">
                <button className="button" onClick={() => navigate("/patient/bookAppointment")}>
                    Book doctor consultation / Bed / Test Appointment
                </button>
            </div>

            <Outlet />
        </div>
    );
}

export default PatientDashBoard;
