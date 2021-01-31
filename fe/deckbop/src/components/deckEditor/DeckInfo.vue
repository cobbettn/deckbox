<template>
  <div class="deck-info">
      <sidebar-nav></sidebar-nav>
      <div class="column-content">
        <input 
            type="text" 
            placeholder="Deck Title" 
            v-model="title"
            @change="setTitle(title)" 
        />
        <button>save</button>
      </div>
      
  </div>
</template>

<script>
import SidebarNav from './SidebarNav.vue'
export default {
    name: "DeckInfo",
    components: {
        SidebarNav,
    },
    methods: {
        setTitle: function(title) {
            this.$store.dispatch("SET_DECK_TITLE", title)
        }
    },
    computed:{
        deckImage: function() {
            return null
        },
        deckTitle: function() {
            return this.$store.getters.deck.title
        }
    },
}
</script>

<style>
    @import '../../style/style.css';
    
</style>