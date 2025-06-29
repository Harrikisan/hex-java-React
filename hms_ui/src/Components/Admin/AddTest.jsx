import { useEffect, useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";
import "../../Componentcss/Login/Login.css";

function AddTest() {
    const [testType, setTestType] = useState("");
    const [labId, setLabId] = useState("");
    const [labs, setLabs] = useState([]);
    const [msg, setMsg] = useState("");

    const token = useSelector(state => state.user.token);

    useEffect(() => {
        axios.get("http://localhost:8080/api/lab/get-all", {
            headers: { Authorization: "Bearer " + token }
        })
        .then(res => setLabs(res.data))
        .catch(err => console.error("Error fetching labs", err));
    }, [token]);

    const addTest = async () => {
        try {
            await axios.post(`http://localhost:8080/api/lab/test/add/${labId}`, {
                testType: testType,
                lab: {
                    id: labId
                }
            }, {
                headers: { Authorization: "Bearer " + token }
            });
            setMsg("Test added successfully!");
        } catch (error) {
            console.error("Error adding test", error);
            setMsg("Failed to add test.");
        }
    };

    return (
        <div className="container mt-4">
            <div className="col-lg-8 offset-lg-2">
                <div className="card">
                    <div className="card-header color-1 text-color-4"><h4>Add Test</h4></div>
                    <div className="card-body color-3">
                        {
                            msg !== "" ?
                            msg === "Test added successfully!" ?
                            <div className="alert alert-primary">{msg}</div> :
                            <div className="alert alert-primary">{msg}</div> : ""
                        }

                        <div className="mb-3">
                            <label className="text-color-1">Test Type</label>
                            <input
                                type="text"
                                value={testType}
                                onChange={(e) => setTestType(e.target.value)}
                                className="form-control color-4"
                            />
                        </div>

                        <div className="mb-3">
                            <label className="text-color-1">Lab</label>
                            <select
                                value={labId}
                                onChange={(e) => setLabId(e.target.value)}
                                className="form-control color-4"
                            >
                                <option value="">Select Lab</option>
                                {labs.map(lab => (
                                    <option key={lab.id} value={lab.id}>
                                        {lab.name} ({lab.location})
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>
                    <div className="card-footer text-end color-2">
                        <button className="button" onClick={addTest}>
                            Add Test
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AddTest;
