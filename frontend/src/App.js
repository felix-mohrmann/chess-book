import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Login from './pages/Login'
import Profile from './pages/Profile'
import Openings from './pages/Openings'
import LichessNavigate from './auth/LichessNavigate'
import AuthProvider from './auth/AuthProvider'
import Opening from './pages/Opening'
import Variations from './pages/Variations'

export default function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="oauth/lichess_redirect" element={<LichessNavigate />} />
          <Route path="profile" element={<Profile />} />
          <Route
            path="openings/:openingName/:orientation/:fen1/:fen2/:fen3/:fen4/:fen5/:fen6/:fen7/:fen8"
            element={Opening}
          />
          <Route path="variations" element={<Variations />} />
          <Route path="openings" element={<Openings />} />
        </Routes>
      </Router>
    </AuthProvider>
  )
}
