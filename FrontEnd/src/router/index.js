import Vue from 'vue'
import VueRouter from 'vue-router'

// accounts
import Login from '@/views/accounts/login.vue'
import MyData from '@/views/accounts/mydata.vue'
import MyPage from '@/views/accounts/mypage.vue'
import Signup from '@/views/accounts/signup.vue'

//tables
import TableList from '@/views/tables/table-list.vue'
import Table from '@/views/tables/table.vue'

//main
import Main from '@/views/main.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/tables',
    name: 'TableList',
    component: TableList
  },
  {
    path: '/mypage/:userId',
    name: 'MyPage',
    component: MyPage
  },
  {
    path: '/mydata/:userId',
    name: 'MyData',
    component: MyData
  },
  {
    path: '/table/:roomId',
    name: 'Table',
    component: Table,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
