import { useEffect, useState } from "react";
import axios from "axios";

function FetchUsers() {
    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    const [gender, setGender] = useState('')
    const [status, setStatus] = useState('')
    const [users, setUsers] = useState([]);
    const [msg, setMsg] = useState('')
    const token = '7600baa711c394b765cd3d2e9e9a1ee1b54bc8453d32a7f111f8fb1d8e346ce1';
    const [edit, setEdit] = useState(false)
    const [add, setAdd] = useState(false)
    const [id, setId] = useState('')
    useEffect(() => {
        getUsers();
    }, []);

    const getUsers = async () => {
        try {
            const response = await axios.get('https://gorest.co.in/public/v2/users');
            // console.log(response.data)
            setUsers(response.data);
            console.log(users)
        } catch (error) {
            console.error(error);
        }
    };

    const deleteUser = async (id) => {
        try {
            await axios.delete(`https://gorest.co.in/public/v2/users/${id}`, {
                headers: { 'Authorization': 'Bearer ' + token }
            });

            const temp = users.filter((t) => t.id !== id);
            setUsers(temp);
            setMsg("Delete successful")
        } catch (error) {
            console.log(error);
            setMsg("Delete failed")
        }
    };

    const AddUser = async () => {
        if (gender === "select" || status === "select") {
            setMsg("All fields are required with valid values.");
            return;
        }

        try {
            const response = await axios.post('https://gorest.co.in/public/v2/users', {
                name: name,
                email: email,
                gender: gender,
                status: status
            }, {
                headers: { 'Authorization': 'Bearer ' + token }
            });

            const user = response.data;
            setUsers([...users, user]);
            setMsg("User added successfully");
            setAdd(false)
        } catch (error) {
            console.log(error)
            setMsg("Failed to add user");
        }
    }

    const getByid = async (id) => {
        setEdit(true)
        setId(id)
        try {
            const response = await axios.get(`https://gorest.co.in/public/v2/users/${id}`);
            console.log(response.data);
            setName(response.data.name);
            setEmail(response.data.email);
            setGender(response.data.gender);
            setStatus(response.data.status);
        } catch (error) {
            console.error("Error fetching user:", error);
        }
    }

    const UpdateUser = async () => {
        try {
            const response = await axios.put(`https://gorest.co.in/public/v2/users/${id}`, {
                name: name,
                email: email,
                gender: gender,
                status: status
            }, {
                headers: { 'Authorization': 'Bearer ' + token }
            })
            setId('')
            // console.log(response.data)
            const user = response.data;
            const tempArr = users.map(u =>
                u.id === user.id ? user : u
            );
            setUsers(tempArr)
            setMsg("Update Succeffful")
            setEdit(false)
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div>
            <div className="container mt-5">
                <div className="card mt-5">
                    <div className="card-header"> Users </div>
                    <div className="card-body">
                        <table className="table table-striped">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Gender</th>
                                    <th>Status</th>
                                    <th>Edit</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                {users.map((user) => (
                                    <tr key={user.id}>
                                        <td>{user.name}</td>
                                        <td>{user.email}</td>
                                        <td>{user.gender}</td>
                                        <td>{user.status}</td>
                                        <td><button className="btn btn-primary"
                                            onClick={() => getByid(user.id)}>Edit</button></td>
                                        <td>
                                            <button className="btn btn-danger"
                                                onClick={() => deleteUser(user.id)}>
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                ))}


                            </tbody>
                        </table>
                    </div>
                    <div className="card-footer">
                        <button className="btn btn-primary"
                            onClick={() => setAdd(true)}  >
                            Add user
                        </button>
                    </div>
                </div>
                {
                    msg !== "" ? <div className="alert alert-primary">
                        {msg}
                    </div> : ""
                }
                {
                    add ? <div>

                        <div className="card mt-5">
                            <div className="card-header">Add User</div>
                            <div className="card-body">
                                <div>
                                    <label>Enter Name</label>
                                    <input className="form-control"
                                        onChange={($e) => setName($e.target.value)} required/>
                                </div>
                                <div>
                                    <label>Enter email</label>
                                    <input className="form-control"
                                        onChange={($e) => setEmail($e.target.value)} required/>
                                </div>
                                <div>
                                    <label>Enter gender</label>
                                    <select className="form-control" onChange={($e) => setGender($e.target.value)}>
                                        <option>select</option>
                                        <option value={'male'}>male</option>
                                        <option value={'female'}>female</option> 
                                    </select>
                                </div>
                                <div>
                                    <label>Enter Status</label>
                                    <select className="form-control" onChange={($e) => setStatus($e.target.value)}>
                                        <option>select</option>
                                        <option value={'active'}>active</option>
                                        <option value={'inactive'}>inactive</option>
                                    </select>
                                </div>
                            </div>
                            <div className="card-footer">
                                <div>
                                    <button className="btn btn-primary" onClick={AddUser}>Add User</button>
                                </div>
                            </div>
                        </div>
                    </div> : ""
                }
                {
                    edit ? <div>
                        <div className="card">
                            <div className="card-header">Edit User</div>
                            <div className="card-body">
                                <div>
                                    <label>Enter Name</label>
                                    <input className="form-control" value={name}
                                        onChange={($e) => setName($e.target.value)} required/>
                                </div>
                                <div>
                                    <label>Enter email</label>
                                    <input className="form-control" value={email}
                                        onChange={($e) => setEmail($e.target.value)} required/>
                                </div>
                                <div>
                                    <label>Enter gender</label>
                                    <select className="form-control" value={gender}
                                        onChange={($e) => setGender($e.target.value)}>
                                        <option value={'male'}>male</option>
                                        <option value={'female'}>female</option>
                                    </select>
                                </div>
                                <div>
                                    <label>Enter Status</label>
                                    <select className="form-control" value={status}
                                        onChange={($e) => setStatus($e.target.value)}>
                                        <option value={'active'}>active</option>
                                        <option value={'inactive'}>inactive</option>
                                    </select>
                                </div>
                            </div>
                            <div className="card-footer">
                                <div>
                                    <button className="btn btn-primary" onClick={UpdateUser}>Update User</button>
                                </div>
                            </div>
                        </div>
                    </div> : ""
                }
            </div>

        </div>
    );
}

export default FetchUsers;
