import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import '../../../Componentcss/Doctor/MiniComponents/AttendPatient.css';
import axios from "axios";
import { useSelector } from "react-redux";

function AttendPatient() {
    const [patientName, setPatientName] = useState('');
    const [diagnosis, setDiagnosis] = useState('');
    const [notes, setNotes] = useState('');
    const [msg, setMsg] = useState('');
    const [prescriptions, setPrescriptions] = useState([]);

    const [medicineName, setMedicineName] = useState('');
    const [dosage, setDosage] = useState('');
    const [morning, setMorning] = useState(false);
    const [afternoon, setAfternoon] = useState(false);
    const [night, setNight] = useState(false);
    const [beforeMeal, setBeforeMeal] = useState(false);
    const [afterMeal, setAfterMeal] = useState(false);

    const token = useSelector(state => state.user.token);
    const { patientId, appointmentId } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        if (!patientId || patientId === "undefined" || !appointmentId || appointmentId === "undefined") {
            navigate("/doctor/todaysAppointments");
        } else {
            getPatient();
        }
    }, [patientId]);

    const getPatient = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/patient/get-by-id/${patientId}`, {
                headers: { 'Authorization': 'Bearer ' + token }
            });
            setPatientName(response.data.name);
        } catch (error) {
            console.log(error);
            navigate("/doctor/todaysAppointments");
        }
    };

    const addMedicalRecord = async () => {
        if (!diagnosis.trim() || !notes.trim()) {
            setMsg("Diagnosis and Notes are required.");
            return;
        }

        try {
            const response = await axios.post(`http://localhost:8080/api/medicalrecord/add/${patientId}`, {
                diagnosis,
                notes,
                prescriptions
            }, {
                headers: { 'Authorization': 'Bearer ' + token }
            });

            setMsg("Medical record added successfully.");
            await addPrescription(response.data.id);
        } catch (error) {
            console.log(error);
            setMsg("Failed to add medical record.");
        }
    };

    const addPrescription = async (medicalRecordId) => {
        try {
            await axios.post(
                `http://localhost:8080/api/prescription/add/batch/${medicalRecordId}`,
                prescriptions,
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );
            await setAppointmentStatus(appointmentId, "FINISHED");
        } catch (error) {
            console.log(error);
        }
    };

    const setAppointmentStatus = async (id, status) => {
        try {
            await axios.put(`http://localhost:8080/api/doctor/appointment/editStatus/${id}`, null, {
                params: { status },
                headers: { Authorization: `Bearer ${token}` }
            });
            navigate("/doctor/todaysAppointments");
        } catch (error) {
            console.log("Failed to update appointment status", error);
        }
    };

    const addPrescriptionCard = () => {
        if (!medicineName.trim() || !dosage.trim()) {
            setMsg("Medicine name and dosage are required.");
            return;
        }

        const newPrescription = {
            medicineName,
            dosage,
            morning,
            afternoon,
            night,
            beforeMeal,
            afterMeal
        };

        setPrescriptions([...prescriptions, newPrescription]);
        setMedicineName('');
        setDosage('');
        setMorning(false);
        setAfternoon(false);
        setNight(false);
        setBeforeMeal(false);
        setAfterMeal(false);
        setMsg('');
    };

    const removePrescription = (indexToRemove) => {
        const updated = [...prescriptions];
        updated.splice(indexToRemove, 1);
        setPrescriptions(updated);
    };

    return (
        <div className="container attend-container">
            <div className="col-lg-12">
                <div className="row">
                    <div className="col-lg-12 attend-breadcrumb">
                        <nav aria-label="breadcrumb">
                            <ol className="breadcrumb">
                                <li className="breadcrumb-item">
                                    <Link className="link" to="/doctor/todaysAppointments">Today's Appointments</Link>
                                </li>
                                <li className="breadcrumb-item active" aria-current="page">Attend Patient</li>
                            </ol>
                        </nav>
                    </div>
                </div>

                <div className="card attend-box">
                    <div className="card-header attend-header">Patient Consultation</div>
                    <div className="card-body attend-body">
                        {msg && (
                            <div className={`alert ${msg.includes("success") ? "alert-primary" : "alert-danger"}`}>
                                {msg}
                            </div>
                        )}

                        <div className="mb-3 attend-group">
                            <label>Patient Name:</label>
                            <input type="text" className="form-control attend-input" value={patientName} readOnly />
                        </div>
                        <div className="mb-3 attend-group">
                            <label>Diagnosis:</label>
                            <textarea className="form-control attend-textarea" value={diagnosis} onChange={(e) => setDiagnosis(e.target.value)} />
                        </div>
                        <div className="mb-3 attend-group">
                            <label>Notes:</label>
                            <textarea className="form-control attend-textarea" value={notes} onChange={(e) => setNotes(e.target.value)} />
                        </div>

                        <div className="prescription-section">
                            {prescriptions.map((p, idx) => (
                                <div key={idx} className="prescription-card">
                                    <p><strong>{p.medicineName}</strong> - {p.dosage}</p>
                                    <p>{p.morning && "Morning "} {p.afternoon && "Afternoon "} {p.night && "Night "}</p>
                                    <p>{p.beforeMeal && "Before Meal "} {p.afterMeal && "After Meal"}</p>
                                    <button
                                        className="btn btn-secondary mb-3 attend-button"
                                        onClick={() => removePrescription(idx)}
                                    >
                                        Remove
                                    </button>
                                </div>
                            ))}
                        </div>

                        <hr />
                        <h5 className="prescription-title attend-group">Add Prescription</h5>
                        <div className="mb-3 attend-group">
                            <label>Medicine Name:</label>
                            <input type="text" className="form-control attend-input" value={medicineName}
                                onChange={(e) => setMedicineName(e.target.value)} required />
                        </div>
                        <div className="mb-3 attend-group">
                            <label>Dosage:</label>
                            <input type="text" className="form-control attend-input" value={dosage}
                                onChange={(e) => setDosage(e.target.value)} required />
                        </div>

                        <div className="checkbox-group">
                            <label><input type="checkbox" checked={morning} onChange={() => setMorning(!morning)} /> Morning</label>
                            <label><input type="checkbox" checked={afternoon} onChange={() => setAfternoon(!afternoon)} /> Afternoon</label>
                            <label><input type="checkbox" checked={night} onChange={() => setNight(!night)} /> Night</label>
                        </div>

                        <div className="checkbox-group">
                            <label><input type="checkbox" checked={beforeMeal} onChange={() => setBeforeMeal(!beforeMeal)} /> Before Meal</label>
                            <label><input type="checkbox" checked={afterMeal} onChange={() => setAfterMeal(!afterMeal)} /> After Meal</label>
                        </div>

                        <button className="btn btn-secondary mb-3 attend-button" onClick={addPrescriptionCard}>
                            + Add Prescription
                        </button>
                    </div>

                    <div className="card-footer attend-footer">
                        <button className="btn btn-primary attend-button" onClick={addMedicalRecord}>
                            Add
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AttendPatient;
