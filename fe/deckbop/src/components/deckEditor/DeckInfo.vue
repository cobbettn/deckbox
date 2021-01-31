<template>
  <div class="deck-info">
      <sidebar-nav></sidebar-nav>
      <div class="column-content">
        <input 
            type="text" 
            placeholder="Deck Title" 
            v-model="deckTitle"
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
    },
    computed:{
        deckImage: function() {
            return null
        },
        deckTitle: {
            set(deckTitle) {
                this.$store.dispatch('SET_DECK_TITLE', deckTitle)
            },
            get() {
                return this.$store.getters.deckTitle
            }
        }
    },
}
</script>

<style>
    @import '../../style/style.css';
    
</style>