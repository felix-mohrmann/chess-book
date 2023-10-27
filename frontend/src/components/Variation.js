import Button from './Button'
import styled from 'styled-components'
import { deleteVariation } from '../service/chess-book-api'

export default function Variation({ variation, update, show }) {
  return (
    <Wrapper>
      <p>{variation.name}</p>
      <ButtonGroup>
        <Button
          onClick={() => {
            show(variation)
          }}
        >
          Show
        </Button>
        <Button
          onClick={() => {
            deleteVariation(variation.name).then(update())
          }}
        >
          Delete
        </Button>
      </ButtonGroup>
    </Wrapper>
  )
}

const Wrapper = styled.div`
  p {
    margin: 5px 0 5px 0;
  }

  margin: 10px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
`

const ButtonGroup = styled.div`
  Button {
    border-radius: 0;
    padding: 5px;
    text-align: center;
  }
`
