import axios from "axios";

// disparch will be passed by Redux Thunk internally
export const getPatientDetails = (disparch) => {
    axios.get('http://localhost:8080/api/patient/get', {
        headers: { Authorization: 'Bearer ' + localStorage.getItem('token') },
    })
        .then(function (response) {
            disparch({
                type: "GET_PATIENT_DETAILS",
                payload: response.data
            });
        })
        .catch(function (error) {
            console.error("Error fetching patient details:", error);
        });
};


export const getPatientDetailsById = (dispatch) => (id) => {
    axios.get(`http://localhost:8080/api/patient/get-by-id/${id}`, {
        headers: { Authorization: 'Bearer ' + localStorage.getItem('token') }
    })
    .then(function (response) {
            disparch({
                type: "GET_PATIENT_DETAILS_BY_ID",
                payload: response.data
            });
        })
        .catch(function (error) {
            console.error("Error fetching patient details:", error);
        });
}