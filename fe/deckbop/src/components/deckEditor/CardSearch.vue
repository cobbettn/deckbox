<template>
    <div class="card-search">
        <h3>card search</h3>   
            <form id="search-form"> 
            <input type="text" v-model="searchText">
            <button v-on:click.prevent="search()">search</button>
        </form>
    </div>    
</template>

<script>
export default {
    name: "CardSearch",
    data: () => {
        return {
            searchText: '',
        }
    },
    methods:{
        search: function(){
            const vm = this
            this.$http.get(
                `https://api.scryfall.com/cards/search/?q=${vm.searchText}`)
            .then((req) => {
                this.$store.dispatch('SET_SEARCH_RESULTS', req.data)
            })
            .catch((err) => {
                console.log("search error: ", err)
            })
        }
    }
}
</script>

<style scoped>
   
</style>