// import router from "@/router/index.js"
import jwt from '@/common/jwt'
import api from '@/common/api'

const accounts = {
  namespaced: true,
  state: {
    user: null,
    token: {
      accessToken: jwt.getToken(),
      fcmToken: '',
    },
    isLogin: !!jwt.getToken(),
  },
  getters: {
    getUser: (state) => state.user,
    getIsLogin: (state) => state.isLogin,
    getAccessToken: (state) => state.token.accessToken,
    getFcmToken: (state) => state.token.fcmToken,
  },
  mutations: {
    LOGIN: (state, payload = {}) => {
      state.token.accessToken = payload.accessToken
      state.user = payload.user
      state.isLogin = true
      jwt.saveToken(payload.accessToken)
    },
    USER_IMG_UPDATE: (state, payload) => {
      state.user.imgUrl = payload;
    },
    USER_UPDATE: (state, userInfo) => {
      state.isLogin = true
      state.user = userInfo
    },
    LOGOUT: (state) => {
      state.token.accessToken = ''
      state.isLogin = false
      state.user = null
      jwt.destroyToken()
    },
    SET_FCM_TOKEN: (state, payload) => {
      state.token.fcmToken = payload
    }
  },
  actions: {
    login: (context, { id, password }) => new Promise((resolve, reject) => {
      api
        .post('/users/login', {
          id, password,
          fcmToken: context.getters['getFcmToken']
        })
        .then(response => {
          const { data } = response

          context.commit('LOGIN', {
            accessToken: data.accessToken,
            user: data.user,
          })

          resolve(response)
        })
        .catch(error => reject(error))
    }),
    loginKakao: (context, { id }) => new Promise((resolve, reject) => {
      api
        .post('/users/social/kakao/login', {
          id,
          fcmToken: context.getters['getFcmToken']
        })
        .then(response => {
          const { data } = response

          context.commit('LOGIN', {
            accessToken: data.accessToken,
            user: data.user,
          })

          resolve(response)
        })
        .catch(error => reject(error))
    }),
    signup: (context, payload) => new Promise((resolve, reject) => {
      api
        .post('/users', payload)
        .then(response => {
          resolve(response)
        })
        .catch(error => reject(error))
    }),
    userUpdate: ({ commit }, userInfo) => {
      commit('USER_UPDATE', userInfo)
    },
    userImgUpdate: ({ commit }, payload) => {
      commit('USER_IMG_UPDATE', payload)
    },
    logout: ({ commit }) => new Promise(resolve => {
      api
        .post('/users/logout')
        .then(() => {
          commit('LOGOUT')
          resolve({})
        })
    }),
    setFcmToken: ({ commit }, payload) => {
      commit('SET_FCM_TOKEN', payload)
    }
  }
}

export default accounts