import React, { useEffect, useState } from 'react';
import '../../../Componentcss/Patient/MiniComponents/BedBooking.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { getPatientDetails, getPatientDetailsById } from '../../../store/Actions/PatientAction';

function BedBooking() {
  const patient =useSelector(state=>state.patient.patient);
  const [patientId, setPatientId] = useState('');
  const [patientName, setPatientName] = useState('');
  const [admissionDate, setAdmissionDate] = useState('');
  const [dischargeDate, setDischargeDate] = useState('');
  const [ward, setWard] = useState([]);
  const [wardNo, setWardNo] = useState('');
  const [bed, setBed] = useState([]);
  const [BedNumber, setBedNumber] = useState('');
  const [username, setUsername] = useState('');
  const [msg, setMsg] = useState('');
  const login = useSelector(state => state.user.login);
  const navigate = useNavigate();
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');
  const dispatch=useDispatch();

  
  const getPatientById = async (id) => {
    // const response = await axios.get(`http://localhost:8080/api/patient/get-by-id/${id}`, {
    //   headers: { Authorization: 'Bearer ' + token }
    // });
    // console.log(response.data);
    getPatientDetailsById(dispatch)(id);
    let patient=useSelector(state=>state.patient)
    console.log(patient)
  };

  const BookAppointment = async () => {
  try {
    if (!admissionDate || !dischargeDate || !BedNumber) {
      setMsg("Please fill all required fields.");
      return;
    }

    const response = await axios.post(
      `http://localhost:8080/api/bed/appointment/book/${patientId}/${BedNumber}`,
      {
        adminssionDate: admissionDate,
        dischargeDate: dischargeDate,
      },
      {
        headers: {
          Authorization: 'Bearer ' + token,
        },
      }
    );

    console.log(response.data);
    setMsg("Bed booked successfully!");
  } catch (error) {
    console.error("Error booking bed:", error);
    setMsg("Error booking bed");
  }
};



  const getBeds = async (wardNumber) => {
    try {
      const response = await axios.get('http://localhost:8080/api/bed/available', {
        headers: { Authorization: 'Bearer ' + token },
      });
      const filteredBeds = response.data
        .filter(item => item.ward.ward_number === wardNumber)
        .map(item => ({ id: item.id, number: item.number }));
      setBed(filteredBeds);
    } catch (error) {
      console.error("Error fetching beds:", error);
    }
  };

  useEffect(() => {
    if (!login || role !== "PATIENT") {
      navigate("/login");
    }

    const getData = () => {
      getPatientDetails(dispatch);
      // console.log(patient.patient)
      setPatientId(patient.id);
      setPatientName(patient.name);
      setUsername(patient.user.username);


      const getWard = async () => {
        try {
          const response = await axios.get('http://localhost:8080/api/ward/all', {
            headers: { Authorization: 'Bearer ' + token },
          });
          const temparr = response.data.map(item => item.ward_number);
          setWard(temparr);
        } catch (error) {
          console.error("Error fetching wards:", error);
        }
      };
      getWard();
    };

    getData();
  }, []);

  useEffect(() => {
    if (wardNo) {
      getBeds(wardNo);
    }
  }, [wardNo]);

  return (
    <div className="card bed-card">
      <div className="card-header color-1 text text-color-4">Bed Booking</div>

      <div className="card-body color-3">
        {msg !== "" && (
          msg === "Bed booked successfully!" ?
            <div className='alert alert-primary'>{msg}</div> :
            <div className='alert alert-danger'>{msg}</div>
        )}

        <div>
          <label className='text text-color-1 line-height'>Patient ID</label>
          <input
            type="text"
            className="form-control color-4"
            value={patientId}
            readOnly={role === "PATIENT"}
            onChange={(e) => {
              setPatientId(e.target.value);
              getPatientById(e.target.value);
            }}
            required
          />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Patient Username</label>
          <input
            type="text"
            className="form-control color-4"
            value={username}
            readOnly={role === "PATIENT"}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Patient Name</label>
          <input
            type="text"
            className="form-control color-4"
            value={patientName}
            readOnly={role === "PATIENT"}
            onChange={(e) => setPatientName(e.target.value)}
            required
          />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Admission Date</label>
          <input
            type="date"
            className="form-control color-4"
            value={admissionDate}
            onChange={(e) => setAdmissionDate(e.target.value)}
            required
          />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Discharge Date</label>
          <input
            type="date"
            className="form-control color-4"
            value={dischargeDate}
            onChange={(e) => setDischargeDate(e.target.value)}
          />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Ward Number</label>
          <select
            value={wardNo}
            onChange={(e) => setWardNo(e.target.value)}
            className="form-control color-4"
            required
          >
            <option value="">Select Ward</option>
            {ward.map((w, index) => (
              <option key={index} value={w}>{w}</option>
            ))}
          </select>
        </div>

        <div>
          <label className='text text-color-1 line-height'>Bed Number</label>
          <select
            value={BedNumber}
            onChange={(e) => setBedNumber(e.target.value)}
            className="form-control color-4"
            required
          >
            <option value="">Select Bed</option>
            {bed.map((b, index) => (
              <option key={index} value={b.id}>{b.number}</option>
            ))}
          </select>
        </div>
      </div>

      <div className="card-footer color-2">
        <button
          className="btn btn-primary color-1 text text-color-4"
          onClick={BookAppointment}
        >
          Submit
        </button>
      </div>
    </div>
  );
}

export default BedBooking;
