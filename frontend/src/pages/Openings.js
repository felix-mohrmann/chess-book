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
      .then(setWhiteOpenings)
      .catch(error => console.error(error))
    getBlackOpenings(username)
      .then(setBlackOpenings)
      .catch(error => console.error(error))
  }, [])

  if (whiteOpenings) {
    console.log(whiteOpenings)
  }

  return (
    <PageStyle>
      <Navbar />
      <Wrapper>
        <h1>White</h1>
        {whiteOpenings && <h3>{whiteOpenings[0].name}</h3>}
        <h1>Black</h1>
      </Wrapper>
    </PageStyle>
  )
}

const Wrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
`
