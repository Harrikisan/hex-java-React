import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { getAppointments } from "../../../store/Actions/DoctorAppointmentActions";
import '../../../Componentcss/Patient/TrackAppointment.css';

function MedicalRecordsListing() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');

  const appointments = useSelector(state => state.doctorAppointment.patient || []);

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [filteredAppointments, setFilteredAppointments] = useState([]);

  useEffect(() => {
    if (!token || role !== "PATIENT") {
      navigate("/login");
    } else {
      getAppointments(dispatch)({ page, size }, token);
    }
  }, [dispatch, navigate, token, role, page, size]);

  useEffect(() => {
    // Filter only FINISHED appointments when Redux state updates
    const finished = appointments.filter(a => a.appointmentStatus === "FINISHED");
    setFilteredAppointments(finished);
  }, [appointments]);

  return (
    <div>
      {
        filteredAppointments.length === 0 ? (
          <p style={{ textAlign: "center", marginTop: "20px" }}>No finished appointments found.</p>
        ) : (
          filteredAppointments.map((a, index) => (
            <div key={index}>
              <div className="appointment-table-container">
                <table className="appointment-table">
                  <tbody>
                    <tr>
                      <th>{index + 1}</th>
                      <th>{a.date}</th>
                      <th>{a.doctorName}</th>
                      <th><button className="button">Get Record</button></th>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          ))
        )
      }
    </div>
  );
}

export default MedicalRecordsListing;
