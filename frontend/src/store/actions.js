// API
import $axios from 'axios'


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