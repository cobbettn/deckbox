<template>
  <div class="profile">
    <div>
      <div v-if="error.length > 0">{{ error }}</div>
      
      <div class="field">
        <div>new email:</div>
        <input type="email" v-model="newEmail">
        <button @click="tryUpdate('email')">change email</button>
      </div>
      
      <div class="field">
        <div>new username: </div>
        <input type="text" v-model="newUsername">
        <button @click="tryUpdate('username')">change username</button>
      </div>

      <div class="field">
        <div>new password:</div>
        <input type="password" v-model="newPassword">
        <button @click="tryUpdate('password')">change password</button>
      </div>
    </div>

  </div>

</template>

<script>
import { userUrl } from '../../config/api'

// cant bundle bcrypt with webpack
const bcrypt = require('../../../node_modules/bcryptjs/') 

const defaultState = {
  newEmail: '',
  newUsername: '',
  newPassword: '',
  currentPassword: '',
  passwordDialogOpen: false,
  error: '',
}

export default {
  name: 'Profile',
  data() {
    return defaultState
  },
  methods: {
    tryUpdate(field) {
      this.currentPassword = prompt("enter password")
      if (this.checkPassword()) {
        this.updateUser(field)
      }
      else {
        this.error = 'wrong password'
      }
    },
    checkPassword() {
      return bcrypt.compareSync(this.currentPassword, this.$store.getters.user.password)
    },
    updateUser(field) {
      const vm = this
      const requestUrl = `${userUrl}/${vm.$store.getters.user.userId}`
      const reqBody = this.getUpdateRequestBody(field)
      const config = {
        headers: {
          "Authorization": 'Bearer ' + this.$store.getters.user.token,
          "Content-Type": "application/json"
        }
      }
      this.$http.post(
        requestUrl,
        reqBody,
        config
      )
      .then(({data}) => {
        const token = this.$store.getters.user.token;
        this.$store.dispatch('UPDATE_USER', {...data, token: token})
      })
      .catch(err => console.log(err))
    },
    getUpdateRequestBody(field) {
      return {
        credentials: {
          username: field === 'username' ?
            this.newUsername :
            this.$store.getters.user.username,
          email: field === 'email' ?
            this.newEmail :
            this.$store.getters.user.email
        },
        password: field === 'password' ?
          this.newPassword :
          this.currentPassword
      }
    },
    getPasswordFromDialog() {
      this.passwordDialogOpen = false
    }
  }
}

</script>
    
<style scoped>
    @import '../../style/style.css';
    .profile {
      display: flex;
      flex-flow: column;
      flex-direction: column;
      flex: 0 0 100%;
      align-items: center;
      padding: 5em;
      width: auto;
      margin: 1rem;
    }
    .field {
      display: flex;
      justify-content: flex-end;
    }
    .field > * {
      margin: 0.5rem;
    }
    input {
        background-color:#505050;
        color: #ffffff;
        border-top: #747474;
        border-left: #747474;
        border-right: #747474;
    } 
    input:focus{
        outline-width: 0;
    }
    button {
        width: 15rem;
        height: 1.75rem;
        background: #14a76c;
        border: none;
        border-radius: 5em;
        font-size: 1.2em;
    }
    button:hover {
      background: #13925f;
    }
    button:active{
      background: #0d6843;
    }
    div.error {
        color: red;
    }
    dialog {
      background: #272727
    }
</style>