import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Login from './pages/Login'
import Profile from './pages/Profile'

export default function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/" component={Login} />
        <Route path="/profile" component={Profile} />
      </Switch>
    </Router>
  )
}
