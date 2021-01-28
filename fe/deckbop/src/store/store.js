import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      jwt: null
    }
  },
  getters: {
    getUserJwt: state => state.user.jwt
  },
  mutations: {
    setUserJwt(state, data) {
      state.user.jwt = data
    },
    clearUserJwt(state) {
      state.user.jwt = null
    }
  },
  actions: {
    setUserJwt(context, jwt) {
      context.commit('setUserJwt', jwt)
    },
    clearUserJwt(context) {
      context.commit('clearUserJwt')
    }
  }

})