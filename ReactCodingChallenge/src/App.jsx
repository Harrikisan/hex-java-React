import { BrowserRouter, Route, Routes } from 'react-router-dom';
import FetchUsers from './assets/components/FetchUsers';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<FetchUsers />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
