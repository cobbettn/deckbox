import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      jwt: null
    },
    deck: {cards:[]},
    searchResults: {data: ""},
  },
  getters: {
    userJwt: state => state.user.jwt,
    searchResults: state => state.searchResults,
    deck: state => state.deck,
  },
  mutations: {
    SET_USER_JWT(state, data) {
      state.user.jwt = data
    },
    CLEAR_USER_JWT(state) {
      state.user.jwt = null
    },
    SET_SEARCH_RESULTS(state, data){
      state.searchResults = data;
    },
    ADD_TO_DECK(state, data){
      state.deck.cards = [...state.deck.cards, data]
    },
    REMOVE_FROM_DECK(state, data){
      state.deck.cards.splice(state.deck.cards.findIndex((x) => x == data), 1)
    },
  },
  actions: {
    LOGIN(context, jwt) {
      context.commit('SET_USER_JWT', jwt)
    },
    LOGOUT(context) {
      context.commit('CLEAR_USER_JWT')
    },
    SET_SEARCH_RESULTS(context, data){
      context.commit('SET_SEARCH_RESULTS', data)
    },
    ADD_TO_DECK(context, data){
      context.commit('ADD_TO_DECK', data)
    },
    REMOVE_FROM_DECK(context, data){
      context.commit('REMOVE_FROM_DECK', data)
    },
  }
})