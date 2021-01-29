import Vue from 'vue'
import Vuex from 'vuex'
import defaultUser from '../model/user'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: defaultUser,
    deck: {cards:[]},
    searchResults: {data: ""},
  },
  getters: {
    user: state => state.user,
    searchResults: state => state.searchResults,
    deck: state => state.deck,
  },
  mutations: {
    SET_USER(state, data) {
      state.user = data
    },
    CLEAR_USER(state) {
      state.user = defaultUser
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
    LOGIN(context, data) {
      context.commit('SET_USER', data)
    },
    LOGOUT(context) {
      context.commit('CLEAR_USER')
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