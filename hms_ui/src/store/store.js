// src/store/store.js

import { configureStore } from "@reduxjs/toolkit";
import UserReducer from "./Reducers/UserReducer";
import DoctorReducer from "./Reducers/DoctorReducer";
import { DoctorAppointmentReducer } from "./Reducers/DoctorAppointmentReducer";

const store = configureStore({
  reducer: {
    user: UserReducer,
    doctors: DoctorReducer,
    doctorAppointment: DoctorAppointmentReducer,
  }
});

export default store;
