import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'

import router from './router/router'
import store from './store/store'

Vue.config.productionTip = false
Vue.prototype.$http = axios;

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')

