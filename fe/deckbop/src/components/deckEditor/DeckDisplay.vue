<template>
    <div class="deck-display">
        <div v-for="cmc of cmcColumns.keys()" :key="`${cmc}-cmc`" class="card-stack">
            <span class="card" v-for="(card, index) in filterByCMC(cmc)" :key="index" @click="removeFromDeck(card)">
                <Card v-bind:card="card"/>
            </span>
        </div>
        <div class="card-stack">
            <span class="card"  v-for="(card, index) in filterByLand" :key="index"  v-on:click="removeFromDeck(card)">
                <Card v-bind:card="card"/>
            </span>
        </div>
    </div>
</template>

<script>
import Card from "../shared/Card"
export default {
    name: "DeckDisplay",
    components: {Card},
    data() {
        return {
            cmcColumns: new Array(8),
        }
    },
    methods: {
        removeFromDeck: function (card){
            this.$store.dispatch('REMOVE_FROM_DECK', card)
        },
        filterByCMC: function(cmc) {
            const vm = this
            return vm.getDeck.scryfallCards.filter(
                card => (cmc === this.cmcColumns.length - 1 ? 
                    card.cmc >= this.cmcColumns.length - 1 : 
                    cmc === card.cmc) &&
                    !card.type_line.includes("Land")
            )
        },
    },
    computed: {
        filterByLand: function() {
            return this.getDeck.scryfallCards.filter(card => card.type_line.includes("Land"))
        },
        getDeck() {
            return this.$store.getters.deck
        } 
    },
}
</script>

<style scoped>
    .deck-display {
        display: flex;
        overflow: auto;
    }
    .card-stack{
        display: inline-flex;
        flex-direction: column;
    }
    .card {
        position: relative;
        overflow:hidden;
    }
    
    .card:not(:last-child) {
        margin-bottom: -125%;
    }
</style>