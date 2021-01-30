<template>
    <div class="register">
        <h1>Register</h1>
        <p v-if="errors.length"> 
            <ul>
                <li v-for="(error, i) in errors" :key="i">{{ error }}</li>
            </ul>
        </p>
        <form id="register-form">
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
</style>