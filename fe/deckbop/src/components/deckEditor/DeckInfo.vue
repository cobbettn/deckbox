<template>
  <div class="deck-info">
      <sidebar-nav></sidebar-nav>
      <div class="column-content">
        <input 
            type="text" 
            placeholder="Deck Title" 
            v-model="deckTitle"
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
    data() {
        return {
            deckTitle: ''
        }
    },
    methods: {
        saveDeck() {
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
                name: vm.deckTitle,
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
}
</script>

<style>
    @import '../../style/style.css';
</style>
