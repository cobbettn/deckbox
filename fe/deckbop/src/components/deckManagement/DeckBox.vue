<template>
    <div @click="editDeck" class="deck-box">
        <h3>{{ deck.name }}</h3>
        <img :src="imgSrc"/>
    </div>    
</template>

<script>
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
            console.log('edit deck', this.deck)
            this.$store.dispatch('SET_EDITOR_MODE', 'edit')
            this.$store.dispatch('SET_DECK', {...this.deck})
            this.$router.push(`/deckEditor/${this.deck.id}`)
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