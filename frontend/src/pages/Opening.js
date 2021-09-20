import { useParams } from 'react-router-dom'
import PageStyle from '../components/PageStyle'
import Navbar from '../components/Navbar'
import Chessboard from 'chessboardjsx'

export default function Opening({ opening }) {
  const { openingName } = useParams()
  return (
    <PageStyle>
      <Navbar />
      <h1>{openingName}</h1>
      <Chessboard position="r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R" />
    </PageStyle>
  )
}
