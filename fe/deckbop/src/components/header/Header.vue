<template>
    <div class="header">
        <h2>DeckBop</h2>
        <div>
            <h2>
                Welcome {{ user }} 
                <router-link  
                    to="/login" 
                    @click.native="showLogoutLink ? onLogout() : null"
                >
                   {{ showLogoutLink ? 'logout' : 'login' }}
                </router-link>
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
            return !!this.$store.getters.userJwt
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
</style>