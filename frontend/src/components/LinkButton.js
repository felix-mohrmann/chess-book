import Button from './Button'
import styled from 'styled-components/macro'

export default function LinkButton(props) {
  return <ButtonStyled as="a" {...props} />
}

const ButtonStyled = styled(Button)`
  text-decoration: none;
`
