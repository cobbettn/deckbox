<template>
    <div class="register">
        <h1>Register</h1>
        <form id="register-form">
            <div class="error" v-for="(error, i) in errorList" :key="i">{{ error }}</div>
            <h2>Email</h2>
            <input type="email" v-model="email">
            <h2>Username</h2>
            <input type="text" v-model="username">
            <h2>Password</h2>
            <input type="password" v-model="password">
            <button v-on:click.prevent="register(email, username, password)">login</button>
        </form>
    </div>
</template>

<script>
import { userRegistrationUrl } from '../../config/api'
export default {
    name: 'Register',
    data() {
        return{
            email: '',
            username: '',
            password: '',
            errorList: [],
        }
    },
    methods: {
        register: function (){
            const vm = this
            const reqBody = {
                credentials: {username: vm.username, email: vm.email},
                password: vm.password
            }
            const reqHeaders = {"Content-Type":"application/json"}
            this.$http.post(
                userRegistrationUrl,
                reqBody,
                reqHeaders
            ).then((res) => {
                console.log(res);
                this.$router.push('/login')
                
            }).catch((err) => {
                console.log("registration error: ", err);
            });
        }
    }
}

</script>
    
<style scoped>
    .register {
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
    div.error {
        color: red;
    }
</style>