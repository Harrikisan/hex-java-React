import Contactbar from "./Components/Basic/Contactbar";
import Navbar from "./Components/Basic/Navbar";
import Sidebar from "./Components/Basic/Sidebar";
import Login from './Components/Login/Login';
import './App.css';
import { BrowserRouter, Route, Routes, useNavigate } from "react-router-dom";
import DoctorDashboard from "./Components/Doctor/DoctorDashboard";
import Appointment from "./Components/Doctor/Appointment";
import DoctorProfile from "./Components/Doctor/DoctorProfile";
import EditSchedule from "./Components/Doctor/EditSchedule";
import Mypatients from "./Components/Doctor/Mypatients";
import UploadDocument from "./Components/Doctor/UploadDocument";
import TodaysAppointments from "./Components/Doctor/TodaysAppointments";
import AttendPatient from "./Components/Doctor/MiniComponents/AttendPatient";
import PatientDashBoard from "./Components/Patient/PatientDashboard";
import NurseDashBoard from "./Components/Nurse/NurseDashboard";
import BookAppointment from "./Components/Patient/BookAppointment";
import MedicalRecord from "./Components/Patient/MedicalRecord";
import PatientProfile from "./Components/Patient/PatientProfile";
import TrackAppointment from "./Components/Patient/TrackAppointment";
import Signup from "./Components/Login/Signup";
import DoctorBooking from "./Components/Patient/MiniComponents/DoctorBooking";
import ViewMedicalRecords from "./Components/Doctor/MiniComponents/ViewMedicalRecords";
import AddDoctor from "./Components/Admin/AddDoctor";
import AddLab from "./Components/Admin/AddLab";
import AddTest from "./Components/Admin/AddTest";
import AddWard from "./Components/Admin/AddWard";
import AddBed from "./Components/Admin/AddBed";
import Home from "./Components/Basic/Home";
import AdminDashboard from "./Components/Admin/AdminDashboard";


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
              <Route path="/signup" element={<Signup />} />

              {/* Home*/}
              <Route path="/" element={<Home/>} />

              {/* Doctor routes*/}
              <Route path="/doctor">
                <Route index element={<DoctorDashboard />} />
                <Route path="appointments" element={<Appointment />} />
                <Route path="profile" element={<DoctorProfile />} />
                <Route path="edieSchedule" element={<EditSchedule />} />
                <Route path="mypatients" element={<Mypatients />} />
                <Route path="mypatients/attendPatient/:patientId/:appointmentId" element={<AttendPatient />} />
                <Route path="uploadDocument" element={<UploadDocument />} />
                <Route path="todaysAppointments" element={<TodaysAppointments />} />
                <Route path="viewMedicalrecords/:id" element={<ViewMedicalRecords />} />
              </Route>

              {/* Patient routes */}
              <Route path="/patient" >
                <Route index element={<PatientDashBoard />} />
                <Route path="bookAppointment" element={<BookAppointment />} />
                <Route path="medicalRecord" element={<MedicalRecord />} />
                <Route path="patientProfile" element={<PatientProfile />} />
                <Route path="trackAppointment" element={<TrackAppointment />} />
                <Route path="doctorBooking/:id" element={<DoctorBooking />} />

              </Route>

              {/* Nurse routes */}
              <Route path="/nurse" element={<NurseDashBoard />} />

              {/* Admin routes */}
              <Route path="/admin" >
              <Route index element={<AdminDashboard/>}/>
                <Route path="addDoctor" element={<AddDoctor/>} />
                <Route path="addLab" element={<AddLab/>}/>
                <Route path="addTest" element={<AddTest/>}/>
                <Route path="addWard" element={<AddWard/>}/>
                <Route path="addBed" element={<AddBed/>}/>
              </Route>

            </Routes>
          </div>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
