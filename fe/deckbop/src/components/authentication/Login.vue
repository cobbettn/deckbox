<template>
    <div class="login">
        <h1>Login</h1>
        <form id="login-form">
            <h2>Username</h2>
            <input type="text" v-model="username">
            <h2>Password</h2>
            <input type="password" v-model="password">
            <div class="column-content">
              <button v-on:click.prevent="login()">login</button>
            </div>
        </form>
    </div>
</template>

<script>
import { StatusCodes } from 'http-status-codes'
import { userLoginUrl } from '../../config/api'
export default {
    name: 'Login',
    data: () => {
        return {
            username: '',
            password: '',
        }    
    },
    methods: {
        login: function () {
            const vm = this
            const reqBody = {
                credentials: {username: vm.username},
                password: vm.password
            }
            const reqHeaders = {"Content-Type":"application/json"}
            this.$http.post(
                userLoginUrl,
                reqBody,
                reqHeaders
            ).then(({status, data}) => {
                if (status === StatusCodes.OK) {
                    this.$store.dispatch('LOGIN', data)
                    this.$router.push('/viewDecks');
                }
            }).catch(error => {
                // error handling
                console.log("login error: ", error)
            });
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
  
    
</style>