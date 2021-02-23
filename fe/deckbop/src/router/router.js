import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '../components/authentication/Login.vue'
import Register from '../components/authentication/Register.vue'
import ViewDecks from '../components/deckManagement/ViewDecks.vue'
import DeckEditor from '../components/deckEditor/DeckEditor.vue'
import Activate from '../components/authentication/Activate.vue'
import Profile from '../components/authentication/Profile.vue'

import store from '../store/store'

Vue.use(VueRouter)

const routes = [
  {path: '/', component: ViewDecks, meta: {noAuth: true}},
  {path: '/register', component: Register, meta: {noAuth: true}},
  {path: '/activate/:id', component: Activate, meta: {noAuth: true}},
  {path: '/login', component: Login, meta: {noAuth: true}},
  {path: '/viewDecks', component: ViewDecks},
  {path: '/deckEditor', component: DeckEditor},
  {path: '/profile', component: Profile},
]

const router = new VueRouter({
  mode: 'history',
  routes,
})

router.beforeEach((to, from, next) => {
  (store.getters.user.token || to.meta.noAuth) ? next() : next('/login')
})

export default router;