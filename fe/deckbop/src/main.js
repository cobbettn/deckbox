import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'

// Import components here for routes
import Register from './components/authentication/Register.vue'
import Login from './components/authentication/Login.vue'
import ViewDecks from './components/deckManagement/ViewDecks.vue'

Vue.config.productionTip = false
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

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

