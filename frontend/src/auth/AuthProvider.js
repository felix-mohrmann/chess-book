import { useContext, useState } from 'react'
import AuthContext from './AuthContext'

export default function AuthProvider({ children }) {
  const [token, setToken] = useState()

  const loginWithLichess = code => getTokenWithLichessCode(code).then(setToken)
  return (
    <AuthContext.Provider value={loginWithLichess}>
      {children}
    </AuthContext.Provider>
  )
}

export const useAuth = () => useContext(AuthContext)
