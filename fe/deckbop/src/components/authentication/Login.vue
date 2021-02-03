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
              <button class="registerBtn" v-on:click.prevent="goToRegister()">register now</button>
            </div>
        </form>
    </div>
</template>

<script>
import { StatusCodes } from 'http-status-codes'
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
            ).then(({status, data}) => {
                if (status === StatusCodes.OK) {
                    this.$store.dispatch('LOGIN', data)
                    this.$router.push('/viewDecks')
                }
            }).catch(error => {
                this.errorList = error.response.data.errorList
            });
        },
        goToRegister() {
            this.$router.push('/register')
        }
    }

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
    button {
        width: 80%;
        background: #14a76c;
        border: none;
        border-radius: 5em;
        font-size: 1.2em;
    }
    div.error {
        color: red;
    }
    .mt4 {
        margin-top: 4rem;
    }
    .registerBtn {
        width: 60%;
        margin: 1rem;
    }
</style>