import axios from 'axios';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import "../../Componentcss/Doctor/ViewMedicalRecords.css";

function TrackAppointment() {
    const id=useSelector(state=>state.user.userId);
    const navigate = useNavigate();
    const token = useSelector(state => state.user.token);

    const [page, setPage] = useState(0);
    const [size] = useState(1);
    const [records, setRecords] = useState([]);
    const [prescriptionsMap, setPrescriptionsMap] = useState({}); 

    const getMedicalRecord = async (id) => {
        try {
            const response = await axios.get(
                `http://localhost:8080/api/medicalrecord/get/patient/${id}?page=${page}&size=${size}`,
                {
                    headers: { Authorization: 'Bearer ' + token }
                }
            );

            const recordList = response.data;
            setRecords(recordList);

            const map = {};

            for (const record of recordList) {
                try {
                    const response = await axios.get(
                        `http://localhost:8080/api/prescription/getByRecord?recordId=${record.id}`,
                        { headers: { Authorization: 'Bearer ' + token } }
                    );
                    map[record.id] = response.data;
                } catch (error) {
                    console.error(`Failed to fetch prescriptions for record ${record.id}:`, error);
                    map[record.id] = [];
                }
            }

            setPrescriptionsMap(map);


        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
            getMedicalRecord(id);
    }, [page]);

    return (
        <div className="container-fluid mt-5 text-color-1">
            <div className="row justify-content-center mb-4">
                <div className="col-12">
                    <h3 className="fw-bold text-center">Medical Record Details</h3>
                </div>
            </div>

            <div className="row justify-content-center">
                <div className="col-12">
                    {records.map((record, index) => (
                        <div key={index} className="card mb-4 shadow-sm w-100">
                            <div className="card-header color-3">
                                <strong className="text-color-1">Record By {record.doctor.name}</strong>
                            </div>
                            <div className="card-body color-4">
                                <div className="mb-2 text-color-1"><strong>Date:</strong> {record.recordDate}</div>
                                <div className="mb-2 text-color-1"><strong>Diagnosis:</strong> {record.diagnosis}</div>
                                <div className="mb-2 text-color-1"><strong>Notes:</strong> {record.notes}</div>

                                {/* Prescriptions under the medical record */}
                                {prescriptionsMap[record.id]?.length > 0 ? (
                                    <div className="prescription-block">
                                        <h6>Prescriptions:</h6>
                                        <ul className="prescription-list">
                                            {prescriptionsMap[record.id].map((pres, i) => (
                                                <li key={i}>
                                                    <div><strong>Medicine:</strong> {pres.medicineName}</div>
                                                    <div><strong>Dosage:</strong> {pres.dosage}</div>
                                                    <div>
                                                        {pres.morning && "Morning "}
                                                        {pres.afternoon && "Afternoon "}
                                                        {pres.night && "Night "}
                                                    </div>
                                                    <div>
                                                        {pres.beforeMeal && "Before Meal "}
                                                        {pres.afterMeal && "After Meal"}
                                                    </div>
                                                </li>
                                            ))}
                                        </ul>
                                    </div>
                                ) : (
                                    <div className="prescription-block">
                                        <h6>Prescriptions:</h6>
                                        <div className="text-muted">No prescriptions available for this record.</div>
                                    </div>
                                )}

                            </div>
                        </div>
                    ))}

                </div>
            </div>

            <nav aria-label="Page navigation example" className="d-flex justify-content-center mt-4">
                <ul className="pagination">
                    <li className="page-item">
                        <button
                            className="page-link"
                            onClick={() => setPage(prev => Math.max(prev - 1, 0))}
                            disabled={page === 0}
                        >
                            Previous
                        </button>
                    </li>
                    <li className="page-item">
                        <button
                            className="page-link"
                            onClick={() => setPage(prev => prev + 1)}
                        >
                            Next
                        </button>
                    </li>
                </ul>
            </nav>
        </div>
    );

}

export default TrackAppointment;
