<template>
    <div>
        <div class="new-deck">
            <h1>+ New Deck</h1>
        </div>
        <div class="view-decks">
            <div v-for="deck in decks" :key="deck">
                <DeckBox v-bind:deck="deck" />
            </div>
        </div>    
    </div>
    
</template>

<script>
import DeckBox from "./DeckBox";

export default {
    name: "ViewDecks",
    components: {
        DeckBox
    },
    props: ["decks"],
}

</script>

<style scoped>
    div {
        padding-top: 3em;
    }
    .view-decks {
        display: flex;
        justify-content: space-around;
        
    }
    .new-deck {
        padding-left: 2em;
    }
</style>


