<template>
  <div id="app">
    <Header user="Planeswalker"/>
    <ViewDecks v-bind:decks="decks" key: />
    <Login />
    
  </div>
</template>

<script>
import Header from './components/header/Header.vue'
import Login from './components/authentication/Login.vue'
import ViewDecks from './components/deckManagement/ViewDecks.vue'

export default {
  name: 'App',
  components: {
    Header,
    Login,
    ViewDecks,

  },
  data() {
    return {
      decks: [
        {
          deck_id: 1,
          user_id: 1,
          decklist_id: 1,
          deck_name: "burn"
        },
        {
          deck_id: 2,
          user_id: 1,
          decklist_id: 2,
          deck_name: "storm"
        },
        {
          deck_id: 3,
          user_id: 1,
          decklist_id: 3,
          deck_name: "red deck wins"
        }
      ]
    }
  }
}
</script>

<style>
* {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
  }
  body {
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.4;
    background: #272727;
    color: #747474;
  }

</style>
