<template>
    <div>
        <div class="new-deck">
            <h1>+ New Deck</h1>
        </div>
        <div class="view-decks">
            <div v-for="(deck, index) in decks" :key="index">
                
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
    props: [],
    data() {
        return {
            decks: [
                {
                deck_id: 1,
                user_id: 1,
                decklist_id: 1,
                deck_name: "burn"
                },
                {
                deck_id: 2,
                user_id: 1,
                decklist_id: 2,
                deck_name: "storm"
                },
                {
                deck_id: 3,
                user_id: 1,
                decklist_id: 3,
                deck_name: "red deck wins"
                }
            ]
            }
    }
}

</script>

<style scoped>
    div {
        padding-top: 3em;
    }
    .view-decks {
        display: flex;
        flex-flow: row wrap;
        justify-content: space-around;
        
    }
    .new-deck {
        padding-left: 2em;
        flex: 1;
    }
</style>


