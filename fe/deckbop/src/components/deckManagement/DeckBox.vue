<template>
    <div class="deck-box">
        <h3>{{deck.deck_name}}</h3>
    </div>    
</template>

<script>
export default {
    name: "DeckBox",
    props: ["deck"]
}
</script>

<style scoped>
    .deck-box {
        border: 1px white solid;
        width: 10vw;
        height: 10vh;
        padding: 5em;
        text-align: center;
    }
</style>