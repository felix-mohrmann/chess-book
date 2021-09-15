import PageStyle from '../components/PageStyle'
import Navbar from '../components/Navbar'
import { useAuth } from '../auth/AuthProvider'
import { useEffect } from 'react'
import LinkButton from '../components/LinkButton'
import Chessboard from 'chessboardjsx'

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
      <Navbar />
      <Chessboard position="r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R" />
      {params && (
        <LinkButton href={lichessUrl} secondary>
          Login with Lichess
        </LinkButton>
      )}
    </PageStyle>
  )
}
