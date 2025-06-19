//src/store/Reducers/UserReducer

const initializeState={
    username:"",
    role:"",
    token:""
}

const UserReducer=(state=initializeState,action)=>{

    if(action.type=== "SET_USER_DETAILS"){
        localStorage.setItem('token',action.payload.token)
        localStorage.setItem('role',action.payload.role)
        let user=action.payload
        console.log(user)
        return{
            ...state,
            username: user.username,
            role: user.role,
            token:user.token
        }
    }

    return state;
}

export default UserReducer

/**
 * UserReducer has 2 paramenters (state,action)
 * 
 * 1. state is 
 * {
 *      username:"",
 *      role:""
 * } initially
 * 
 * 2.action is a String which is passed from UserAction
 * 
 * dispatch({
        "payload":user,
        "type":"SET_USER_DETAILS"
    })<-action

    state is updated to action.payload and returned when the action is "SET_USER_DETAILS" 
 * 
*/