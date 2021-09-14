import { Redirect, useLocation } from 'react-router-dom'
import { useAuth } from './AuthProvider'
import { useEffect, useState } from 'react'
import PageStyle from '../components/PageStyle'
import Header from '../components/Header'

export default function LichessRedirect() {
  const location = useLocation()
  const query = location.search
  const search = new URLSearchParams(query)

  const code = search.get('code')

  const { params, loginWithLichess } = useAuth()

  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loginWithLichess(code)
      .catch(error => console.error(error))
      .finally(setLoading(false))
  }, [code, loginWithLichess])

  if (loading) {
    return (
      <PageStyle>
        <Header title="Authorizing..." />
      </PageStyle>
    )
  }

  return <Redirect to="/profile" />
}
