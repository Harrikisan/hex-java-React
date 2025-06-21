const initializeState={
    patient:[]
}

export function PatientReducer(state=initializeState,action){
    if(action.type=="GET_PATIENT_DETAILS"){
        return{
            ...state,
            patient:action.payload
        }
    }
    if(action.type=="GET_PATIENT_DETAILS_BY_ID"){
        return{
            ...state,
            patient:action.payload
        }
    }
    return state;
}