<template>
    <div class="login">
        <h1>Login</h1>
        <form id="login-form">
            
            <!-- remove -->
            <h2>store token:</h2>
            <div>{{ this.$store.state.user.jwt }}</div>
            <!-- remove -->

            <h2>Username</h2>
            <input type="text" v-model="username">
            <h2>Password</h2>
            <input type="password" v-model="password">
            <button v-on:click.prevent="login()">login</button>
        </form>
    </div>
</template>

<script>
import { StatusCodes } from 'http-status-codes'
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
            const vm = this;
            const reqBody = {
                credentials: {username: vm.username},
                password: vm.password
            }
            const reqHeaders = {"Content-Type":"application/json"};
            this.$http.post(
                "http://localhost:8081/user/login",
                reqBody,
                reqHeaders
            ).then((res) => {
                if (res?.status === StatusCodes.OK) {
                    this.$store.state.user.jwt = res.data.token;
                }
            })
            .catch(err => {
                console.log(err, 'bad credentials');
            });
        }
    }

}
</script>

<style scoped>
    .login {
        display: flex;
        flex-flow: column;
        flex: 0 0 100%;
        align-items: center;
        border: 1px #747474 solid;
        border-radius: 2em;
        margin: 10em 15em;
        padding: 5em;
        
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
</style>