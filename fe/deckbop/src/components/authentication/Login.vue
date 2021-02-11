<template>
    <div class="login">
        <h1>Login</h1>
        <form id="login-form">
            <div class="error" v-for="(error, i) in errorList" :key="i">{{ error }}</div>
            <h2>Username</h2>
            <input type="text" v-model="username">
            <h2>Password</h2>
            <input type="password" v-model="password">
            <div class="column-content">
              <button v-on:click.prevent="login()">login</button>
              <h3 class="mt4">Don't have an account?</h3>
              <router-link class="registerLink" to="/register">register now</router-link>
            </div>
        </form>
    </div>
</template>

<script>
import { userLoginUrl, jsonContentHeader } from '../../config/api'
export default {
    name: 'Login',
    data: () => {
        return {
            username: '',
            password: '',
            errorList: [],
        }    
    },
    methods: {
        login: function () {
            const vm = this
            const reqBody = {
                credentials: {username: vm.username},
                password: vm.password
            }
            this.$http.post(
                userLoginUrl,
                reqBody,
                jsonContentHeader
            ).then(({data}) => {
                this.$store.dispatch('LOGIN', data)
                this.$router.push('/viewDecks')
            }).catch(error => {
                this.errorList = error.response?.data.errorList
            });
        },
        goToRegister() {
            this.$router.push('/register')
        }
    },
}
</script>

<style scoped>
    @import '../../style/style.css';
    .login {
        display: flex;
        flex-flow: column;
        flex: 0 0 100%;
        align-items: center;
        padding: 5em;
        width: auto;
    }
    h1 {
        padding-bottom: 2em;
    }
    input {
        margin-bottom: 3em;
        background: #272727;
        color: #ffffff;
        border-top: #747474;
        border-left: #747474;
        border-right: #747474;
    } 
    input:focus{
        outline-width: 0;
    }
    div.error {
        color: red;
    }
    .mt4 {
        margin-top: 4rem;
    }
    .registerLink {
        color: #14a76c;
        display: flex;
        justify-content: center;
    }
</style>