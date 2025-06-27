import axios from "axios";

// Do NOT import or use useSelector here.

export const getAppointments = (dispatch) => (pagination, token) => {
  const getAppointmentByPatient = async () => {
    try {
      const response = await axios.get(
        'http://localhost:8080/api/doctor/appointment/get-by-patient',
        {
          headers: { Authorization: 'Bearer ' + token },
          params: {
            page: pagination.page,
            size: pagination.size,
          },
        }
      );

      console.log(response.data);

      dispatch({
        type: "GET_APPOINTMENT_BY_PATIENT",
        payload: response.data,
      });

    } catch (error) {
      console.error("Error fetching appointments:", error);
    }
  };

  getAppointmentByPatient();
};

export const getAppointmentsByDoctor = (dispatch) => {
  const getAppointmentByPatient = async () => {
    try {
      const response = await axios.get(
        'http://localhost:8080/api/doctor/appointment/get-by-doctor',
        {
          headers: { Authorization: 'Bearer ' + localStorage.getItem('token') },
        }
      );

      console.log(response.data);

      dispatch({
        type: "GET_APPOINTMENT_BY_DOCTOR",
        payload: response.data,
      });

    } catch (error) {
      console.error("Error fetching appointments:", error);
    }
  };

  getAppointmentByPatient();
};
