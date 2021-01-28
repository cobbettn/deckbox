<template>
    <div class="deck-display">
        <div v-for="(card, index) in populateDeck" :key="index"  v-on:click="removeFromDeck(card)">

            <Card v-bind:card="card"/>
        </div>
    </div>
</template>

<script>
import Card from "../shared/Card"
export default {
    name: "DeckDisplay",
    components: {Card},
    methods:{
        removeFromDeck: function (card){
            this.$store.commit('REMOVE_FROM_DECK', card)
        }
    },
    computed: {
        populateDeck: function() {
            return this.$store.state.deck.cards
        }
    }
}
</script>

<style scoped>
    .deck-display{
        display: flex;
    }
</style>