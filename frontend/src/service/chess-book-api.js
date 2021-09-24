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

export const saveVariation = moves =>
  axios
    .post(`/api/chess-book/variations/add`, moves)
    .then(response => response.data)

export const getVariation = id =>
  axios
    .get(`/api/chess-book/variations/get/${id}`)
    .then(response => response.data)

export const deleteVariation = id =>
  axios
    .delete(`/api/chess-book/variations/delete/${id}`)
    .then(response => response.data)
