import React, { useEffect, useState } from 'react';
import '../../../Componentcss/Patient/MiniComponents/BedBooking.css';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { getPatientDetails } from '../../../store/Actions/PatientAction';

function DoctorBooking() {
    const [patientId, setPatientId] = useState('');
    const [patientName, setPatientName] = useState('');
    const [patientType, setPatientType] = useState('');
    const [username, setUsername] = useState('');
    const [doctorName, setDoctorName] = useState('');
    const [selectedDate, setSelectedDate] = useState('');
    const [schedule, setSchedule] = useState('');
    const [reason, setReason] = useState('');
    const [msg, setMsg] = useState('');
    const [token, setToken] = useState('');

    const [availableDates, setAvailableDates] = useState([]);
    const [availableSlots, setAvailableSlots] = useState([]);

    const dispatch = useDispatch();
    const patient = useSelector(state => state.patient.patient);
    const login = useSelector(state => state.user.login);
    const navigate = useNavigate();
    const role = localStorage.getItem('role');
    const params = useParams();
    const doctorId=params.id;
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
        if (storedToken) setToken(storedToken);
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

    useEffect(() => {
        if (doctorId && token) {
            axios.get(`http://localhost:8080/api/doctor/get-by-id/${doctorId}`, {
                headers: { 'Authorization': 'Bearer ' + token }
            }).then(res => {
                setDoctorName(res.data.name);
            }).catch(err => {
                console.error("Failed to fetch doctor name", err);
            });

            axios.get(`http://localhost:8080/api/doctor/appointment/get-dates/${doctorId}`, {
                headers: { 'Authorization': 'Bearer ' + token }
            }).then(response => {
                setAvailableDates(response.data);
            }).catch(error => {
                console.error("Failed to fetch available dates", error);
            });
        }
    }, [doctorId, token]);

    useEffect(() => {
        if (selectedDate && token) {
            axios.get(`http://localhost:8080/api/doctor/schedule/get-slots/${doctorId}`, {
                params: { date: selectedDate },
                headers: { 'Authorization': 'Bearer ' + token }
            }).then(response => {
                setAvailableSlots(response.data); // Expected format: ["ONE", "TWO", ...]
            }).catch(error => {
                console.error("Failed to fetch available slots", error);
            });
        }
    }, [selectedDate, doctorId, token]);

    const BookAppointment = async () => {
        if (!patientType || !selectedDate || !schedule || !reason) {
            setMsg("Please fill in all required fields.");
            return;
        }

        try {
            await axios.post(`http://localhost:8080/api/doctor/appointment/add/${patientId}/${doctorId}/${schedule}`, {
                patientType,
                date: selectedDate,
                reason
            }, {
                headers: {
                    'Authorization': 'Bearer ' + token
                }
            });

            setMsg("Appointment booked successfully!");
        } catch (error) {
            console.error(error);
            setMsg("Failed to book appointment.");
        }
    };

    return (
        <div className="card bed-card">
            <div className="card-header color-1 text text-color-4">Doctor Appointment Booking</div>
            <div className="card-body color-3">
                {msg && (
                    <div className={`alert ${msg.includes("success") ? 'alert-primary' : 'alert-danger'}`}>
                        {msg}
                    </div>
                )}

                <div>
                    <label className='text text-color-1 line-height'>Patient ID</label>
                    <input type="text" className="form-control color-4" value={patientId} readOnly />
                </div>

                <div>
                    <label className='text text-color-1 line-height'>Patient Username</label>
                    <input type="text" className="form-control color-4" value={username} readOnly />
                </div>

                <div>
                    <label className='text text-color-1 line-height'>Patient Name</label>
                    <input type="text" className="form-control color-4" value={patientName} readOnly />
                </div>

                <div>
                    <label className='text text-color-1 line-height'>Patient Type</label>
                    <select className="form-control color-4" value={patientType} onChange={(e) => setPatientType(e.target.value)}>
                        <option value="">Select Type</option>
                        <option value="INPATIENT">IN-PATIENT</option>
                        <option value="OUTPATIENT">OUT-PATIENT</option>
                    </select>
                </div>

                <div>
                    <label className='text text-color-1 line-height'>Doctor ID</label>
                    <input type="text" className="form-control color-4" value={doctorId} readOnly />
                </div>

                <div>
                    <label className='text text-color-1 line-height'>Doctor Name</label>
                    <input type="text" className="form-control color-4" value={doctorName} readOnly />
                </div>

                <div>
                    <label className='text text-color-1 line-height'>Appointment Date</label>
                    <select className="form-control color-4" value={selectedDate} onChange={(e) => setSelectedDate(e.target.value)}>
                        <option value="">Select Date</option>
                        {availableDates.map((d, index) => (
                            <option key={index} value={d}>{d}</option>
                        ))}
                    </select>
                </div>

                <div>
                    <label className='text text-color-1 line-height'>Slot</label>
                    <select className='form-control color-4' value={schedule} onChange={(e) => setSchedule(e.target.value)}>
                        <option value="">Select Slot</option>
                        {availableSlots.map((slot) => (
                            <option key={slot.id} value={slot.id}>{slotTimeMap[slot.slot]}</option>
                        ))}
                    </select>
                </div>

                <div>
                    <label className='text text-color-1 line-height'>Reason</label>
                    <textarea className="form-control color-4" value={reason} onChange={(e) => setReason(e.target.value)} />
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

export default DoctorBooking;
