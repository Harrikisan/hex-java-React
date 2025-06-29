import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import '../../../Componentcss/Patient/TrackAppointment.css';

function DoctorAppointment() {
  const navigate = useNavigate();
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');
  const [appointments, setAppointments] = useState([]);

  useEffect(() => {
    if (!token || role !== "PATIENT") {
      navigate("/login");
    } else {
      const getAppointments = async () => {
        const response = await axios.get('http://localhost:8080/api/test/appointment/get-by-patien_id', {
          headers: { 'Authorization': 'Bearer ' + token }
        })
        setAppointments(response.data)
      }
      getAppointments()
    }
  }, [navigate, token, role]);

  const editAppointment = async (id) => {
    axios.put(`http://localhost:8080/api/test/appointment/edit/${id}`, {}, {
      params: { status: "CANCELED" },
      headers: { 'Authorization': 'Bearer ' + token }
    }).then(res => console.log(res.data))
      .catch(error => console.log(error))
  }

  return (
    <div className="appointment-table-container">
      <table className="appointment-table">
        <thead>
          <tr>
            <th>S.No</th>
            <th>Date</th>
            <th>Lab</th>
            <th>Test</th>
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
                <td>{a.lab.name}</td>
                <td>{a.test.testType}</td>
                <td>{a.status}</td>
                <td>
                  {
                    a.status === "CANCELED" ? "-" :
                      <button className="button" onClick={() => editAppointment(a.id)}>
                        Cancel
                      </button>}
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
