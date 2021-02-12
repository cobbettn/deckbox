<template>
  <div class="deck-info">
      <sidebar-nav></sidebar-nav>
      <div class="column-content">
        <input type="text" placeholder="Deck Name" v-model="getDeck.name"/>
        <button @click="handleClick">{{ isEditMode ? 'save': 'create' }}</button>
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
    computed: {
        getDeck() {
            return this.$store.getters.deck
        },
        getUser() {
            return this.$store.getters.user
        },
        isEditMode() {
            return this.$store.getters.editorMode === 'edit'
        },
    },
    methods: {
        handleClick() {
            this.isEditMode ? this.updateDeck() : this.createDeck()
        },
        createDeck() {
            const {reqUrl, reqBody, reqHeaders} = this.getRequestData()
            this.$http.post(
                reqUrl,
                reqBody,
                reqHeaders
            ).then(() => {
                this.$toasted.show('Deck created', { position: 'bottom-center', duration: 2000})
            })
            .catch(e => console.log(e))
        },
        updateDeck() {
            const {reqUrl, reqBody, reqHeaders} = this.getRequestData(true)
            this.$http.post(
                reqUrl,
                reqBody,
                reqHeaders
            ).then(() => {
                this.$toasted.show('Deck updated', { position: 'bottom-center', duration: 2000})
            })
            .catch(e => console.log(e))
        },
        getRequestData(update) {
            const vm = this
            const reqBody = {
                name: vm.getDeck.name,
                userId: this.getUser.userId,
                cards: []
            }
            const cards = this.getDeck.cards
            const map = {}
            cards.forEach(({id}) => {
                map[id] = map[id] ? map[id] + 1 : 1;
            });
            Object.entries(map).forEach(([key, value]) => {
                reqBody.cards.push({card_id: key, card_quantity: value})
            })
            const reqHeaders = {
                headers: {
                    ...jsonContentHeader,
                    ...authTokenFactory(this.getUser.token)
                }
            }
            const getUrl = () => {
                return update ? deckUrl + `/${this.getDeck.id}` : deckUrl
            }
            return {reqUrl: getUrl(), reqBody, reqHeaders}
        }
    }
}
</script>

<style>
    @import '../../style/style.css';
</style>
