const initializeState = {
  appointments: []
};

export const DoctorAppointmentReducer = (state = initializeState, action) => {
  if (action.type === "GET_APPOINTMENT_BY_PATIENT") {
    return {
      ...state,
      appointments: action.payload 
    };
  }
  if (action.type === "GET_APPOINTMENT_BY_DOCTOR") {
    return {
      ...state,
      appointments: action.payload 
    };
  }
  return state;
};
