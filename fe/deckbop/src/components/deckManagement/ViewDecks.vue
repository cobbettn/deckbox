<template>
    <div>
        <div v-if="showLoggedInView" class="new-deck">
            <router-link to="/deckEditor"><h1>+ New Deck</h1></router-link>
        </div>
        <div class="view-decks">
            <div v-for="(deck, index) in getDecks" :key="index">
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
    computed: {
        showLoggedInView() {
            return !!this.$store.getters.user.token
        },
        getDecks() {
            // TODO: call api
            return [
                { 
                    id: 1,
                    name : "isochron", 
                    userId : 1, 
                    cardList: [
                        { card_id: "2aa24fe0-e275-4307-b26c-2a656068a451", card_quantity: 1 }
                    ] 
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
        justify-content: center;
    }
    .view-decks > * {
        padding: 0.5rem;
    }
    .new-deck {
        padding-left: 2em;
        flex: 1;
    }
</style>


