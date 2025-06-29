import axios from "axios";
import { useState } from "react";
import { useSelector } from "react-redux";
import "../../Componentcss/Login/Login.css"
function AddLab() {
    const [name, setName] = useState("");
    const [location, setLocation] = useState("");
    const [contact, setContact] = useState("");
    const token=useSelector(state=>state.user.token);
    const [msg,setmsg]=useState('')
    const addLab=async()=>{
        axios.post(`http://localhost:8080/api/lab/add`,{
            name:name,
            location:location,
            contact:contact
        },{
            headers:{'Authorization':'Bearer '+token}
        }).then(res=>setmsg("Lab added Successfully"))
        .catch(error=>setmsg("Failed to add lab"))
        
    }
    return (
        <div className="container mt-4">
            <div className="col-lg-8 offset-lg-2">
                <div className="card">
                    <div className="card-header color-1 text-color-4"><h4>Add Lab</h4></div>
                    <div className="card-body color-3">
                        {
                            msg!==""?
                            msg=="Lab added Successfully"?
                            <div className="alert alert-primary">{msg}</div>:
                            <div className="alert alert-primary">{msg}</div>:""
                        }
                        <div className="mb-3">
                            <label className="text-color-1" >Lab Name</label>
                            <input
                                type="text"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                className="form-control color-4"
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <label className="text-color-1">Location</label>
                            <input
                                type="text"
                                value={location}
                                onChange={(e) => setLocation(e.target.value)}
                                className="form-control color-4"
                            />
                        </div>

                        <div className="mb-3">
                            <label className="text-color-1">Contact</label>
                            <input
                                type="text"
                                value={contact}
                                onChange={(e) => setContact(e.target.value)}
                                className="form-control color-4"
                            />
                        </div>
                    </div>
                    <div className="card-footer text-end color-2">
                        <button className="button" onClick={()=>addLab()}>
                            Add Lab
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AddLab;
