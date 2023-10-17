import axios from 'axios'
axios.defaults.baseURL = 'http://localhost:8080'

export const getTokenWithLichessCode = (code, verifier) => {
  console.log(code, verifier)
  axios
    .post('/api/chess-book/auth/access-token', { code, verifier })
    .then(response => response.data)
}

const headers = token => ({
  headers: {
    Authorization: `Bearer ${token}`,
  },
})

export const getProfile = token =>
  axios
    .get(`https://lichess.org/api/account`, headers(token))
    .then(response => response.data)
