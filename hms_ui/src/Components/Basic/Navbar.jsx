import '../../Componentcss/Basic/Navbar.css';
import { FaUser } from 'react-icons/fa';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setUserDetails } from '../../store/Actions/UserActions';
import { useEffect } from 'react';

function Navbar() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const login = useSelector((state) => state.user.login);
  const username = useSelector((state) => state.user.username);
  const role=useSelector((state)=>state.user.role);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');
    const name = localStorage.getItem('name');
    const userID = localStorage.getItem('userId');

    // If token exists but Redux is empty, restore Redux state
    if (token && !login) {
      setUserDetails(dispatch)({
        token,
        role,
        username: name,
        login: true,
        userId: userID,
      });
    }
  }, [dispatch, login]); // run only once or when login is false

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('name');
    localStorage.removeItem('login');
    localStorage.removeItem('userID');
    setUserDetails(dispatch)({ username: "", role: "", token: "", login: false, userId: "" });
    navigate('/login');
  };

  const handleLogin = () => {
    navigate('/login');
  };

  const getProfile=()=>{
    switch(role){
      case "PATIENT": navigate('/patient/patientProfile');break;
      case "DOCTOR": navigate('/doctor/profile');break;
    }
  }

  return (
    <div className="navbar">
      <div className="navbar-title">AMAZE CARE</div>
      <div className="navbar-right">
        {!login ? (
          <span className="navbar-link" onClick={handleLogin}>Login</span>
        ) : (
          <>
            <span className="navbar-link" onClick={handleLogout}>Logout</span>
            <button className="navbar-profile" onClick={()=>getProfile()}>
              <span className="profile-text" >{username || "Profile"}</span>
              <FaUser className="profile-icon" />
            </button>
          </>
        )}
      </div>
    </div>
  );
}

export default Navbar;
