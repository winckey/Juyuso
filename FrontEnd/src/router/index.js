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
    component: Signup,
    props: true
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
    component: TableList
  },
  {
    path: '/mypage/:userId',
    name: 'MyPage',
    component: MyPage
  },
  {
    path: '/friendpage/:userId',
    name: 'FriendPage',
    component: FriendPage
  },
  {
    path: '/mydata/:userId',
    name: 'MyData',
    component: MyData,
    props: true
  },
  {
    path: '/table/:roomId',
    name: 'Table',
    component: Table,
    props: true
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// router.beforeEach((to, from, next) => {
//   console.log(to)
//   console.log(from)
//   console.log(next)

//   let { name } = to;

//   if (name === 'Main' || name === 'Login' || name === 'Signup' || name === 'SignupKakao') {
//     if (localStorage.getItem('jwt')) {
//       alert('이미 로그인을 했으므로, 해당 페이지 이동이 제한됩니다.');
//       next({
//         name: 'TableList',
//         replace: true,
//       });
//     } else {
//       next();
//     }
//   } else {
//     // 인증이 필요한 모든 라우터 path에 대해, 로그인 여부 체크
//     // jwt 토큰이 없으면, 로그인 페이지로 이동한다.
//     !localStorage.getItem('jwt') ?
//       next({
//         name: 'Login',
//         replace: true,
//       })
//     : next();
//   }
// });

export default router
