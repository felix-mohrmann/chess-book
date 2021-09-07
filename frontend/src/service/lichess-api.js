import axios from 'axios'

const headers = () => ({
  headers: {
    Accept: 'application/x-ndjson',
    Authorization: `Bearer lip_5pN9m1oQ0jShZVhbkUcr`,
  },
})

export const getLast100Games = name =>
  axios
    .get(
      `https://lichess.org/api/games/user/${name}?perfType=blitz&rated=true&opening=true&max=100`,
      { headers }
    )
    .then(response => response.data)
    .catch(console.error)

export const getProfile = () =>
  axios
    .get(`https://lichess.org/api/account`, { headers })
    .then(response => response.data)
    .catch(console.error)
