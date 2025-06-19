//src/store/Actions/DoctorActions
import axios from "axios"

export const getAllDoctors=(dispatch)=>(pagination)=>{

    axios.get('http://localhost:8080/api/doctor/get-all-listing',{
        params:{
            page:pagination.page,
            size:pagination.size
        }
    })
    .then(function(response){
        // console.log(response)
        dispatch({
            'payload':response.data,
            'type':"GET_ALL_DOCTORS"
        })
    })
}

