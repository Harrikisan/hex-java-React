//src/store/Reducers/DoctorReducer

const initializeState={
    doctors:[]
}

const DoctorReducer=(state=initializeState,action)=>{
    if(action.type==="GET_ALL_DOCTORS"){
        return{
            ...state,
            doctors:action.payload
        }
    }
    
    return state
}

export default DoctorReducer