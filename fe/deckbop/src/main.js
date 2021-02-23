import Vue from 'vue'
import Toasted from 'vue-toasted';
import App from './App.vue'

import router from './router/router'
import store from './store/store'
import http from './http/http'

Vue.config.productionTip = false
Vue.prototype.$http = http
Vue.use(Toasted)

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')

