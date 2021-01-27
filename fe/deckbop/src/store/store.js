import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      jwt: null
    }
  },
  mutations: {
    SET_JWT(state, data) {
      state.user.jwt = data;
    }
  },
  getters: {
    userJwt: state => state.user.jwt
  }
})