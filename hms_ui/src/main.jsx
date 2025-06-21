import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
// import './index.css'
import App from './App.jsx'
import {Provider} from 'react-redux'
import store from '../src/store/store.js'
import "../node_modules/primereact/resources/themes/lara-light-cyan/theme.css";

createRoot(document.getElementById('root')).render(
  <Provider store={store}>
    <App/>
  </Provider>
)
