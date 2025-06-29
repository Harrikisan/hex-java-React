import { useState, useEffect } from "react";
import axios from "axios";
import { useSelector } from "react-redux";

function AddBed() {
    const [number, setNumber] = useState("");
    const [wardId, setWardId] = useState("");
    const [wards, setWards] = useState([]);
    const [msg, setMsg] = useState("");

    const token = useSelector((state) => state.user.token);

    useEffect(() => {
        axios
            .get("http://localhost:8080/api/ward/all", {
                headers: { Authorization: "Bearer " + token },
            })
            .then((res) => setWards(res.data))
            .catch((err) => console.error("Error fetching wards", err));
    }, [token]);

    const addBed = async () => {
        try {
            await axios.post(
                `http://localhost:8080/api/bed/add/${wardId}`,
                { number: number },
                { headers: { Authorization: "Bearer " + token } }
            );
            setMsg("Bed added successfully");
        } catch (err) {
            setMsg("Failed to add bed");
            console.error(err);
        }
    };

    return (
        <div className="container mt-4">
            <div className="col-lg-8 offset-lg-2">
                <div className="card">
                    <div className="card-header color-1 text-color-4">
                        <h4>Add Bed</h4>
                    </div>

                    <div className="card-body color-3">
                        {
                            msg !== "" ?
                                msg === "Bed added successfully" ?
                                    <div className="alert alert-primary">{msg}</div> :
                                    <div className="alert alert-primary">{msg}</div> :
                                ""
                        }

                        <div className="mb-3">
                            <label className="text-color-1">Bed Number</label>
                            <input
                                type="text"
                                value={number}
                                onChange={(e) => setNumber(e.target.value)}
                                className="form-control color-4"
                            />
                        </div>

                        <div className="mb-3">
                            <label className="text-color-1">Ward</label>
                            <select
                                value={wardId}
                                onChange={(e) => setWardId(e.target.value)}
                                className="form-control color-4"
                            >
                                <option value="">Select Ward</option>
                                {wards.map((ward) => (
                                    <option key={ward.id} value={ward.id}>
                                        {ward.ward_number || `Ward ${ward.id}`}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>

                    <div className="card-footer text-end color-2">
                        <button className="button" onClick={addBed}>
                            Add Bed
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AddBed;
