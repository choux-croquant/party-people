// API
import $axios from 'axios'

export function requestLogin ({ state }, payload) {
  console.log('requestLogin', state, payload)
  const url = '/auth/login'
  let body = payload
  console.log(url)
  console.log(payload)
  return $axios.post(url, body)
}

export function requestSignup ({ state }, payload) {
  console.log('requestSignup', state, payload)
  const url = '/users'
  let body = payload
  return $axios.post(url, body)
}

export function requestRoomList ({ state }, payload) {
  console.log('requestRoomList', state, payload)
  const url = `/list/room`
  return $axios.get(url)
}

export function roomSearch ({ state }, payload) {
  console.log('roomSearch', state, payload)
  const url = `/list/roomSearch?include=${payload.include}&word=${payload.word}`
  return $axios.get(url) 
}

export function createRoom ({ state }, payload) {
  console.log('createRoom', state, payload)
  const url = '/rooms'
  let token = localStorage.getItem('access_token')
  console.log(token)
  return $axios({
    method:'POST',
    url: url,
    headers: {'Authorization': 'Bearer ' + token,
              'Content-Type':'multipart/form-data'
    },
    data: payload
  })
}