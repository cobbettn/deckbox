<template>
    <div class="card-search">
       <sidebar-nav></sidebar-nav>
        <div>
            <form class="column-content" id="search-form" > 
                <input type="text" v-model="searchText" placeholder="Search">
                <button v-on:click.prevent="search()">search</button>
            </form>
        </div>
    </div>    
</template>

<script>
import SidebarNav from './SidebarNav.vue'
export default {
  components: { SidebarNav },
    name: "CardSearch",
    data: () => {
        return {
            searchText: ''
        }
    },
    methods: {
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
        },
        
    },
    computed:{
        
    }
}
</script>

<style scoped>
    @import "../../style/style.css";
    

    
    
</style>