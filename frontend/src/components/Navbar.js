import styled from 'styled-components/macro'

export default function Navbar() {
  return (
    <Wrapper>
      <h1>Hello</h1>
      <h1>Bye</h1>
    </Wrapper>
  )
}
const Wrapper = styled.nav`
  width: 100%;
  background-color: var(--gray-dark);

  display: flex;
  justify-content: space-evenly;
`
