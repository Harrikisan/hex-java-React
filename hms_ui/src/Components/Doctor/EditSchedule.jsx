import { useEffect, useState } from "react";
import '../../Componentcss/Doctor/EditSchedule.css';
import axios from "axios";
import { useSelector } from 'react-redux';

function EditSchedule() {
    const days = [
        "MONDAY", "TUESDAY", "WEDNESDAY",
        "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
    ];

    const timeSlots = {
        ONE: "9:00 - 10:00",
        TWO: "10:00 - 11:00",
        THREE: "11:00 - 12:00",
        FOUR: "12:00 - 1:00",
        FIVE: "1:00 - 2:00",
        SIX: "2:00 - 3:00",
        SEVEN: "3:00 - 4:00",
        EIGHT: "4:00 - 5:00"
    };

    const token = useSelector(state => state.user.token);
    const [buttonStates, setButtonStates] = useState({});

    useEffect(() => {
        getSchedule();
    }, []);

    const getSchedule = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/doctor/schedule/get-by-doctor`, {
                headers: { 'Authorization': 'Bearer ' + token }
            });

            const temp = {};
            response.data.forEach(entry => {
                const key = `${entry.day}_${entry.slot}`;
                temp[key] = {
                    status: entry.status,  // "AVAILABLE" or "NOTAVAILABLE"
                    id: entry.id
                };
            });
            setButtonStates(temp);
        } catch (error) {
            console.log(error);
        }
    };

    const editStatus = async (recordId, newStatus) => {
        try {
            await axios.put(`http://localhost:8080/api/doctor/schedule/editStatus/${recordId}?status=${newStatus}`, {}, {
                headers: { 'Authorization': 'Bearer ' + token }
            });
            console.log(`Updated ${recordId} to ${newStatus}`);
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <div className="schedule_container">
            <div className="login_card">
                <div className="header p-3 text-center">
                    <h2 className="text">Schedule</h2>
                </div>
                <div className="table-responsive">
                    <table className="table text text-center">
                        <thead className="color-2 text-white">
                            <tr>
                                <th>Day</th>
                                {Object.entries(timeSlots).map(([_, time]) => (
                                    <th key={time}>{time}</th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            {days.map(day => (
                                <tr key={day}>
                                    <td className="font-weight-bold text-color-1">{day}</td>
                                    {Object.keys(timeSlots).map(slot => {
                                        const key = `${day}_${slot}`;
                                        const entry = buttonStates[key] || {};
                                        const currentStatus = entry.status || "NOTAVAILABLE";
                                        const displayStatus = currentStatus === "AVAILABLE" ? "ACTIVE" : "INACTIVE";

                                        return (
                                            <td key={slot}>
                                                <button
                                                    className={`btn btn-sm ${currentStatus === "AVAILABLE"
                                                        ? "color-1 text-white"
                                                        : "color-3 text-color-1"}`}
                                                    onClick={() => {
                                                        const newStatus = currentStatus === "AVAILABLE" ? "NOTAVAILABLE" : "AVAILABLE";
                                                        setButtonStates(prev => ({
                                                            ...prev,
                                                            [key]: {
                                                                ...entry,
                                                                status: newStatus
                                                            }
                                                        }));
                                                        editStatus(entry.id, newStatus);
                                                    }}
                                                    style={{
                                                        borderRadius: "20px",
                                                        padding: "4px 10px",
                                                        fontSize: "12px",
                                                        fontWeight: "600",
                                                        transition: "background 0.3s"
                                                    }}
                                                >
                                                    {displayStatus}
                                                </button>
                                            </td>
                                        );
                                    })}
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default EditSchedule;
