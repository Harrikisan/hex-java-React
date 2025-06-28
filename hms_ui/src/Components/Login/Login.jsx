import { useState } from 'react';
import '../../Componentcss/Login/Login.css';
import axios from "axios";
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux'
import { setUserDetails } from '../../store/Actions/UserActions';

function Login() {

  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")

  const [msg, setMsg] = useState("")

  const navigate = useNavigate()

  const dispatch = useDispatch()

  const handleLogin = async () => {
    const encodedString = window.btoa(username + ':' + password);

    try {
      const response = await axios.get('http://localhost:8080/api/user/token', {
        headers: { Authorization: "Basic " + encodedString }

      });

      console.log(response.data.body)
      setMsg('Login Successful')
      let token = response.data.body
      localStorage.setItem('token',token)
      localStorage.setItem('name',username)
      getDashboard(token)
    } catch (error) {
      console.error(error)
      setMsg('Login Failed')
    }
  }

  const getDashboard = async (token) => {
    try {
      // console.log(token)
      const response = await axios('http://localhost:8080/api/user/details', {
        headers: { "Authorization": "Bearer " + token }
      })
      const user = {
        username: response.data.user.username,
        token:token,
        role: response.data.user.role,
        login:true,
        userId:response.data.id
      }
      console.log(response.data)
      setUserDetails(dispatch)(user)

      
      let role = response.data.user.role;

      switch (role) {
        case "PATIENT":
          navigate("/patient");break;
        case "DOCTOR":
          navigate("/doctor");break;
      }


    } catch (error) {
      console.log(error)
    }
  }

  return (
    <div className="card login_card header">
      <div className="card-header color-1 text text-color-4">Login</div>

      <div className="card-body color-3">
        {
          msg !== "" ? msg == "Login Successful" ? <span className='alert alert-primary'>{msg}</span> :
            <span className='alert alert-danger'>{msg}</span> : ""

        }

        <div>
          <label className='text text-color-1 line-height'>Enter username:</label>
          <input
            type="text"
            className="form-control color-4"
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>

        <div>
          <label className='text text-color-1 line-height'>Enter password:</label>
          <input
            type="password"
            className="form-control color-4"
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
      </div>

      <div className="card-footer color-2">
        <div>
          <button
            className="btn btn-primary color-1 text text-color-4"
            onClick={handleLogin}
          >
            Login
          </button>
        </div>
        <div className="signup-prompt">
          Don’t have an account?{' '}
          <span className="signup-link" onClick={() => navigate("/signup")}>
            Signup
          </span>
        </div>

      </div>
    </div>
  );
}

export default Login;
