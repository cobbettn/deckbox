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
    data: () => {
        return {}
    },
    methods:{
        addToDeck: function (card){
            this.$store.dispatch('ADD_TO_DECK', card)
        }
    },
    computed: {
        populateResults() {
            return this.$store.getters.searchResults.data
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