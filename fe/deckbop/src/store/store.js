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
  mutations: {
    SET_JWT(state, data) {
      state.user.jwt = data;
    },
    SET_SEARCH_RESULTS(state, data){
      state.searchResults = data;
    },
    ADD_TO_DECK(state, data){
      state.deck.cards = [...state.deck.cards, data]
    },
    REMOVE_FROM_DECK(state, data){
      console.log(data)
      state.deck.cards.splice(state.deck.cards.findIndex((x) => x == data), 1)
    }

  },
  getters: {
    userJwt: state => state.user.jwt,
    getSearchResults: state => state.searchResults,
  }
})