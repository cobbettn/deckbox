<template>
    <div  class="deck-box">
        <h3>{{ deck.name }}</h3>
        <button type="button" @click="editDeck">edit</button>
        <button type="button" @click="deleteDeck">delete</button>
        <img :src="imgSrc"/>
    </div>    
</template>

<script>
import { deckUrl, authTokenFactory } from '../../config/api'
export default {
    name: "DeckBox",
    props: ["deck"],
    data() {
        return {
            imgSrc: '',
        }
    },
    methods: {
        editDeck() {
            this.$store.dispatch('SET_EDITOR_MODE', 'edit')
            this.$store.dispatch('SET_DECK', this.deck)
            this.$router.push(`/deckEditor`)
        },
        deleteDeck() {
            const reqUrl = `${deckUrl}/${this.deck.id}`
            const reqHeaders = {
                headers: {
                    ...authTokenFactory(this.getUser.token)
                }
            }
            this.$http.delete(
                reqUrl,
                reqHeaders
            )
            .then(() => {
                const updatedDecks = this.getUserDecks.filter(deck => deck.id !== this.deck.id)
                this.$store.dispatch("UPDATE_USER",  {...this.getUser, decks: updatedDecks})
            })
            .catch(e => console.log(e))
        }
    },
    computed: {
        getUser() {
            return this.$store.getters.user
        },
        getUserDecks() {
            return this.getUser.decks
        }
    }
}
</script>

<style scoped>
    .deck-box {
        width: 15rem;
        height: 20rem;
        border: solid white 1px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    img { width: 10rem;}
</style>