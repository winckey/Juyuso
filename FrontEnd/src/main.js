import Vue from 'vue'
import App from './App.vue'
import { initializeApp } from "firebase/app";
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import {CalendarHeatmap} from 'vue-calendar-heatmap'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

// Import Bootstrap an BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
// calendar
import VCalendar from 'v-calendar'
Vue.use(VCalendar,{
  componentPrefix: 'vc',
})

Vue.use(VueToast);
Vue.config.productionTip = false
// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

Vue.component('calendarHeatmap', CalendarHeatmap)

const firebaseConfig = {
  apiKey: "AIzaSyDLDx9b0c8DplnM14-WDEErlHHq5N1Xl6Y",
  authDomain: "juyuso-ssafy.firebaseapp.com",
  projectId: "juyuso-ssafy",
  storageBucket: "juyuso-ssafy.appspot.com",
  messagingSenderId: "827632160252",
  appId: "1:827632160252:web:bb51a70eefbaf4f9c9b679"
};

initializeApp(firebaseConfig);

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
