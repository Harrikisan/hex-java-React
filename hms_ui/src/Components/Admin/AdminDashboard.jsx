import { useNavigate } from "react-router-dom"

function AdminDashboard(){

    const navigate=useNavigate()
    navigate('/admin/addDoctor')
}

export default AdminDashboard