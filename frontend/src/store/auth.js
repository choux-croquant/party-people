import $axios from 'axios'


export default {
  namespaced: true,

  state: () => {
    loginState: false
  },

  getters: {
    getLoginState (state) {
      return state.loginState
    }
  },

  mutations: {
    setLoginState (state, setLogin) {
      console.log(setLogin)
      state.loginState = setLogin
      console.log(state.loginState)
    }
  },

  actions: {
    requestLogin ({ state }, payload) {
      console.log('requestLogin', state, payload)
      const url = '/auth/login'
      let body = payload
      console.log(url)
      console.log(payload)
      return $axios.post(url, body)
    },

    requestSignup ({ state }, payload) {
      console.log('requestSignup', state, payload)
      const url = '/users'
      let body = payload
      return $axios.post(url, body)
    }
  }
}
