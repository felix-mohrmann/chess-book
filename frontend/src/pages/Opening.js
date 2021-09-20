import { useParams } from 'react-router-dom'
import PageStyle from '../components/PageStyle'
import Navbar from '../components/Navbar'

export default function Opening() {
  const { openingName } = useParams()
  return (
    <PageStyle>
      <Navbar />
      <h1>{openingName}</h1>
    </PageStyle>
  )
}
