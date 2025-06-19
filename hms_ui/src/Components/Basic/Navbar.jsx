import '../../Componentcss/Basic/Navbar.css';
import { FaUser } from 'react-icons/fa';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setUserDetails } from '../../store/Actions/UserActions';
import { useEffect, useState } from 'react';

function Navbar() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [token, setToken] = useState("");

  const handleLogout = () => {
    setUserDetails(dispatch)({ username: "", role: "", token: "" });
    navigate('/login');
  };

  const handleLogin = () => {
    navigate('/login');
  };

  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    if (storedToken) {
      setToken(storedToken);
    }
  }, []);


  return (
    <div className="navbar">
      <div className="navbar-title">AMAZE CARE</div>
      <div className="navbar-right">
        {token == undefined || token == "" ? (
          <span className="navbar-link" onClick={handleLogin}>Login</span>
        ) : (
          <>
            <span className="navbar-link" onClick={handleLogout}>Logout</span>
            <div className="navbar-profile">
              <span className="profile-text">Profile</span>
              <FaUser className="profile-icon" />
            </div>
          </>
        )}
      </div>
    </div>
  );
}

export default Navbar;
