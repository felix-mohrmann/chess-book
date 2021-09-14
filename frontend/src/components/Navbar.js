import styled from 'styled-components/macro'
import { NavLink } from 'react-router-dom'
import { useAuth } from '../auth/AuthProvider'

export default function Navbar() {
  const { token } = useAuth()
  return (
    <Wrapper>
      {!token && (
        <NavLink exact to="/">
          Login
        </NavLink>
      )}
      {token && <NavLink to="/profile">Profile</NavLink>}
      {token && <NavLink to="/openings">Openings</NavLink>}
    </Wrapper>
  )
}
const Wrapper = styled.nav`
  width: 100%;
  background-color: var(--gray-dark);

  display: flex;
  justify-content: space-evenly;

  a {
    text-decoration: none;
    color: white;
    padding: var(--size-l);
    font-size: var(--size-xl);
  }

  a.active {
    color: var(--accent);
  }
`
