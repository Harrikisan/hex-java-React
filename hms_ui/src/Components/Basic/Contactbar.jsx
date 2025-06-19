import { FaEnvelope, FaPhone } from 'react-icons/fa';
import '../../Componentcss/Basic/Contactbar.css'
function Contactbar(){

    return(
        <div className="contactbar">
      <div className="contact-item">
        <FaEnvelope className="icon" />
        <span>email: amazecare@gmail.com</span>
      </div>
      <div className="contact-item">
        <FaPhone className="icon" />
        <span>contact: 9988 9988 10</span>
      </div>
    </div>
    )
}

export default Contactbar