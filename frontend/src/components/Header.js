import styled from 'styled-components/macro'

export default function Header({ title, ...props }) {
  return (
    <Wrapper {...props}>
      <h1>{title}</h1>
    </Wrapper>
  )
}

const Wrapper = styled.header`
  width: 100%;
  text-align: center;
  background: var(--gray-dark);
  color: var(--accent);
`
