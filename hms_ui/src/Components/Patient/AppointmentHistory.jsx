import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import DoctorAppointment from "./MiniComponents/DoctorAppointment";

function AppointmentHistory() {
  const navigate = useNavigate();
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');

  useEffect(() => {
    if (!token || role !== "PATIENT") {
      navigate("/login");
    }
  }, []);

  return (
    <div>
    </div>
  );
}

export default AppointmentHistory;
