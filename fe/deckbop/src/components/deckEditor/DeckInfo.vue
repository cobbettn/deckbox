<template>
  <div class="deck-info">
      <sidebar-nav></sidebar-nav>
      <div class="column-content">
        <input 
            type="text" 
            placeholder="Deck Name" 
            v-model="getDeckName"
        />
        <button @click="saveDeck">save</button>
      </div>
      
  </div>
</template>

<script>
import { jsonContentHeader, authTokenFactory, deckUrl } from '../../config/api'
import SidebarNav from './SidebarNav.vue'
export default {
    name: "DeckInfo",
    components: {
        SidebarNav,
    },
    methods: {
        saveDeck() {
            // update or create?
            const {reqUrl, reqBody, reqHeaders} = this.getRequestData()
            this.$http.post(
                reqUrl,
                reqBody,
                reqHeaders
            ).then(() => {
                this.$toasted.show('Deck saved', { position: 'bottom-center', duration: 2000})
            })
            .catch(e => console.log(e))
        },
        getRequestData() {
            const vm = this
            const reqBody = {
                name: vm.deckName,
                userId: this.$store.getters.user.userId,
                cardList: []
            }
            const cards = this.$store.getters.deck.cards
            const map = {}
            cards.forEach(({id}) => {
                map[id] = map[id] ? map[id] + 1 : 1;
            });
            Object.entries(map).forEach(([key, value]) => {
                reqBody.cardList.push({card_id: key, card_quantity: value})
            })

            const reqHeaders = {
                headers: {
                    ...jsonContentHeader,
                    ...authTokenFactory(this.$store.getters.user.token)
                }
            }
            return {reqUrl: deckUrl, reqBody, reqHeaders}
        }
    },
    computed: {
        getDeckName() {
            return this.$store.getters.editorMode === 'edit' ? this.$store.getters.deck.name : ''
        }
    }
}
</script>

<style>
    @import '../../style/style.css';
</style>
