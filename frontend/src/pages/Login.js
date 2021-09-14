import PageStyle from '../components/PageStyle'
import Navbar from '../components/Navbar'
import { useAuth } from '../auth/AuthProvider'
import { useEffect } from 'react'

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
      {params && <a href={lichessUrl}>Login with Lichess</a>}
    </PageStyle>
  )
}
