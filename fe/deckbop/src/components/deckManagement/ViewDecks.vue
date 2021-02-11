<template>
    <div>
        <div v-if="showLoggedInView" class="new-deck">
            <router-link @click.native="setEditorModeToCreate" to="/deckEditor"><h1>+ New Deck</h1></router-link>
        </div>
        <div class="view-decks">
            <div v-for="(deck, index) in getUserDecks" :key="index">
                <DeckBox v-bind:deck="deck" />
            </div>
        </div>    
    </div>
    
</template>

<script>
import { scryfallCollectionUrl } from '../../config/scryfall';
import DeckBox from "./DeckBox";
export default {
    name: "ViewDecks",
    components: {
        DeckBox
    },
    methods: {
        setEditorModeToCreate() {
            this.$store.dispatch('SET_EDITOR_MODE', 'create')
        }
    },
    computed: {
        showLoggedInView() {
            return !!this.$store.getters.user.token
        },
        getUserDecks() {
            return this.$store.getters.user.decks
        }
    },
    created() {
        const user = this.$store.getters.user
        if (user.token) {
            const decks = []
            user.decks.forEach(deck => {
                const reqBody = {identifiers:[]}
                deck.cardList.forEach(card => {
                    for (let i = 0; i < card.card_quantity; i++) {
                        reqBody.identifiers.push({id: card.card_id})
                    }
                })
                this.$http.post(
                    scryfallCollectionUrl,
                    reqBody
                )
                .then(res => {
                    decks.push({...deck, cards: res.data.data})
                })
            })
            this.$store.dispatch('UPDATE_USER', {...user, decks : decks})
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


