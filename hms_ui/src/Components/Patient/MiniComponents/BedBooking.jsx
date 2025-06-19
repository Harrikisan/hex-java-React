import React, { useState } from 'react';
import '../../../Componentcss/Patient/MiniComponents/BedBooking.css';

function BedBooking() {
  const [patientId, setPatientId] = useState('');
  const [admissionDate, setAdmissionDate] = useState('');
  const [dischargeDate, setDischargeDate] = useState('');
  const [wardNumber, setWardNumber] = useState('');
  const [roomNumber, setRoomNumber] = useState('');
  const [bedNumber, setBedNumber] = useState('');


  return (
    <div className="card bed-card">
      <div className="card-header color-1 text text-color-4">Bed Booking</div>

      <div className="card-body color-3">
        <div>
          <label className='text text-color-1 line-height'>Patient ID</label>
          <input
            type="text"
            className="form-control color-4"
            value={patientId}
            onChange={(e) => setPatientId(e.target.value)}
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
          <input
            type="text"
            className="form-control color-4"
            value={wardNumber}
            onChange={(e) => setWardNumber(e.target.value)}
            required
          />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Room Number</label>
          <input
            type="text"
            className="form-control color-4"
            value={roomNumber}
            onChange={(e) => setRoomNumber(e.target.value)}
            required
          />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Bed Number</label>
          <input
            type="text"
            className="form-control color-4"
            value={bedNumber}
            onChange={(e) => setBedNumber(e.target.value)}
            required
          />
        </div>
      </div>

      <div className="card-footer color-2">
        <button
          className="btn btn-primary color-1 text text-color-4"
          
        >
          Submit
        </button>
      </div>
    </div>
  );
}

export default BedBooking;
