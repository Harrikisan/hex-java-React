import { useEffect, useState } from "react";
import axios from 'axios';
import { useNavigate } from "react-router-dom";

function Mypatients() {
    const [patientArr, setPatientArr] = useState([]);
    

    const today = new Date().toISOString().split('T')[0];
    
    const slot = {
        ONE: "9:00 - 10:00",
        TWO: "10:00 - 11:00",
        THREE: "11:00 - 12:00",
        FOUR: "12:00 - 1:00",
        FIVE: "1:00 - 2:00",
        SIX: "2:00 - 3:00",
        SEVEN: "3:00 - 4:00",
        EIGHT: "4:00 - 5:00"
    };

    const navigate=useNavigate();

    const getMypatients = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/doctor/getTodaysAppointments', {
                params: { date: today },
                headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
            });
            setPatientArr(response.data);
        } catch (error) {
            console.log(error);
        }
    };

    

    useEffect(() => {
        getMypatients();
    }, []);

    return (
        <div className="container">
            <div className="doctor-grid" >
                {patientArr.length > 0 ? (
                    patientArr.map((p, index) => (
                        <div className="doctor-card" key={index}
                            onClick={()=>navigate(`/doctor/mypatients/attendpatient/${p.patientId}/${p.appointmentId}`)}>
                            <div className="doctor-image"></div>
                            <div className="doctor-info">
                                <p><strong>Name:</strong> {p.parientName}</p>
                            </div>
                            <div className="doctor-info">
                                <p><strong>Date:</strong> {p.date}</p>
                            </div>
                            <div className="doctor-info">
                                <p><strong>Slot:</strong> {slot[p.slot]}</p>
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
