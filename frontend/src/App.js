import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Login from './pages/Login'
import Profile from './pages/Profile'
import Openings from './pages/Openings'
import LichessRedirect from './auth/LichessRedirect'
import AuthProvider from './auth/AuthProvider'
import Opening from './pages/Opening'

export default function App() {
  return (
    <AuthProvider>
      <Router>
        <Switch>
          <Route exact path="/" component={Login} />
          <Route path="/oauth/lichess_redirect" component={LichessRedirect} />
          <Route path="/profile" component={Profile} />
          <Route path="/openings/:openingName" component={Opening} />
          <Route path="/openings" component={Openings} />
        </Switch>
      </Router>
    </AuthProvider>
  )
}
