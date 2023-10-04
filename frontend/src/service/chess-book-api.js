import axios from 'axios'

export const getParams = () =>
  axios('/api/chess-book/auth/params').then(response => response.data)

export const getWhiteOpenings = name =>
  axios
    .get(`/api/chess-book/openings/white/${name}`)
    .then(response => response.data)

export const getBlackOpenings = name =>
  axios
    .get(`/api/chess-book/openings/black/${name}`)
    .then(response => response.data)

export const saveVariation = (moves, name, orientation) =>
  axios
    .post(`/api/chess-book/variations/add`, {
      moveArray: moves,
      name: name,
      orientation: orientation,
    })
    .then(response => response.data)

export const getVariations = () =>
  axios.get(`/api/chess-book/variations/all`).then(response => response.data)

export const getVariation = name =>
  axios
    .get(`/api/chess-book/variations/get/${name}`)
    .then(response => response.data)

export const deleteVariation = name =>
  axios.delete(`/api/chess-book/variations/delete/${name}`)
