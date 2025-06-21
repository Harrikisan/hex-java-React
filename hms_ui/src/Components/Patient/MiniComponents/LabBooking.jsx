import React, { useEffect, useState } from 'react';
import '../../../Componentcss/Patient/MiniComponents/BedBooking.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useSelector } from 'react-redux';

function LabBooking() {
  const [patientId, setPatientId] = useState('');
  const [patientName, setPatientName] = useState('');
  const [admissionDate, setAdmissionDate] = useState('');
  const [dischargeDate, setDischargeDate] = useState('');
  const [wardNumber, setWardNumber] = useState('');
  const [roomNumber, setRoomNumber] = useState('');
  const [bedNumber, setBedNumber] = useState('');
  const login = useSelector(state => state.user.login);
  const navigate = useNavigate()
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');
  useEffect(() => {

    if (!login || role !== "PATIENT") {
      navigate("/login");
    }

    const getPatientById = async () => {
      const response = await axios.get(`http://localhost:8080/api/patient/get-by-id/${patientId}`, {
        headers: { Authorization: 'Bearer ' + token }
      })
      console.log(response.data)
    }

    const getData = () => {

      const getPatient = async () => {
        const response = await axios.get('http://localhost:8080/api/patient/get', {
          headers: { 'Authorization': 'Bearer ' + token }
        })
        console.log(response.data)
        setPatientId(response.data.id)
        setPatientName(response.data.name)
      }

      getPatient();

    }
    getData()

  },);

  return (
    <div className="card bed-card">
      <div className="card-header color-1 text text-color-4">Test Booking</div>

      <div className="card-body color-3">
        <div>
          <label className='text text-color-1 line-height' >Patient ID</label>
          <input type="text" className="form-control color-4" value={patientId}
            onChange={(e) => setPatientId(e.target.value)}
            required
          />
        </div>

        <div>
          <label className='text text-color-1 line-height'
          >
            Patient Username</label>
          <input type="text" className="form-control color-4"
            value={localStorage.getItem('username')}
            onChange={(e) => setPatientName(e.target.value)}
            required
          />
        </div>
        <div>
          <label className='text text-color-1 line-height'
          >
            Patient Name</label>
          <input type="text" className="form-control color-4"
            value={patientName}
            onChange={(e) => setPatientName(e.target.value)}
            required
          />
        </div>


        <div>
          <label className='text text-color-1 line-height'>Date</label>
          <input type="date" className="form-control color-4"
            onChange={(e) => setAdmissionDate(e.target.value)}
            required
          />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Slot</label>
          <input type="text" className="form-control color-4"
            onChange={(e) => setDischargeDate(e.target.value)}
          />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Lab</label>
          <input type="text" className="form-control color-4"
            onChange={(e) => setWardNumber(e.target.value)}
            required
          />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Test</label>
          <input type="text" className="form-control color-4"
            onChange={(e) => setRoomNumber(e.target.value)}
            required
          />
        </div>

      </div>

      <div className="card-footer color-2">
        <button className="btn btn-primary color-1 text text-color-4">Submit</button>
      </div>
    </div>
  );
}

export default LabBooking;
