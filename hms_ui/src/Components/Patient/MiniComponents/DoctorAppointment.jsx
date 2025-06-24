import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import { getAppointments } from "../../../store/Actions/DoctorAppointmentActions";
import '../../../Componentcss/Patient/TrackAppointment.css';

function DoctorAppointment() {
  const navigate = useNavigate();
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');
  const dispatch = useDispatch();
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const appointments = useSelector(state => state.doctorAppointment.patient);

  useEffect(() => {
    if (!token || role !== "PATIENT") {
      navigate("/login");
    } else {
      getAppointments(dispatch)({ page, size }, token);
    }
  }, [dispatch, navigate, token, role, page, size]);

  return (
    <div className="appointment-table-container">
      <table className="appointment-table">
        <thead>
          <tr>
            <th>S.No</th>
            <th>Date</th>
            <th>Doctor</th>
            <th>Action</th>
            <th>Status</th>
            <th>Cancel</th>
          </tr>
        </thead>
        <tbody>
          {appointments && appointments.length > 0 ? (
            appointments.map((a, index) => (
              <tr key={index}>
                <td>{index + 1}</td>
                <td>{a.date}</td>
                <td>{a.doctorName}</td>
                <td>
                  <button className="button">Edit</button>
                </td>
                <td>{a.appointmentStatus}</td>
                <td>
                  <button className="button">Cancel</button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="5" style={{ textAlign: "center" }}>No appointments found</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default DoctorAppointment;
