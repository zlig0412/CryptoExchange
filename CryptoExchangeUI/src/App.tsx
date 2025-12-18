import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { Home } from './pages/home'
import { LoginCallbackPage } from './pages/logincallback'
import './assets/index.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home/>}></Route>
        <Route path="/logincallback" element={<LoginCallbackPage/>}></Route>
      </Routes>
    </BrowserRouter>
  </StrictMode>
)