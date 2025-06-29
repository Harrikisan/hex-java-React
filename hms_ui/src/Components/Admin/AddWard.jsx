import { useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";

function AddWard() {
    const [wardNumber, setWardNumber] = useState("");
    const [msg, setMsg] = useState("");

    const token = useSelector(state => state.user.token);

    const addWard = async () => {
        try {
            await axios.post("http://localhost:8080/api/ward/add", {}, {
                params: {
                    wardNumber: wardNumber
                },
                headers: {
                    Authorization: "Bearer " + token
                }
            });

            setMsg("Ward added successfully!");
        } catch (error) {
            console.error("Error adding ward:", error);
            setMsg("Failed to add ward.");
        }
    };

    return (
        <div className="container mt-4">
            <div className="col-lg-8 offset-lg-2">
                <div className="card">
                    <div className="card-header color-1 text-color-4"><h4>Add Ward</h4></div>
                    <div className="card-body color-3">
                        {
                            msg!==""?
                            msg=="Ward added Successfully"?
                            <div className="alert alert-primary">{msg}</div>:
                            <div className="alert alert-primary">{msg}</div>:""
                        }

                        <div className="mb-3">
                            <label className="text-color-1">Ward Number</label>
                            <input
                                type="text"
                                value={wardNumber}
                                onChange={(e) => setWardNumber(e.target.value)}
                                className="form-control color-4"
                                required
                            />
                        </div>
                    </div>

                    <div className="card-footer text-end color-2">
                        <button className="button" onClick={addWard}>Add Ward</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AddWard;
