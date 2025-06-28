import { useEffect } from "react";
import { useNavigate } from "react-router-dom"

function Home(){

    localStorage.clear()
    const navigate = useNavigate();

    useEffect(()=>{
        navigate("/login");
    },[])
    return(
        <div><h1>Home</h1></div>
    )
}

export default Home