import PageStyle from '../components/PageStyle'
import { useAuth } from '../auth/AuthProvider'
import { useEffect } from 'react'
import LinkButton from '../components/LinkButton'
import image from '../image/lichessPony.png'
import styled from 'styled-components'

export default function Login() {
  const { params } = useAuth()
  let lichessUrl = ''

  useEffect(() => {
    localStorage.setItem('init', 'yes')
  }, [])

  if (params) {
    localStorage.setItem('code_verifier', params[3])
    lichessUrl =
      'https://lichess.org/oauth?response_type=code&redirect_uri=http://localhost:3000/oauth/lichess_redirect&client_id=chess-book' +
      '&code_challenge=' +
      params[0] +
      '&code_challenge_method=' +
      params[1] +
      '&state=' +
      params[2]
  }

  return (
    <PageStyle>
      <Header>
        <h1>Chess Book</h1>
        <p>
          Welcome to this free chess tool! To continue please Login with Lichess
          to load your profile.
        </p>
      </Header>

      <Wrapper>
        <Text>
          <p>Once logged in the following futures will be available to you:</p>
          <ul>
            <li>View your rankings in bullet, blitz and rapid and classical</li>
            <li>Explore your top 3 most played openings for black and white</li>
            <li>Discover your win percentages for each of them</li>
            <li>Play around on a chess board, flip or reset it</li>
            <li>Save your favorite openings, variations, traps or games</li>
            <li>Review and learn your saved moves</li>
          </ul>
        </Text>
        <Util>
          <img src={image} alt="Chess Pieces" />
          {params && (
            <LinkButton href={lichessUrl}>Login with Lichess</LinkButton>
          )}
        </Util>
      </Wrapper>
    </PageStyle>
  )
}

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
`
const Header = styled.div`
  h1 {
    text-align: center;
    font-family: Palatino, fantasy;
    font-size: 500%;
  }
`

const Text = styled.div``

const Util = styled.div`
  margin: 10px 100px;
  display: flex;
  flex-direction: column;
  text-align: center;

  img {
    width: 100px;
    margin-bottom: 20px;
    align-self: center;
  }
`
