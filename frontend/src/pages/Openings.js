import Navbar from '../components/Navbar'
import PageStyle from '../components/PageStyle'
import styled from 'styled-components/macro'
import { useEffect, useState } from 'react'
import { useAuth } from '../auth/AuthProvider'
import { getBlackOpenings, getWhiteOpenings } from '../service/chess-book-api'
import OpeningCard from '../components/OpeningCard'

export default function Openings() {
  const { username } = useAuth()
  const [whiteOpenings, setWhiteOpenings] = useState()
  const [blackOpenings, setBlackOpenings] = useState()

  useEffect(() => {
    getWhiteOpenings(username)
      .then(data => setWhiteOpenings(data.openings))
      .catch(error => console.error(error))
    getBlackOpenings(username)
      .then(data => setBlackOpenings(data.openings))
      .catch(error => console.error(error))
  }, [username])

  return (
    <PageStyle>
      <Navbar />
      {whiteOpenings && blackOpenings && (
        <Wrapper>
          <h1>White</h1>
          {whiteOpenings.map((opening, index) => (
            <OpeningCard key={opening.name} opening={opening} index={index} />
          ))}
          <br />
          <h1>Black</h1>
          {blackOpenings.map((opening, index) => (
            <OpeningCard key={opening.name} opening={opening} index={index} />
          ))}
        </Wrapper>
      )}
    </PageStyle>
  )
}

const Wrapper = styled.div`
  h1 {
    text-align: center;
  }
`
