import { useParams } from 'react-router-dom'
import PageStyle from '../components/PageStyle'
import Navbar from '../components/Navbar'

import Chessboard from 'chessboardjsx'
import { useEffect, useState } from 'react'
import { getOpeningFEN } from '../service/chess-book-api'

export default function Opening() {
  const { openingName } = useParams()
  const { position, setPosition } = useState()

  useEffect(() => {
    getOpeningFEN()
      .then(setPosition)
      .catch(error => console.error(error))
  }, [setPosition])

  return (
    <PageStyle>
      <Navbar />
      <h1>{openingName}</h1>

      {position && <Chessboard position={position} />}
    </PageStyle>
  )
}
