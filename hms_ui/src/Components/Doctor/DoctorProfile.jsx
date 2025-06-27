import { useEffect, useState } from 'react';
import '../../Componentcss/Doctor/DoctorProfile.css';
import axios from 'axios';
import { useSelector } from 'react-redux';

function DoctorProfile() {
    const token = useSelector(state => state.user.token);
    const username = useSelector(state => state.user.username);

    const [name, setName] = useState('');
    const [specilization, setSpecilization] = useState('');
    const [qualification, setQualification] = useState('');
    const [yearsOfExperience, setYearsOfExperience] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [address, setAddress] = useState('');
    const [userStatus, setUserStatus] = useState('');
    const [image, setImage] = useState(null);
    const [previewUrl, setPreviewUrl] = useState(null);

    useEffect(() => {
        axios.get("http://localhost:8080/api/doctor/profile", {
            headers: { Authorization: 'Bearer ' + token }
        }).then(res => {
            const data = res.data;
            setName(data.name);
            setSpecilization(data.specilization);
            setQualification(data.qualification);
            setYearsOfExperience(data.yearsOfExperience);
            setEmail(data.email);
            setPhone(data.phone);
            setAddress(data.address);
            setUserStatus(data.userStatus);
            if (data.imageUrl) {
                setPreviewUrl(`/images/${data.imageUrl}`);
            }

        }).catch(err => {
            console.error("Failed to fetch doctor profile:", err);
        });
    }, []);

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        setImage(file);
        setPreviewUrl(URL.createObjectURL(file));
    };

    const handleUpdate = async () => {
        const updatedData = {
            name,
            specilization,
            qualification,
            yearsOfExperience,
            email,
            phone,
            address,
            userStatus
        };

        try {
            // Update doctor info
            await axios.put("http://localhost:8080/api/doctor/update-info", updatedData, {
                headers: {
                    Authorization: 'Bearer ' + token
                }
            });

            // Upload image if selected
            if (image) {
                const formData = new FormData();
                formData.append("file", image);

                // ✅ Fixed URL
                await axios.post("http://localhost:8080/api/doctor/upload/profile-pic", formData, {
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

                {/* Profile Picture Section */}
                <div className="profile-image-section">
                    <img
                        src={previewUrl || '/default-doctor.png'}
                        alt="Profile"
                        className="profile-image mb-3"
                    />
                    <input type="file" accept="image/*" onChange={handleImageChange} />
                </div>

                {/* Profile Info Section */}
                <div className="profile-info p-4">
                    <div className="form-group">
                        <label className="text-color-1">Name</label>
                        <input className="form-control mb-2" value={name} onChange={e => setName(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Specialization</label>
                        <input className="form-control mb-2" value={specilization} onChange={e => setSpecilization(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Qualification</label>
                        <input className="form-control mb-2" value={qualification} onChange={e => setQualification(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Experience (Years)</label>
                        <input className="form-control mb-2" value={yearsOfExperience} onChange={e => setYearsOfExperience(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Email</label>
                        <input className="form-control mb-2" value={email} onChange={e => setEmail(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Phone</label>
                        <input className="form-control mb-2" value={phone} onChange={e => setPhone(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Address</label>
                        <textarea className="form-control mb-2" value={address} onChange={e => setAddress(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label className="text-color-1">Status</label>
                        <input className="form-control mb-3" value={userStatus} disabled />
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

export default DoctorProfile;
