import Vue from 'vue'
import Vuex from 'vuex'
import defaultUser from '../model/user'
import defaultDeck from '../model/deck'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {...defaultUser},
    deck: {...defaultDeck},
    editorMode: '',
    searchResults: {data: ""},
    viewSearch: false,
  },
  getters: {
    user: state => state.user,
    searchResults: state => state.searchResults,
    deck: state => state.deck,
    viewSearch: state => state.viewSearch,
    deckName: state => state.deck.name,
    editorMode: state => state.editorMode,
  },
  mutations: {
    SET_USER(state, data) {
      state.user = {...data}
    },
    CLEAR_USER(state) {
      state.user = {...defaultUser}
    },
    SET_SEARCH_RESULTS(state, data) {
      state.searchResults = data;
    },
    ADD_TO_DECK(state, data) {
      state.deck.cards = [...state.deck.cards, data].sort((a,b) => a.name < b.name ? -1 : 1)
    },
    REMOVE_FROM_DECK(state, data) {
      state.deck.cards.splice(state.deck.cards.findIndex((x) => x == data), 1)
    },
    TOGGLE_VIEW_SEARCH(state) {
      state.viewSearch = !state.viewSearch
    },
    SET_DECK_NAME(state, data) {
      state.deck.name = data
    },
    SET_DECK(state, data) {
      state.deck = {...data}
    },
    SET_EDITOR_MODE(state, data) {
      state.editorMode = data
    },
    CLEAR_DECK(state) {
      state.deck = {...defaultDeck}
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
    ADD_TO_DECK(context, data) {
      context.commit('ADD_TO_DECK', data)
    },
    REMOVE_FROM_DECK(context, data) {
      context.commit('REMOVE_FROM_DECK', data)
    },
    TOGGLE_VIEW_SEARCH(context) {
      context.commit('TOGGLE_VIEW_SEARCH')
    },
    SET_DECK_NAME(context, data) {
      context.commit('SET_DECK_NAME', data)
    },
    UPDATE_USER(context, data) {
      context.commit('SET_USER', data)
    },
    SET_DECK(context, data) {
      context.commit('SET_DECK', data)
    },
    SET_EDITOR_MODE(context, data) {
      context.commit('SET_EDITOR_MODE', data)
    },
    CLEAR_DECK(context) {
      context.commit('CLEAR_DECK')
    },
  }
})