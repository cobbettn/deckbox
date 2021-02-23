<template>
    <div class="deck-editor">
        <deck-info v-if="viewSearch" class="container" id="deck-info"></deck-info>
        <card-search v-else class="container" id="search"></card-search>    
        <card-search-results class="container" id="search-results"></card-search-results>
        <deck-display class="container" id="deck-display"></deck-display>
    </div>    
</template>

<script>
import CardSearch from './CardSearch';
import CardSearchResults from './CardSearchResults';
import DeckDisplay from './DeckDisplay';
import DeckInfo from './DeckInfo.vue';
export default {
    name: "DeckEditor",
    components: { 
      CardSearch,
      CardSearchResults,
      DeckDisplay,
      DeckInfo,
    },
    computed: {
        viewSearch() {
            return this.$store.getters.viewSearch
        },
    },
    mounted() {
        this.$store.dispatch("SET_SEARCH_RESULTS", {data: ""});
    }
}
</script>

<style scoped>
    .deck-editor{
        height: 93vh;
        width: auto;
        display: grid;
        grid-template-columns: 1fr 6fr;
        grid-template-rows: 1fr 3fr;
        grid-template-areas:
                    "info-search search-results"
                    "info-search deck-display";
        grid-gap: 1%;
        padding: 1%;
    }

    .container {
        border: #747474 solid;
        border-radius: 1em;
        padding: 1em;
    }

    #search {
        grid-area: info-search;
    }
    #deck-info {
        grid-area: info-search;
    }
    #search-results {
        grid-area: search-results;
    }   
    #deck-display {
        grid-area: deck-display;
    }

</style>