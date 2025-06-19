import { useState } from 'react';
import '../../Componentcss/Login/Signup.css';

function AddDoctor() {
  const [specialization, setSpecialization] = useState("");
  const [name,setName]=useState("")
  const [contact,setContact]=useState("")
  const [email,setEmail]=useState("")
  const [address,setAddress]=useState("")
  const [qualification,setQualification]=useState("")
  const [yearsOfExp,setYearsOfExp]=useState("")
  const [username,setUsername]=useState("")
  const [password,setPassword]=useState("")

  return (
    <div className="signup_card">
      <div className="card-header">Signup</div>
      <div className="card-body">
        <div>
          <label>Name:</label>
          <input type="text" className="form-control" />
        </div>

        <div>
          <label>Specialization:</label>
          <select
            className="form-control"
            value={specialization}
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
          <label>Contact:</label>
          <input type="text" className="form-control" />
        </div>

        <div>
          <label>Email:</label>
          <input type="text" className="form-control" />
        </div>

        <div>
          <label>DOB (YYYY/MM/DD):</label>
          <input type="text" className="form-control" />
        </div>

        <div>
          <label>Address:</label>
          <textarea className="form-control" />
        </div>

        <div>
          <label>Qualification:</label>
          <input type="text" className="form-control" />
        </div>

        <div>
          <label>Years of Experience:</label>
          <input type="text" className="form-control" />
        </div>

        <div>
          <label>Username:</label>
          <input type="text" className="form-control" />
        </div>

        <div>
          <label>Password:</label>
          <input type="password" className="form-control" />
        </div>

        <div>
          <button className="btn-submit">Register</button>
        </div>
      </div>

      <div className="card-footer">
        Already have an account? <span className="signup-link">Login</span>
      </div>
    </div>
  );
}

export default AddDoctor;
