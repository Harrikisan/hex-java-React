import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import '../../../Componentcss/Patient/TrackAppointment.css';

function BedAppointment() {
  const navigate = useNavigate();
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');
  const [appointments, setAppointments] = useState([]);

  useEffect(() => {
    if (!token || role !== "PATIENT") {
      navigate("/login");
    } else {
      getAppointment();
    }
  }, [navigate, token, role]);

  const getAppointment = async () => {
    try {
      const response = await axios.get(
        'http://localhost:8080/api/bed/appointment/by-username',
        {
          headers: { Authorization: 'Bearer ' + token }
        }
      );
      setAppointments(response.data);
    } catch (error) {
      console.error("Error fetching appointments:", error);
    }
  };

  const cancelBooking = async (id) => {
    try {
      const response = await axios.put(
        `http://localhost:8080/api/bed/appointment/cancel/${id}`,
        {},
        {
          headers: { Authorization: 'Bearer ' + token }
        }
      );
      console.log(response.data);
      getAppointment(); // Refresh the appointment list
    } catch (error) {
      console.error("Error cancelling booking:", error);
    }
  };

  return (
    <div className="appointment-table-container">
      <table className="appointment-table">
        <thead>
          <tr>
            <th>S.No</th>
            <th>From</th>
            <th>To</th>
            <th>Status</th>
            <th>Cancel</th>
          </tr>
        </thead>
        <tbody>
          {appointments.length > 0 ? (
            appointments.map((a, index) => (
              <tr key={a.id || index}>
                <td>{index + 1}</td>
                <td>{a.adminssionDate}</td>
                <td>{a.dischargeDate}</td>
                <td>{a.status}</td>
                <td>
                  {a.status !== "CANCELED" && a.status !== "FINISHED" ? (
                    <button className="button" onClick={() => cancelBooking(a.id)}>
                      Cancel
                    </button>
                  ) : (
                    "-"
                  )}
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td style={{ textAlign: "center" }}>
                No appointments found
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default BedAppointment;
