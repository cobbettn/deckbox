<template>
    <div class="deck-editor">
        <card-search id="a"></card-search>
        <card-search-results id="b"></card-search-results>
        <deck-display id="c"></deck-display>
    </div>    
</template>

<script>
import CardSearch from './CardSearch';
import CardSearchResults from './CardSearchResults';
import DeckDisplay from './DeckDisplay';
export default {
  components: { 
      CardSearch,
      CardSearchResults,
      DeckDisplay },
    
}
</script>

<style scoped>
    .deck-editor{
        height: 80vh;
        width: 100%;
        display: grid;
        grid-template-columns: 1fr 3fr;
        grid-template-rows: 1fr 3fr;
        grid-template-areas:
                    "a b"
                    "a c";
    }
    #a {
        grid-area: a;
        border: red solid;
    }
    #b {
        grid-area: b;
        border: red solid;
    }   
    #c {
        grid-area: c;
        border: red solid;
    }

</style>