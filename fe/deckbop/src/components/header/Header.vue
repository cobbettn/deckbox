<template>
    <div class="header">
        <router-link to="/">Deckbox</router-link>
        <div>
            <div v-if="showLogoutLink"> Welcome, {{ getUsername }}</div>
            <router-link v-if="showLogoutLink" to="/profile">profile</router-link>
            <router-link 
                to="/login"
                class="marginLeft" 
                @click.native="showLogoutLink ? onLogout() : null"
            >{{showLogoutLink ? 'logout' : 'login'}}</router-link>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Header',
    computed: {
        showLogoutLink() {
            return !!this.$store.getters.user.token
        },
        getUsername() {
            return this.$store.getters.user.username
        }
    },
    methods: {
        onLogout() {
            this.$store.dispatch('LOGOUT')
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
    .marginLeft {
        margin-left: 0.5rem;
    }
</style>