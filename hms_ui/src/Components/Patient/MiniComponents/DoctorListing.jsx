import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import "../../../Componentcss/Patient/MiniComponents/DoctorListing.css";
import { getAllDoctors } from "../../../store/Actions/DoctorActions";
import { useNavigate } from "react-router-dom";

function DoctorListing() {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [doctor, setDoctor] = useState("");
    const [specialization, setSpecialization] = useState("");
    const [doctorList, setDoctorList] = useState([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);

    const allDoctors = useSelector(state => state.doctors.doctors);

    const checkLogin = () => {
        if (!localStorage.getItem('login')) {
            navigate("/login");
        }
    };

    useEffect(() => {
        getAllDoctors(dispatch)({ page, size });
    }, [dispatch, page, size]);

    useEffect(() => {
        setDoctorList(allDoctors);
    }, [allDoctors]);

    const getDoctors = () => {
        if (doctor === "" && specialization === "") {
            setDoctorList(allDoctors);
            return;
        }

        let filtered = [...allDoctors];

        if (doctor !== "") {
            filtered = filtered.filter((doc) =>
                doc.name.toLowerCase().includes(doctor.toLowerCase())
            );
        }

        if (specialization !== "") {
            filtered = filtered.filter((doc) =>
                doc.specilization.toLowerCase() === specialization.toLowerCase()
            );
        }

        setDoctorList(filtered);
    };

    return (
        <div>
            <div className="doctor-search-bar mt-3">
                <div className="field-group">
                    <label className="dropdown-label">Doctor</label>
                    <input
                        type="text"
                        className="dropdown-input"
                        onChange={(e) => setDoctor(e.target.value.trim().toLowerCase())}
                        placeholder="Enter doctor name"
                    />
                </div>
                <div className="field-group">
                    <label className="dropdown-label">Specialization</label>
                    <select
                        className="dropdown-select"
                        onChange={(e) => setSpecialization(e.target.value)}
                    >
                        <option value="">Select</option>
                        <option value="Cardiology">Cardiology</option>
                        <option value="Neurology">Neurology</option>
                        <option value="Pediatrician">Pediatrician</option>
                        <option value="Dermatology">Dermatology</option>
                        <option value="Ortho">Ortho</option>
                    </select>
                </div>
                <div>
                    <button className="button" onClick={getDoctors}>Search</button>
                </div>
            </div>

            <div className="doctor-grid">
                {doctorList.length > 0 ? (
                    doctorList.map((d, index) => (
                        <div className="doctor-card" key={index}
                            onClick={() => { checkLogin(); navigate(`/patient/doctorBooking/${d.id}`); }}>
                            <div className="doctor-image">
                                <img
                                    src={d.imageUrl ? `/images/${d.imageUrl}` : "/images/default-doctor.png"}
                                    alt="Doctor"
                                    className="doctor-thumbnail"
                                />
                            </div>
                            <div className="doctor-info">
                                <p><strong>Name:</strong> {d.name}</p>
                                <p><strong>Specialization:</strong> {d.specilization}</p>
                            </div>
                        </div>
                    ))
                ) : (
                    <p>No doctors found.</p>
                )}
            </div>

            {/* Centered pagination */}
            <div className="pagination-arrows mt-4">
                <button
                    className="arrow-button"
                    onClick={() => setPage((prev) => Math.max(prev - 1, 0))}
                    disabled={page === 0}
                >
                    &#8592;
                </button>
                <span className="page-number">Page {page + 1}</span>
                <button
                    className="arrow-button"
                    onClick={() => setPage((prev) => prev + 1)}
                >
                    &#8594;
                </button>
            </div>
        </div>
    );
}

export default DoctorListing;
