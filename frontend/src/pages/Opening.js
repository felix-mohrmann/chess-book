import { useParams } from 'react-router-dom'
import PageStyle from '../components/PageStyle'
import Navbar from '../components/Navbar'
import Chessboard from 'chessboardjsx'
import styled from 'styled-components'

export default function Opening() {
  const {
    openingName,
    orientation,
    fen1,
    fen2,
    fen3,
    fen4,
    fen5,
    fen6,
    fen7,
    fen8,
  } = useParams()
  const fen =
    fen1 +
    '/' +
    fen2 +
    '/' +
    fen3 +
    '/' +
    fen4 +
    '/' +
    fen5 +
    '/' +
    fen6 +
    '/' +
    fen7 +
    '/' +
    fen8

  return (
    <PageStyle>
      <Navbar />
      <Wrapper>
        <h1>{openingName}</h1>
        <Chessboard position={fen} orientation={orientation} />
      </Wrapper>
    </PageStyle>
  )
}

const Wrapper = styled.div`
  h1 {
    text-align: center;
  }
`
