import { useEffect, useState } from "react";
import '../../Componentcss/Doctor/EditSchedule.css';
import axios from "axios";
import { useSelector } from 'react-redux';

function EditSchedule() {
    const days = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"];

    const timeSlots = {
        ONE: "9:00 - 10:00",
        TWO: "10:00 - 11:00",
        THREE: "11:00 - 12:00",
        FOUR: "12:00 - 1:00",
        FIVE: "1:00 - 2:00",
        SIX: "2:00 - 3:00",
        SEVEN: "3:00 - 4:00",
        EIGHT: "4:00 - 5:00"
    }; //{key,values}={ONE: "9:00 - 10:00"}

    const token = useSelector(state => state.user.token);
    const [schedule, setSchedule] = useState({});

    useEffect(() => {
        Schedule();
    }, []);

    const Schedule = async () => {
        try {
            const res = await axios.get("http://localhost:8080/api/doctor/schedule/get-by-doctor", {
                headers: { Authorization: `Bearer ${token}` }
            });

            const scheduleData = {};
            res.data.forEach(item => {
                const key = `${item.day}_${item.slot}`;
                scheduleData[key] = { status: item.status, id: item.id };
            });
            //key will be MONDAY_ONE and value will be AVAILABLE,{id from db}

            setSchedule(scheduleData);
        } catch (err) {
            console.error("Failed to load schedule", err);
        }
    };

    const toggleStatus = async (id, currentStatus) => {
        const newStatus = currentStatus === "AVAILABLE" ? "NOTAVAILABLE" : "AVAILABLE";

        try {
            await axios.put(`http://localhost:8080/api/doctor/schedule/editStatus/${id}?status=${newStatus}`, {}, {
                headers: { Authorization: `Bearer ${token}` }
            });

            Schedule();
        } catch (err) {
            console.error("Failed to update status", err);
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
                                {Object.values(timeSlots).map(time => (
                                    <th key={time}>{time}</th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            {days.map(day => (
                                <tr key={day}>
                                    <td className="font-weight-bold text-color-1">{day}</td>
                                    {Object.entries(timeSlots).map(([slot, _]) => {
                                        const key = `${day}_${slot}`; //MONDAY_ONE
                                        const entry = schedule[key];

                                        if (!entry) {
                                            return <td key={slot}>-</td>;
                                        }

                                        const isAvailable = entry.status === "AVAILABLE";
                                        const display = isAvailable ? "ACTIVE" : "INACTIVE";
                                        const btnClass = isAvailable ? "color-1 text-white" : "color-3 text-color-1";

                                        return (
                                            <td key={slot}>
                                                <button
                                                    className={`btn btn-sm ${btnClass}`}
                                                    style={{
                                                        borderRadius: "20px",
                                                        padding: "4px 10px",
                                                        fontSize: "12px",
                                                        fontWeight: "600",
                                                        transition: "background 0.3s"
                                                    }}
                                                    onClick={() => toggleStatus(entry.id, entry.status)}
                                                >
                                                    {display}
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


//How stuff works
/*
1. DAYS-MONDAY,TUESDAY,....
2. SLOT-ONE,TWO,THREE,.....(but for end users understanding 9:00-10:00,10:00-11:00,..)
3. Getting schedule from doctor schedule and storing them in [schedule] 
    but setting the key and values as {MONDAY_ONE}={{id from db},MONDAY,ONE,{status}}.
4.Edit -> when active is clicked ,it goes to edit status->
        1.toggles active to not active.
        2.id passed to this function from return [entry.id,entry.status], then  it is updated
5. Task done-> Happy :) 
*/