const initializeState = {
  patient: []
};

export const DoctorAppointmentReducer = (state = initializeState, action) => {
  if (action.type === "GET_APPOINTMENT_BY_PATIENT") {
    return {
      ...state,
      patient: action.payload 
    };
  }
  return state;
};
