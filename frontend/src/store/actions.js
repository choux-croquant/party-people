// API
import $axios from 'axios'

export function requestLogin ({ state }, payload) {
  console.log('requestLogin', state, payload)
  const url = '/auth/login'
  let body = payload
  console.log(url)
  return $axios.post(url, body)
}

export function requestSignup ({ state }, payload) {
  console.log('requestSignup', state, payload)
  const url = '/users'
  let body = payload
  return $axios.post(url, body)
}

export function roomSearch ({ state }, payload) {
  console.log('roomSearch', state, payload)
  const url = '/list/roomSearch'
  let body = payload
  return $axios.get(url, body) 
}