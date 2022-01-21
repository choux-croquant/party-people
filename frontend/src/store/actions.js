// API
import $axios from 'axios'

export function requestLogin ({ state }, payload) {
  console.log('requestLogin', state, payload)
  const url = 'https://e3920adf-c92e-4046-b6b5-aa9001342557.mock.pstmn.io/user'
  let body = payload
  return $axios.post(url, body)
}

export function requestSignup ({ state }, payload) {
  console.log('requestSignup', state, payload)
  const url = 'https://e3920adf-c92e-4046-b6b5-aa9001342557.mock.pstmn.io/user'
  let body = payload
  return $axios.post(url, body)
}