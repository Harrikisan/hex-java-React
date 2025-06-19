import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";


function PatientProfile(){

    const navigate = useNavigate()
    const token =localStorage.getItem('token');
    const role = localStorage.getItem('role');
    useEffect(() => {
            if (token == null || token == undefined || token == "" || role !== "PATIENT") {
            navigate("/login");
        }
    
        }, []);
    return(
        <div>Profile</div>
    )
}

export default PatientProfile