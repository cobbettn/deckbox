<template>
    <div class="register">
        <h1>Register</h1>
        <form id="register-form">
            <h2>Email</h2>
            <input type="email" v-model="email">
            <h2>Username</h2>
            <input type="text" v-model="username">
            <h2>Password</h2>
            <input type="password" v-model="password">
            <div class="column-content">
                <button v-on:click.prevent="register(email, username, password)">register</button>
            </div>
        </form>
    </div>
</template>

<script>
export default {
    name: 'Register',
    data() {
        return{
            email: '',
            username: '',
            password: '',
        }
    },
    methods: {
        register: function (email, username, password){
            const base_url = "http://localhost/8080/";
            const axios = require('axios');
            
            axios.post(base_url + 'register',
                {
                    username:username,
                    password:password
                },
                {'Content-Type': "application/json"})
            .then(function(response){
                console.log(response);
            })
            .catch((err) => {
                console.log(err);
            });
            //alternative axios syntax
            // axios({
            //     method: 'post',
            //     url: base_url + 'register',
            //     data: {username:username, password:password},
            //     headers: {'Content-Type': "application/json"}
            // })
        }
    }
}
</script>
    
<style scoped>
    @import '../../style/style.css';

    .register {
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