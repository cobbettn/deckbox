<template>
    <div class="register">
        <h1>Register</h1>
        <pulse-loader :loading="isLoading" :color=spinnerColor></pulse-loader>
        <form id="register-form">
            <div class="error" v-for="(error, i) in errorList" :key="i">{{ error }}</div>
            <div class="success" v-if="successMsg">{{successMsg}}</div>
            <h2>Username</h2>
            <input type="text" v-model="username">
            <h2>Email</h2>
            <input type="email" v-model="email">
            <h2>Password</h2>
            <input type="password" v-model="password">
            <div class="column-content">
                <button v-on:click.prevent="register()">register</button>
            </div>
        </form>
    </div>
</template>

<script>
import { userRegistrationUrl , jsonContentHeader} from '../../config/api'
import PulseLoader from 'vue-spinner/src/PulseLoader.vue'
export default {
    name: 'Register',
    components: {PulseLoader},
    data() {
        return{
            email: '',
            username: '',
            password: '',
            errorList: [],
            successMsg: '',
            isLoading: false,
            spinnerColor: "#14a76c",
        }
    },
    methods: {
        register() {
            this.errorList = []
            this.successMsg = ''
            const vm = this
            const reqBody = {
                credentials: {username: vm.username, email: vm.email},
                password: vm.password
            }
            if (vm.username && vm.email && vm.password){
                vm.isLoading = true
                this.$http.post(
                    userRegistrationUrl,
                    reqBody,
                    jsonContentHeader
                ).then(() => {
                    this.successMsg = 'congrats, check your email for activation link'
                }).catch(error => {
                    this.errorList = error.response.data.errorList
                }).finally(() => {
                    this.isLoading = false
                });
            } 
            else {
                if (!vm.username){
                    this.errorList.push('please provide a username')
                }
                if (!vm.password){
                    this.errorList.push('please provide a password')
                }
                if (!vm.email){
                    this.errorList.push('please provide an email to register with')
                }
            }
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
     div.success {
        color: #14a76c;
    }
</style>