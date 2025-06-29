import React, { useEffect, useState } from 'react';
import '../../../Componentcss/Patient/MiniComponents/BedBooking.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { getPatientDetails } from '../../../store/Actions/PatientAction';

function LabBooking() {
  const [patientId, setPatientId] = useState('');
  const [patientName, setPatientName] = useState('');
  const [patientType, setPatientType] = useState('');
  const [username, setUsername] = useState('');
  const [doctorId, setDoctorId] = useState('');
  const [doctorName, setDoctorName] = useState('');
  const [selectedDate, setSelectedDate] = useState('');
  const [lab, setLab] = useState('');
  const [schedule, setSchedule] = useState('');
  const [test, setTest] = useState('');
  const [refered, setRefered] = useState(false);
  const [reason, setReason] = useState('');
  const [msg, setMsg] = useState('');
  const [token, setToken] = useState('');

  const [labArr, setLabArr] = useState([]);
  const [testArr, setTestArr] = useState([]);
  const [dateArr, setDateArr] = useState([]);
  const [scheduleArr, setScheduleArr] = useState([]);
  const [day, setDay] = useState('');

  const dispatch = useDispatch();
  const patient = useSelector(state => state.patient.patient);
  const login = useSelector(state => state.user.login);
  const navigate = useNavigate();
  const role = localStorage.getItem('role');

  const slotTimeMap = {
    "ONE": "9:00 - 10:00 AM",
    "TWO": "10:00 - 11:00 AM",
    "THREE": "11:00 - 12:00 PM",
    "FOUR": "12:00 - 1:00 PM",
    "FIVE": "2:00 - 3:00 PM",
    "SIX": "3:00 - 4:00 PM",
    "SEVEN": "4:00 - 5:00 PM",
    "EIGHT": "5:00 - 6:00 PM"
  };

  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    if (storedToken) {
      setToken(storedToken);
    }
  }, []);

  useEffect(() => {
    if (!login || role !== "PATIENT") {
      navigate("/login");
    }
    getPatientDetails(dispatch);
  }, []);

  useEffect(() => {
    if (patient && patient.id) {
      setPatientId(patient.id);
      setPatientName(patient.name);
      setUsername(patient.user?.username || '');
    }
  }, [patient]);

  useEffect(() => { if (lab) getTest(); }, [lab]);
  useEffect(() => { if (test) getDates(); }, [test]);
  useEffect(() => { if (selectedDate) getSchedule(); }, [selectedDate]);

  useEffect(() => {
    const getAvailableLabs = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/lab/get-all', {
          headers: { Authorization: `Bearer ${token}` }
        });
        setLabArr(response.data);
      } catch (err) {
        console.error("Error fetching labs", err);
      }
    };
    getAvailableLabs();
  }, [token]);

  const getTest = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/lab/test/getByLab/${lab}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setTestArr(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const getDates = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/test/appointment/get-dates/${lab}/${test}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setDateArr(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const getSchedule = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/test/schedule/getAvailableSlots/${lab}`, {
        params: { date: selectedDate },
        headers: { Authorization: `Bearer ${token}` }
      });
      setScheduleArr(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const BookAppointment = async () => {
    if (!patientType) {
      setMsg("Please select a patient type before booking.");
      return;
    }
    try {
      const response = await axios.post(
        `http://localhost:8080/api/test/appointment/add/${patientId}/${doctorId || 0}/${lab}/${test}/${schedule}`,
        { date: selectedDate, patientType, reason },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      console.log("Response:", response.data);
      setMsg("Lab booked successfully!");
    } catch (err) {
      console.log("Booking error:", err);
      setMsg("Booking failed. Please try again.");
    }
  };

  return (
    <div className="card bed-card">
      <div className="card-header color-1 text text-color-4">Lab Booking</div>
      <div className="card-body color-3">
        {msg && (
          <div className={`alert ${msg.includes("success") ? 'alert-primary' : 'alert-danger'}`}>
            {msg}
          </div>
        )}

        <div>
          <label className='text text-color-1 line-height'>Patient ID</label>
          <input type="text" className="form-control color-4" value={patientId}
            readOnly={role === "PATIENT"} onChange={(e) => setPatientId(e.target.value)} required />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Patient Username</label>
          <input type="text" className="form-control color-4" value={username}
            readOnly={role === "PATIENT"} onChange={(e) => setUsername(e.target.value)} required />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Patient Name</label>
          <input type="text" className="form-control color-4" value={patientName}
            readOnly={role === "PATIENT"} onChange={(e) => setPatientName(e.target.value)} required />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Patient Type</label>
          <select className="form-control color-4" value={patientType}
            onChange={(e) => setPatientType(e.target.value)}>
            <option value="">Select Type</option>
            <option value="INPATIENT">IN-PATIENT</option>
            <option value="OUTPATIENT">OUT-PATIENT</option>
          </select>
        </div>

        {refered && (
          <>
            <div>
              <label className='text text-color-1 line-height'>Referred Doctor ID</label>
              <input type="text" className="form-control color-4"
                value={doctorId} onChange={(e) => setDoctorId(e.target.value)} />
            </div>
            <div>
              <label className='text text-color-1 line-height'>Doctor Name</label>
              <input type="text" className="form-control color-4"
                value={doctorName} onChange={(e) => setDoctorName(e.target.value)} />
            </div>
          </>
        )}

        <div>
          <label className='text text-color-1 line-height'>Lab</label>
          <select className='form-control color-4' value={lab} onChange={(e) => setLab(e.target.value)}>
            <option>Select Lab</option>
            {labArr.map((lab, index) => (
              <option key={index} value={lab.id}>{lab.name}</option>
            ))}
          </select>
        </div>

        <div>
          <label className='text text-color-1 line-height'>Test</label>
          <select className='form-control color-4' value={test}
            onChange={(e) => setTest(e.target.value)}>
            <option>Select Test</option>
            {testArr.map(t => (
              <option key={t.id} value={t.id}>{t.testType}</option>
            ))}
          </select>
        </div>

        <div>
          <label className='text text-color-1 line-height'>Date</label>
          <select className='form-control color-4' value={selectedDate}
            onChange={(e) => {
              const dateVal = e.target.value;
              setSelectedDate(dateVal);
            }}>
            <option value="">Select Date</option>
            {dateArr.map((d, index) => (
              <option key={index} value={d}>{d}</option>
            ))}
          </select>
        </div>

        <div>
          <label className='text text-color-1 line-height'>Slot</label>
          <select className='form-control color-4' value={schedule}
            onChange={(e) => setSchedule(e.target.value)}>
            <option value="">Select Slot</option>
            {scheduleArr.map(s => (
              <option key={s.id} value={s.id}>
                {slotTimeMap[s.slot] || s.slot}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className='text text-color-1 line-height'>Reason</label>
          <textarea className="form-control color-4" value={reason}
            onChange={(e) => setReason(e.target.value)} />
        </div>
      </div>

      <div className="card-footer color-2">
        <button className="btn btn-primary color-1 text text-color-4" onClick={BookAppointment}>
          Submit
        </button>
      </div>
    </div>
  );
}

export default LabBooking;
