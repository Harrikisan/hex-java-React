import { useEffect, useState } from 'react';
import '../../../Componentcss/Patient/BookAppointment.css';
import { getAppointments, getAppointmentsByDoctor } from '../../../store/Actions/DoctorAppointmentActions';
import { useDispatch, useSelector } from 'react-redux';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Appointment() {
  const [category, setCategory] = useState('APPOROVED'); // default to Approved
  const [filterDate, setFilterDate] = useState('');
  const [appointments, setAppointments] = useState([]);
  const [statusMap, setStatusMap] = useState({});

  const navigate = useNavigate();
  const allAppointments = useSelector(state => state.doctorAppointment.appointments);
  const dispatch = useDispatch();
  const token = useSelector(state => state.user.token);

  useEffect(() => {
    getAppointments(dispatch)({ page: 0, size: 100 }, token);
  }, [dispatch]);

  useEffect(() => {
    if (allAppointments && Array.isArray(allAppointments)) {
      const filtered = allAppointments.filter((a) => {
        const matchStatus = a.appointmentStatus === category;
        const matchDate = !filterDate || a.date === filterDate;
        return matchStatus && matchDate;
      });

      setAppointments(filtered);
    }
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
      getAppointmentsByDoctor(dispatch); // Refresh data
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
              {!(category === "FINISHED" || category === "CANCELED") && <th>Action</th>}
              {!(category === "FINISHED" || category === "CANCELED") && <th>Confirm</th>}
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
                    {!(category === "FINISHED" || category === "CANCELED") && (
                      <>
                        <td>
                          <select
                            className="dropdown-select"
                            value={statusMap[appointmentId] || ''}
                            onChange={(e) =>
                              appointmentId &&
                              setStatusMap(prev => ({
                                ...prev,
                                [appointmentId]: e.target.value
                              }))
                            }
                          >
                            <option value="">--Select--</option>
                            <option value="CANCELED">Cancel</option>
                          </select>
                        </td>
                        <td>
                          <button
                            className="button"
                            onClick={() => {
                              const selectedStatus = statusMap[appointmentId];
                              if (selectedStatus && appointmentId) {
                                editStatus(appointmentId, selectedStatus);
                              } else {
                                alert("Please select a valid status.");
                              }
                            }}
                            disabled={!statusMap[appointmentId]}
                          >
                            Confirm
                          </button>
                        </td>
                      </>
                    )}
                  </tr>
                );
              })
            ) : (
              <tr>
                {[...Array(category === "FINISHED" || category === "CANCELED" ? 4 : 6)].map((_, i) => (
                  <td key={i} style={{ textAlign: i === 0 ? "center" : "left" }}>
                    {i === 0 && "No appointments found"}
                  </td>
                ))}
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Appointment;
