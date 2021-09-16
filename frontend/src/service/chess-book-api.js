import axios from 'axios'

export const getParams = () =>
  axios.get('/api/chess-book/auth/params').then(response => response.data)

export const getWhiteOpenings = name =>
  axios
    .get(`/api/chess-book/openings/white/${name}`)
    .then(response => response.data)

export const getBlackOpenings = name =>
  axios
    .get(`/api/chess-book/openings/black/${name}`)
    .then(response => response.data)
