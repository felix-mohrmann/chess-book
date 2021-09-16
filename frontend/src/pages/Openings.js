import Navbar from '../components/Navbar'
import PageStyle from '../components/PageStyle'
import styled from 'styled-components/macro'
import { useEffect, useState } from 'react'
import { useAuth } from '../auth/AuthProvider'
import { getBlackOpenings, getWhiteOpenings } from '../service/chess-book-api'

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
          <h3>
            1. {whiteOpenings[0].name} ({whiteOpenings[0].totalGames} Games) -
            WR: {whiteOpenings[0].winPercentage}%
          </h3>
          <h3>
            2. {whiteOpenings[1].name} ({whiteOpenings[1].totalGames} Games) -
            WR: {whiteOpenings[1].winPercentage}%
          </h3>
          <h3>
            3. {whiteOpenings[2].name} ({whiteOpenings[2].totalGames} Games) -
            WR: {whiteOpenings[2].winPercentage}%
          </h3>
          <br />
          <h1>Black</h1>
          <h3>
            1. {blackOpenings[0].name} ({blackOpenings[0].totalGames} Games) WR:{' '}
            {blackOpenings[0].winPercentage}%
          </h3>
          <h3>
            2. {blackOpenings[1].name} ({blackOpenings[1].totalGames} Games) WR:{' '}
            {blackOpenings[1].winPercentage}%
          </h3>
          <h3>
            3. {blackOpenings[2].name} ({blackOpenings[2].totalGames} Games) WR:{' '}
            {blackOpenings[2].winPercentage}%
          </h3>
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
