<template>
    <div class="card-search-results">
         <div v-for="(card, index) in populateResults" :key="index"  v-on:click="addToDeck(card)">
            <Card v-bind:card="card"/>
        </div>
    </div>
</template>

<script>
import Card from '../shared/Card'

export default {
    name: "CardSearchResults",
    components: {
        Card,
    },
    methods:{
        addToDeck: function (card) {
            this.getDeck.scryfallCards.push(card)
            this.$store.dispatch('SET_DECK', this.getDeck)
        }
    },
    computed: {
        populateResults() {
            return this.$store.getters.searchResults.data
        },
        getDeck() {
            return this.$store.getters.deck
        }
    }
}
</script>

<style scoped>
    .card-search-results {
        display: flex;
        overflow-x: auto;
        -webkit-overflow-scrolling: touch;
        min-height: 275px;
    }
    
</style>