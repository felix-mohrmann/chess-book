import PageStyle from '../components/PageStyle'
import Navbar from '../components/Navbar'
import { useEffect, useState } from 'react'
import { getParams } from '../service/chess-book-api'

export default function Login() {
  const [params, setParams] = useState()
  let lichessUrl = ''

  useEffect(() => {
    getParams()
      .then(setParams)
      .catch(error => console.error(error))
  }, [])

  if (params) {
    lichessUrl =
      'https://lichess.org/oauth?response_type=code&redirect_uri=http://localhost:3000/profile&client_id=chess-book' +
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
