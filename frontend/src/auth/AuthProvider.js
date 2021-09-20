import { useContext, useEffect, useState } from 'react'
import AuthContext from './AuthContext'
import { getTokenWithLichessCode } from '../service/lichess-api'
import { getParams } from '../service/chess-book-api'

export default function AuthProvider({ children }) {
  const [params, setParams] = useState()
  const [token, setToken] = useState()
  const [username, setUsername] = useState()

  useEffect(() => {
    if (!params && localStorage.getItem('init') === 'yes') {
      getParams()
        .then(setParams)
        .catch(error => console.error(error))
      localStorage.setItem('init', 'no')
    }
  }, [params])

  const loginWithLichess = code =>
    getTokenWithLichessCode(code, localStorage.getItem('code_verifier')).then(
      setToken
    )

  return (
    <AuthContext.Provider
      value={{ params, token, loginWithLichess, username, setUsername }}
    >
      {children}
    </AuthContext.Provider>
  )
}

export const useAuth = () => useContext(AuthContext)
