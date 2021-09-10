import axios from 'axios'

export const getParams = () =>
  axios.get('api/chess-book/auth/params').then(response => response.data)
