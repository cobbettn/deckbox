<template>
    <div class="sidebar-nav">
        <ul>
            <li 
                v-bind:class="{ 
                    'active-tab': !showSearch,
                    'inactive-tab': showSearch 
                }" 
                v-bind:disabled="!showSearch" 
                v-on:click="toggleView()">
                    Card Search
            </li>
            <li 
                v-bind:class="{ 
                    'active-tab': showSearch,
                    'inactive-tab': !showSearch 
                }"
                v-bind:disabled="showSearch" 
                v-on:click="toggleView()">
                    Deck Info
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    name: "SidebarNav",
    methods: {
        toggleView: function() {
            this.$store.dispatch("TOGGLE_VIEW_SEARCH")
        },
    },
    computed: {
        showSearch() {
            return this.$store.getters.viewSearch
        }
    }
}
</script>

<style>
    .sidebar-nav ul {
        list-style-type: none;
        display: flex;
        justify-content: space-around;
        margin-bottom: 2em;
    }

    .sidebar-nav li {
        text-align: center;
        width: 100%;
        padding: 1em;
        border-top-left-radius: 1em;
        border-top-right-radius: 1em;
        font-size: 1.1em;
    }
    .active-tab {
        color: #14a76c;
        background: #747474;
        pointer-events: none;
        font-weight: bold;
    }

    .inactive-tab {
       
        color:#fff;
    }
</style>