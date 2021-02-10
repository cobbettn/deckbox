<template>
    <div @click="edit" class="deck-box">
        <h3>{{deck.name}}</h3>
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
    mounted() {    
        // data.image_uris.art_crop needs to be saved to the deck table for one of the cards in the deck
        // load the url here and assign to imgSrc
    },
    methods: {
        edit() {
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