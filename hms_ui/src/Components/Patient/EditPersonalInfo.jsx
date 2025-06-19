import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";


function EditPersonalInfo(){

    const navigate = useNavigate()
    const token =localStorage.getItem('token');
    const role = localStorage.getItem('role');
    useEffect(() => {
            
            if (token == null || token == undefined || token == "" || role !== "PATIENT") {
            navigate("/login");
        }
    
        }, []);
    return(
        <div>Edit Personal info</div>
    )
}
export default EditPersonalInfo