import PageStyle from '../components/PageStyle'
import Navbar from '../components/Navbar'

const lichessUrl = ''

export default function Login() {
  return (
    <PageStyle>
      <Navbar />
      <button>Login with Lichess</button>
    </PageStyle>
  )
}
