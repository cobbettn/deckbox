import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '../components/authentication/Login.vue'
import Register from '../components/authentication/Register.vue'
import ViewDecks from '../components/deckManagement/ViewDecks.vue'
import DeckEditor from '../components/deckEditor/DeckEditor.vue'
import Activate from '../components/authentication/Activate.vue'
import Profile from '../components/authentication/Profile.vue'

Vue.use(VueRouter)

const routes = [
  {path: '/', component: ViewDecks},
  {path: '/register', component: Register},
  {path: '/activate/:id', component: Activate},
  {path: '/login', component: Login},
  {path: '/viewDecks', component: ViewDecks},
  {path: '/deckEditor', component: DeckEditor},
  {path: '/deckEditor/:id', component: DeckEditor},
  {path: '/profile', component: Profile},
]

const router = new VueRouter({
  mode: 'history',
  routes,
})

export default router;