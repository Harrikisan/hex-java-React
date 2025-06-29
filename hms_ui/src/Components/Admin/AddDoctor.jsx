import { useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";
import "../../Componentcss/Login/Login.css"; // Make sure your color classes are defined here

function AddDoctor() {
    const [name, setName] = useState("");
    const [specialization, setSpecialization] = useState("");
    const [phone, setPhone] = useState("");
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [qualification, setQualification] = useState("");
    const [yearsOfExperience, setYearsOfExperience] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [msg, setMsg] = useState("");

    const token = useSelector(state => state.user.token);

    const specializationOptions = [
        "Cardiologist", "Dermatologist", "Neurologist", "Pediatrician",
        "Orthopedic", "Gynecologist", "General Physician", "Psychiatrist",
        "ENT Specialist", "Oncologist", "Urologist", "Dentist", "Radiologist"
    ];

    const addDoctor = async () => {
        try {
            await axios.post("http://localhost:8080/api/doctor/add", {
                name: name,
                specilization: specialization,
                phone: phone,
                email: email,
                address: address,
                qualification: qualification,
                yearsOfExperience: yearsOfExperience,
                user: {
                    username: username,
                    password: password
                }
            }, {
                headers: { Authorization: "Bearer " + token }
            });

            setMsg("Doctor added successfully!");
        } catch (error) {
            console.error("Error adding doctor:", error);
            setMsg("Failed to add doctor. Please check all fields or try again.");
        }
    };

    return (
        <div className="container mt-4">
            <div className="col-lg-8 offset-lg-2">
                <div className="card">
                    <div className="card-header color-1 text-color-4"><h4>Add Doctor</h4></div>
                    <div className="card-body color-3">
                        {
                            msg !== "" ?
                            msg === "Doctor added successfully!" ?
                            <div className="alert alert-primary">{msg}</div> :
                            <div className="alert alert-primary">{msg}</div> : ""
                        }

                        <div className="mb-3">
                            <label className="text-color-1">Name</label>
                            <input type="text" value={name} onChange={(e) => setName(e.target.value)} className="form-control color-4" required />
                        </div>

                        <div className="mb-3">
                            <label className="text-color-1">Specialization</label>
                            <select value={specialization} onChange={(e) => setSpecialization(e.target.value)} className="form-control color-4" required>
                                <option value="">Select Specialization</option>
                                {specializationOptions.map((spec, index) => (
                                    <option key={index} value={spec}>{spec}</option>
                                ))}
                            </select>
                        </div>

                        <div className="mb-3">
                            <label className="text-color-1">Phone</label>
                            <input type="text" value={phone} onChange={(e) => setPhone(e.target.value)} className="form-control color-4" />
                        </div>

                        <div className="mb-3">
                            <label className="text-color-1">Email</label>
                            <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} className="form-control color-4" />
                        </div>

                        <div className="mb-3">
                            <label className="text-color-1">Address</label>
                            <textarea value={address} onChange={(e) => setAddress(e.target.value)} className="form-control color-4" />
                        </div>

                        <div className="mb-3">
                            <label className="text-color-1">Qualification</label>
                            <input type="text" value={qualification} onChange={(e) => setQualification(e.target.value)} className="form-control color-4" required />
                        </div>

                        <div className="mb-3">
                            <label className="text-color-1">Years of Experience</label>
                            <input type="text" value={yearsOfExperience} onChange={(e) => setYearsOfExperience(e.target.value)} className="form-control color-4" />
                        </div>

                        <hr className="border-color" />

                        <div className="mb-3">
                            <label className="text-color-1">Username (for login)</label>
                            <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} className="form-control color-4" required />
                        </div>

                        <div className="mb-3">
                            <label className="text-color-1">Password</label>
                            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} className="form-control color-4" required />
                        </div>
                    </div>

                    <div className="card-footer text-end color-2">
                        <button className="button" onClick={addDoctor}>
                            Add Doctor
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AddDoctor;
