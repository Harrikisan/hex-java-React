import { useEffect, useState } from 'react';
import '../../Componentcss/Patient/PatientProfile.css';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

function PatientProfile() {
    const token = useSelector(state => state.user.token);
    const role = useSelector(state => state.user.role);
    const navigate = useNavigate();

    const [name, setName] = useState('');
    const [gender, setGender] = useState('');
    const [phone, setPhone] = useState('');
    const [email, setEmail] = useState('');
    const [address, setAddress] = useState('');
    const [emergencyContact, setEmergencyContact] = useState('');
    const [dob, setDob] = useState('');
    const [userStatus, setUserStatus] = useState('');
    const [image, setImage] = useState(null);
    const [previewUrl, setPreviewUrl] = useState(null);

    useEffect(() => {
        if (!token || role !== "PATIENT") {
            navigate("/login");
            return;
        }

        axios.get("http://localhost:8080/api/patient/get", {
            headers: { Authorization: 'Bearer ' + token }
        }).then(res => {
            const data = res.data;
            setName(data.name || '');
            setGender(data.gender || '');
            setPhone(data.phone || '');
            setEmail(data.email || '');
            setAddress(data.address || '');
            setEmergencyContact(data.emergencyContact || '');
            setDob(data.dob || '');
            setUserStatus(data.userStatus || '');
            if (data.imageUrl) {
                setPreviewUrl(`/images/${data.imageUrl}`);
            }
        }).catch(err => {
            console.error("Failed to fetch patient profile", err);
        });
    }, [token, role, navigate]);

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        setImage(file);
        setPreviewUrl(URL.createObjectURL(file));
    };

    const handleUpdate = async () => {
        const updatedData = {
            name,
            gender,
            phone,
            email,
            address,
            emergencyContact,
            dob,
            userStatus
        };

        try {
            await axios.put("http://localhost:8080/api/patient/edit", updatedData, {
                headers: {
                    Authorization: 'Bearer ' + token
                }
            });

            if (image) {
                const formData = new FormData();
                formData.append("file", image);

                await axios.post("http://localhost:8080/api/patient/upload/profile-pic", formData, {
                    headers: {
                        "Content-Type": "multipart/form-data",
                        Authorization: "Bearer " + token
                    }
                });
            }

        } catch (err) {
            console.error("Update failed:", err);
        }
    };

    return (
        <div className="profile-container">
            <div className="profile-card d-flex">

                <div className="profile-image-section">
                    <img
                        src={previewUrl || '/images/default-patient.png'}
                        alt="Profile"
                        className="profile-image mb-3"
                    />
                    <input type="file" accept="image/*" onChange={handleImageChange} />
                </div>

                <div className="profile-info p-4">
                    <div className="form-group">
                        <label className="text-color-1">Name</label>
                        <input className="form-control mb-2" value={name} onChange={e => setName(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Gender</label>
                        <select className="form-control mb-2" value={gender} onChange={e => setGender(e.target.value)}>
                            <option value="">Select</option>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                            <option value="OTHER">Other</option>
                        </select>
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Phone</label>
                        <input className="form-control mb-2" value={phone} onChange={e => setPhone(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Email</label>
                        <input className="form-control mb-2" value={email} onChange={e => setEmail(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Address</label>
                        <textarea className="form-control mb-2" value={address} onChange={e => setAddress(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Emergency Contact</label>
                        <input className="form-control mb-2" value={emergencyContact} onChange={e => setEmergencyContact(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Date of Birth</label>
                        <input
                            type="date"
                            className="form-control mb-2"
                            value={dob}
                            onChange={e => setDob(e.target.value)}
                        />
                    </div>
                    
                    <div className="text-center">
                        <button className="btn color-1 text-white px-4 py-2 rounded" onClick={handleUpdate}>
                            Update Profile
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default PatientProfile;
