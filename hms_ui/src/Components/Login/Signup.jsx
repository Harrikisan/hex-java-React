import { useState } from 'react';
import { useNavigate } from 'react-router-dom'
import axios from 'axios'
import '../../Componentcss/Login/Signup.css';

function Signup() {
    const [name, setName] = useState("")
    const [contact, setContact] = useState("")
    const [email, setEmail] = useState("")
    const [address, setAddress] = useState("")
    const [emergencyContact, setEmergencyContact] = useState("")
    const [gender, setGender] = useState("")
    const [dob, setDob] = useState("")
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [msg, setMsg] = useState("")

    const navigate = useNavigate()

    const registerPatient = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/patient/insert',
                {
                    name: name,
                    gender: gender,
                    phone: contact,
                    email: email,
                    address: address,
                    emergencyContact: emergencyContact,
                    dob: dob,
                    user: {
                        username: username,
                        password: password
                    }
                }
            )
            console.log(response)
            setMsg("Registered Successfully")
        } catch (error) {
            setMsg("Failed: check inputs")
        }

    }

    return (
        <div className="signup_card">
            <div className="card-header">Signup</div>
            <div className="card-body">

                {
                    msg != "" ?
                        msg == "Registered Successfully" ? <div className='alert alert-primary'>Login Successful!!</div> :
                            <div className='alert alert-danger'>Login failed</div> :
                        ""
                }
                <div>
                    <label>Name:</label>
                    <input type="text" className="form-control"
                        onChange={(e) => setName(e.target.value)} />
                </div>

                <div>
                    <label>Contact:</label>
                    <input type="text" className="form-control"
                        onChange={(e) => setContact(e.target.value)} />
                </div>

                <div>
                    <label>Email:</label>
                    <input type="text" className="form-control"
                        onChange={(e) => setEmail(e.target.value)} />
                </div>

                <div>
                    <label>Address:</label>
                    <textarea className="form-control"
                        onChange={(e) => setAddress(e.target.value)} />
                </div>

                <div>
                    <label>Emergency Contact:</label>
                    <input type="text" className="form-control"
                        onChange={(e) => setEmergencyContact(e.target.value)} />
                </div>

                <div>
                    <label>Gender:</label>
                    <select className="form-control"
                        onChange={(e) => setGender(e.target.value)}>
                        <option value="">Select</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                        <option value="Prefer not to say">Prefer not to say</option>
                    </select>
                </div>

                <div>
                    <label>DOB (YYYY/MM/DD):</label>
                    <input type="text" className="form-control"
                        onChange={(e) => setDob(e.target.value)} />
                </div>

                <div>
                    <label>Username:</label>
                    <input type="text" className="form-control"
                        onChange={(e) => setUsername(e.target.value)} />
                </div>

                <div>
                    <label>Password:</label>
                    <input type="password" className="form-control"
                        onChange={(e) => setPassword(e.target.value)} />
                </div>

            </div>

            <div className="card-footer">
                <div>
                    <button className="btn-submit" onClick={() => registerPatient()}>Register</button>
                </div>
                <div>
                    Already have an account? <span className="signup-link" onClick={() => navigate('/login')}>Login</span>
                </div>
            </div>
        </div>
    );

}

export default Signup;
