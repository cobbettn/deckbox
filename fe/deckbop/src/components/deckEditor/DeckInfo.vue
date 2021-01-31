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
        saveDeck: function(){
            const vm = this
            const reqBody = {
                name : "update_deck",
                userId: 1, //vm.$store.getters.user.id,
                cardList: [{"card_id":"fun","card_quantity":4},{"card_id":"bar","card_quantity":2}] }
            const reqHeaders = {
                "Content-Type":"application/json",
                "Authorization": "Bearer " + vm.$store.getters.userJwt
            }
            this.$http.post(
                "post-url",
                reqBody,
                reqHeaders
            )
        }
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