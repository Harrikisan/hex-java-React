import { BrowserRouter, Route, Routes } from 'react-router-dom';
import FetchUsers from './assets/components/FetchUsers';
import Adduser from './assets/components/Adduser';
import Updateuser from './assets/components/Updateuser';
import Deleteuser from './assets/components/Deleteuser';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/get/user" element={<FetchUsers />} />
        <Route path="/add/user" element={<Adduser />} />
        <Route path="/update/user" element={<Updateuser />} />
        <Route path="/delete/user" element={<Deleteuser />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
