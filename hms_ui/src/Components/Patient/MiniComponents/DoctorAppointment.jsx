import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios"
import { getAppointments } from "../../../store/Actions/DoctorAppointmentActions";
import '../../../Componentcss/Patient/TrackAppointment.css'
function DoctorAppointment() {

  const navigate = useNavigate()
  const token =localStorage.getItem('token');
    const role = localStorage.getItem('role');
  const dispatch = useDispatch()
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

  console.log(appointments); // appointments will automatically update when Redux state updates

  return (
    <div>
      
      {
        appointments.map((a,index) => (
          <div key={index}>
            <div className="appointment-table-container">
              <table className="appointment-table">
                <tbody>
                  <tr>
                    <th>{index+1}</th>
                    <th>{a.date}</th>
                    <th>{a.doctorName}</th>
                    <th><button className="button">Edit</button></th>
                    <th>{a.appointmentStatus}</th>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        ))
      }
    </div>

  );
}

export default DoctorAppointment