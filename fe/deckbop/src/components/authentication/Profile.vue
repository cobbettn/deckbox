<template>
  <div class="profile">
    <div>
      <div class="error" v-if="errorList.length > 0">{{ errorList[0] }}</div>
      <div class="success" v-if="successMsg">{{ successMsg }}</div>
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
import { userUrl, jsonContentHeader, authTokenFactory } from '../../config/api'
// cant bundle bcrypt with webpack using normal import
const bcrypt = require('../../../node_modules/bcryptjs/') 
const defaultState = {
  newEmail: '',
  newUsername: '',
  newPassword: '',
  currentPassword: '',
  errorList: [],
  successMsg: '',
}
export default {
  name: 'Profile',
  data() {
    return {...defaultState}
  },
  methods: {
    tryUpdate(field) {
      this.errorList = []
      this.successMsg = ''
      this.currentPassword = prompt("enter password")
      if (this.checkPassword()) {
        this.updateUser(field)
      }
      else {
        this.errorList.push('wrong password')
      }
    },
    checkPassword() {
      return bcrypt.compareSync(this.currentPassword, this.$store.getters.user.password)
    },
    getUpdateRequestBody(field) {
      const reqBody = {
        credentials: {}
      }
      if (field === 'username') {
        reqBody.credentials.username = this.newUsername
      }
      if (field === 'email') {
        reqBody.credentials.email = this.newEmail
      }
      if (field === 'password') {
        reqBody.password = this.newPassword
      }
      return reqBody
    },
    updateUser(field) {
      const vm = this
      const { userId, token } = vm.$store.getters.user
      const requestUrl = `${userUrl}/${userId}`
      const reqBody = this.getUpdateRequestBody(field)
      const reqHeaders = {
        headers: {
          ...jsonContentHeader,
          ...authTokenFactory(token)
        }
      }
      this.$http.post(
        requestUrl,
        reqBody,
        reqHeaders
      )
      .then(({data}) => {
        this.successMsg = `${field} succesfully updated`
        this.$store.dispatch('UPDATE_USER', {...data, token: token})
      })
      .catch(() => this.errorList.push(`error updating ${field}`))
    },
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
    div.success {
        color: #14a76c;
    }
    dialog {
      background: #272727
    }
</style>