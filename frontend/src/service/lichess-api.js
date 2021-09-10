import axios from 'axios'

export const getTokenWithLichessCode = code =>
  axios
    .post('api/chess-book/auth/access-token')
    .then(response => response.data)
    .then(dto => dto.token)

const headers = () => ({
  headers: {
    Accept: 'application/x-ndjson',
    Authorization: `Bearer lip_5pN9m1oQ0jShZVhbkUcr`,
  },
})

export const getProfile = () =>
  axios
    .get(`https://lichess.org/api/account`, {
      headers: {
        Authorization: `Bearer lip_5pN9m1oQ0jShZVhbkUcr`,
      },
    })
    .then(response => response.data)
