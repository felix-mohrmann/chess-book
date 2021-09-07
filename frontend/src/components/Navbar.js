import styled from 'styled-components/macro'
import { NavLink } from 'react-router-dom'

export default function Navbar() {
  return (
    <Wrapper>
      <NavLink exact to="/">
        Login
      </NavLink>
      <NavLink to="/profile">Profile</NavLink>
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
