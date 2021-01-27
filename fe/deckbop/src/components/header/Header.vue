<template>
    <div class="header">
        <h2>DeckBop</h2>
        <div>
            <h2>
                Welcome {{ user }} 
                <a v-if="showLogoutLink" v-on:click="onLogout" href="/">logout</a>
                <router-link v-else to="/login">login</router-link>
            </h2>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Header',
    props: {
        user: String
    },
    computed: {
        showLogoutLink() {
            return !!this.$store.getters.getUserJwt
        }
    },
    methods: {
        onLogout() {
            this.$store.dispatch('clearUserJwt')
            this.$router.push('/login')
        }
    }
}
</script>

<style scoped>

    .header{
        padding: .7em;
        background: #747474;
        color: #272727;
        display: flex;
        justify-content: space-between;
    }
</style>