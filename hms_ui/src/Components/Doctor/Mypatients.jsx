import { useEffect, useState } from "react";
import axios from 'axios';
import { useNavigate } from "react-router-dom";
import "../../Componentcss/Doctor/Mypatients.css"; // Match your current styling structure

function Mypatients() {
    const [patientArr, setPatientArr] = useState([]);
    const navigate = useNavigate();

    const today = new Date().toISOString().split('T')[0];

    const getMypatients = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/doctor/getMyPatients', {
                params: { date: today },
                headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
            });
            setPatientArr(response.data);
        } catch (error) {
            console.error("Error fetching patients:", error);
        }
    };

    useEffect(() => {
        getMypatients();
    }, []);

    return (
        <div className="container">
            <div className="doctor-grid">
                {patientArr.length > 0 ? (
                    patientArr.map((p, index) => (
                        <div className="doctor-card" key={index}
                            onClick={() => navigate(`/doctor/viewMedicalrecords/${p.id}`)}>
                            <div className="doctor-image">
                                <img
                                    src={p.imageUrl ? `/images/${p.imageUrl}` : "/images/default-avatar.png"}
                                    alt="Patient"
                                    className="doctor-thumbnail"
                                    onError={(e) => { e.target.src = "/images/default-avatar.png"; }}
                                />
                            </div>
                            <div className="doctor-info">
                                <p><strong>Name:</strong> {p.name}</p>
                                <p><strong>DOB:</strong> {p.dob}</p>
                                <p><strong>Phone:</strong> {p.phone}</p>
                            </div>
                        </div>
                    ))
                ) : (
                    <p>No patients found.</p>
                )}
            </div>
        </div>
    );
}

export default Mypatients;
