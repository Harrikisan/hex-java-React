// src/store/store.js

import { configureStore } from "@reduxjs/toolkit";
import UserReducer from "./Reducers/UserReducer";
import DoctorReducer from "./Reducers/DoctorReducer";
import { DoctorAppointmentReducer } from "./Reducers/DoctorAppointmentReducer";
import { PatientReducer } from "./Reducers/PatientReducer";

const store = configureStore({
  reducer: {
    user: UserReducer,
    doctors: DoctorReducer,
    doctorAppointment: DoctorAppointmentReducer,
    patient:PatientReducer
  }
});

export default store;
