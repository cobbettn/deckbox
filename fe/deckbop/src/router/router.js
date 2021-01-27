import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '../components/authentication/Login.vue'
import Register from '../components/authentication/Register.vue'
import ViewDecks from '../components/deckManagement/ViewDecks.vue'

Vue.use(VueRouter)

const routes = [
  {path: '/login', component: Login},
  {path: '/register', component: Register},
  {path: '/viewDecks', component: ViewDecks},
  {path: '/', component: ViewDecks},
]

const router = new VueRouter({
  routes
})

export default router;