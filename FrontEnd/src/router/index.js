import Vue from 'vue'
import VueRouter from 'vue-router'

// accounts
import Login from '@/views/accounts/login.vue'
import MyData from '@/views/accounts/mydata.vue'
import MyPage from '@/views/accounts/mypage.vue'
import Signup from '@/views/accounts/signup.vue'
import FriendPage from '@/views/accounts/friendpage.vue'

//tables
import TableList from '@/views/tables/table-list.vue'
import Table from '@/views/tables/table.vue'

//main
import Main from '@/views/main.vue'

import store from '../store'

Vue.use(VueRouter)

const beforeAuth = isAuth => (from, to, next) => {
  const isLogin = store.getters['accounts/getIsLogin'];
  ((isLogin && isAuth) || (!isLogin && !isAuth)) ? next() : next({ name : 'Login', replace: true });
}

const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main,
    beforeEnter: beforeAuth(true)
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup,
  },
  {
    path: '/signup/kakao',
    name: 'SignupKakao',
    component: () => import('@/views/accounts/signup-kakao'),
    props: true
  },
  {
    path: '/tables',
    name: 'TableList',
    component: TableList,
    beforeEnter: beforeAuth(true)
  },
  {
    path: '/mypage/:userId',
    name: 'MyPage',
    component: MyPage,
    beforeEnter: beforeAuth(true)
  },
  {
    path: '/friendpage/:userId',
    name: 'FriendPage',
    component: FriendPage,
    beforeEnter: beforeAuth(true)
  },
  {
    path: '/mydata/:userId',
    name: 'MyData',
    component: MyData,
    props: true,
    beforeEnter: beforeAuth(true)
  },
  {
    path: '/table/:roomId',
    name: 'Table',
    component: Table,
    props: true,
    beforeEnter: beforeAuth(true)
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
