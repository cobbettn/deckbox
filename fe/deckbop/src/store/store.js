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
    userJwt: state => state.user.jwt
  },
  mutations: {
    SET_USER_JWT(state, data) {
      state.user.jwt = data
    },
    CLEAR_USER_JWT(state) {
      state.user.jwt = null
    }
  },
  actions: {
    SET_USER_JWT(context, jwt) {
      context.commit('SET_USER_JWT', jwt)
    },
    LOGOUT(context) {
      context.commit('CLEAR_USER_JWT')
    }
  }

})