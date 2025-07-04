import { useEffect, useState } from 'react';
import '../../../Componentcss/Patient/BookAppointment.css';
import { getAppointments, getAppointmentsByDoctor } from '../../../store/Actions/DoctorAppointmentActions';
import { useDispatch, useSelector } from 'react-redux';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Appointment() {
  const [category, setCategory] = useState('APPOROVED');
  const [filterDate, setFilterDate] = useState('');
  const [appointments, setAppointments] = useState([]);
  const [statusMap, setStatusMap] = useState({});
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(100);

  const navigate = useNavigate();
  const allAppointments = useSelector(state => state.doctorAppointment.appointments);
  const dispatch = useDispatch();
  const token = useSelector(state => state.user.token);

  useEffect(() => {
    getAppointments(dispatch)({ page, size }, token);
  }, [dispatch, token, page, size]);

  useEffect(() => {
    const filtered = allAppointments.filter(
      (a) =>
        a.appointmentStatus === category &&
        (!filterDate || a.date === filterDate) // If filter date is not selected it wont filter
    );

    setAppointments(filtered);
  }, [allAppointments, category, filterDate]);

  const editStatus = async (id, status) => {
    try {
      const response = await axios.put(
        `http://localhost:8080/api/doctor/appointment/editStatus/${id}`,
        {},
        {
          params: { status },
          headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
          }
        }
      );
      console.log("Updated:", response.data);
      await getAppointments(dispatch)({ page, size }, token);
    } catch (error) {
      console.error("Error updating status:", error);
    }
  };

  return (
    <div className="container">
      <div className="col lg-12">
        <div className="filters">
          <div className="dropdown-container">
            <label htmlFor="category" className="dropdown-label">Filter by Status</label>
            <select
              id="category"
              value={category}
              onChange={(e) => setCategory(e.target.value)}
              className="dropdown-select"
            >
              <option value="APPOROVED">Approved</option>
              <option value="FINISHED">Finished</option>
              <option value="WAITING">Waiting</option>
              <option value="CANCELED">Canceled</option>
            </select>
          </div>

          <div className="dropdown-container mt-3">
            <label htmlFor="dateFilter" className="dropdown-label">Filter by Date</label>
            <input
              type="date"
              id="dateFilter"
              className="dropdown-select"
              value={filterDate}
              onChange={(e) => setFilterDate(e.target.value)}
            />
          </div>
        </div>
      </div>

      <div className="appointment-table-container">
        <table className="appointment-table">
          <thead>
            <tr>
              <th>S.No</th>
              <th>Date</th>
              <th>Doctor</th>
              <th>Status</th>
              {!(category === "FINISHED" || category === "CANCELED") ? <th>Cancel</th> : ""}
            </tr>
          </thead>
          <tbody>
            {appointments.length > 0 ? (
              appointments.map((a, index) => {
                const appointmentId = a.id ?? a.appointmentId;
                return (
                  <tr key={appointmentId || index}>
                    <td>{index + 1}</td>
                    <td>{a.date}</td>
                    <td>{a.doctorName}</td>
                    <td>{a.appointmentStatus}</td>
                    {!(category === "FINISHED" || category === "CANCELED") ?
                      <td>
                        <button className='button' onClick={() => editStatus(a.appointmentId, "CANCELED")}>Cancel</button>
                      </td> : ""}
                  </tr>
                );
              })
            ) : (
              <tr>
                {"No appointments found"}
              </tr>
            )}
          </tbody>
        </table>

      </div>
    </div>
  );
}

export default Appointment;
