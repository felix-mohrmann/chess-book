import axios from 'axios'

export const getLichessToken = (code, verifier) =>
  axios
    .post('/api/chess-book/auth/access-token', { code, verifier })
    .then(response => response.data)

const headers = token => ({
  headers: {
    Authorization: `Bearer ${token}`,
  },
})

export const getProfile = token =>
  axios
    .get(`https://lichess.org/api/account`, headers(token))
    .then(response => response.data)
