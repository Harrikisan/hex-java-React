//src/store/Actions/UserActions

export const setUserDetails=(dispatch)=>(user)=>{
    dispatch({
        "payload":user,
        "type":"SET_USER_DETAILS"
    })
}


/**
 * if user logins 
 * user={
 *      username:"arunprakash.dev99@gmail.com"
 *      role:"PATIENT"
 * }
 * 
 * dispatch is passed by 
 * 
 * dispatch=useDispatch(),
 * 
 * user is passed to
 *  
 * setUserDetails(dispatch)(user)
 * 
 * this{
 *      payload:user
 *      type:"SET_USER_DISPATCH" 
 * } is get attached to "action" in UserReducer
*/