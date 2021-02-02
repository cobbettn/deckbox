<template>
    <div class="deck-display">
        <div class="card-stack">
            <span class="card"  v-for="(card, index) in filterByCMC0" :key="index"  v-on:click="removeFromDeck(card)">
                <Card v-bind:card="card"/>
            </span>
        </div>
        <div class="card-stack">
            <span class="card"  v-for="(card, index) in filterByCMC1" :key="index"  v-on:click="removeFromDeck(card)">
                <Card v-bind:card="card"/>
            </span>
        </div>
        <div class="card-stack">
            <span class="card"  v-for="(card, index) in filterByCMC2" :key="index"  v-on:click="removeFromDeck(card)">
                <Card v-bind:card="card"/>
            </span>
        </div>
        <div class="card-stack">
            <span class="card"  v-for="(card, index) in filterByCMC3" :key="index"  v-on:click="removeFromDeck(card)">
                <Card v-bind:card="card"/>
            </span>
        </div>
        <div class="card-stack">
            <span class="card"  v-for="(card, index) in filterByCMC4" :key="index"  v-on:click="removeFromDeck(card)">
                <Card v-bind:card="card"/>
            </span>
        </div>
        <div class="card-stack">
            <span class="card"  v-for="(card, index) in filterByCMC5" :key="index"  v-on:click="removeFromDeck(card)">
                <Card v-bind:card="card"/>
            </span>
        </div>
        <div class="card-stack">
            <span class="card"  v-for="(card, index) in filterByCMC6" :key="index"  v-on:click="removeFromDeck(card)">
                <Card v-bind:card="card"/>
            </span>
        </div>
        <div class="card-stack">
            <span class="card"  v-for="(card, index) in filterByCMC7Plus" :key="index"  v-on:click="removeFromDeck(card)">
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
    methods: {
        removeFromDeck: function (card){
            this.$store.dispatch('REMOVE_FROM_DECK', card)
        }
    },
    computed: {
        filterByCMC0: function() {
            return this.$store.getters.deck.cards.filter(
                card => card.cmc == 0 && !card.type_line.includes("Land"))
        },
        filterByCMC1: function() {
            return this.$store.getters.deck.cards.filter(card => card.cmc == 1)
        },
        filterByCMC2: function() {
            return this.$store.getters.deck.cards.filter(card => card.cmc == 2)
        },
        filterByCMC3: function() {
            return this.$store.getters.deck.cards.filter(card => card.cmc == 3)
        },
        filterByCMC4: function() {
            return this.$store.getters.deck.cards.filter(card => card.cmc == 4)
        },
        filterByCMC5: function() {
            return this.$store.getters.deck.cards.filter(card => card.cmc == 5)
        },
        filterByCMC6: function() {
            return this.$store.getters.deck.cards.filter(card => card.cmc == 6)
        },
        filterByCMC7Plus: function() {
            return this.$store.getters.deck.cards.filter(card => card.cmc >= 7)
        },
        filterByLand: function() {
            return this.$store.getters.deck.cards.filter(card => card.type_line.includes("Land"))
        },
        populateDeck: function() {
            console.log(this.$store.getters.deck.cards)
            return this.$store.getters.deck.cards
        }
    }
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
