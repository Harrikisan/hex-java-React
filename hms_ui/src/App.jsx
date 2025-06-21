import Contactbar from "./Components/Basic/Contactbar";
import Navbar from "./Components/Basic/Navbar";
import Sidebar from "./Components/Basic/Sidebar";
import Login from './Components/Login/Login';
import './App.css';
import { BrowserRouter, Route, Routes, useNavigate } from "react-router-dom";
import DoctorDashboard from "./Components/Doctor/DoctorDashboard";
import PatientDashBoard from "./Components/Patient/PatientDashboard";
import NurseDashBoard from "./Components/Nurse/NurseDashboard";
import AdminDashboard from "./Components/Admin/AdminDashboard";
import BookAppointment from "./Components/Patient/BookAppointment";
import AppointmentHistory from "./Components/Patient/AppointmentHistory";
import MedicalRecord from "./Components/Patient/MedicalRecord";
import PatientProfile from "./Components/Patient/PatientProfile";
import TrackAppointment from "./Components/Patient/TrackAppointment";
import Home from "./Components/Basic/Home";
import Signup from "./Components/Login/Signup";


function App() {


  return (
    <BrowserRouter>
      <div className="app-container">
        <Contactbar />
        <Navbar />
        <div className="main-content">
          <Sidebar />
          <div className="content-area">
            <img src="/images/amazecare.jpeg" alt="Amaze Care" className="background-image" />
            <Routes>

              {/* Login Signin*/}
              <Route path="/login" element={<Login />} />
              <Route path="/signup" element={<Signup/>}/>
              
              {/* Home*/}
              <Route path="/" element={<Home/>}/>

              {/* Doctor routes*/}
              <Route path="/doctor" element={<DoctorDashboard />} />

              {/* Patient routes */}
              <Route path="/patient" >
                <Route index element={<PatientDashBoard />} />
                <Route path="bookAppointment" element={<BookAppointment />} />
                <Route path="appointmenthistory" element={<AppointmentHistory />} />
                <Route path="medicalRecord" element={<MedicalRecord />} />
                <Route path="patientProfile" element={<PatientProfile />} />
                <Route path="trackAppointment" element={<TrackAppointment />} />
              </Route>

              {/* Nurse routes */}
              <Route path="/nurse" element={<NurseDashBoard />} />

              {/* Admin routes */}
              <Route path="/admin" element={<AdminDashboard />} />

            </Routes>
          </div>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
