<template>
  <div class="d-flex justify-content-center my-auto">
    <div class="login-box rounded-lg">
      <div  class="mx-auto p-4">
          <v-form ref="form" v-model="valid" lazy-validation @submit.prevent="login">
            <span>
              <h2 class="my-3 text-center">🍻 적셔 🍻</h2>
            </span>
            <v-text-field
            v-model="credentials.id"
            :counter="15"
            :rules = "idRules"
            label="아이디"
            required>
            </v-text-field>

            <v-text-field
            :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
            :type="passwordShow ? 'text' : 'password'"
            @click:append="passwordShow = !passwordShow"
            v-model="credentials.password"
            hint="영어, 숫자, 특수문자를 모두 포함해야합니다 (9-16자)"
            :rules="passwordRules"
            label="비밀번호"
            required
            @keyup.enter="login"></v-text-field>
            
            <span class="d-flex justify-content-center my-3">
              <v-btn @click="login" color="#1CFD9F" rounded>로그인</v-btn>
            </span>

            <span class="d-flex justify-content-center my-3">
              <v-btn @click="$router.push({ name: 'Signup' })" color="indigo lighten-2"  rounded>회원가입</v-btn>
            </span>

            <span class="d-flex justify-content-center my-3">
              <v-btn @click="oAuth" color="indigo lighten-2"  rounded>카카오로 로그인</v-btn>
            </span>
            
          </v-form>

          <!-- <span class="d-flex justify-content-center">
            <v-btn plain @click="goPasswordFind">비밀번호 찾기</v-btn>    
          </span> -->


      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import {mapActions} from 'vuex'
import { getMessaging, getToken } from 'firebase/messaging'

const accounts = 'accounts' 

export default {
  name: 'Login',
  data: function () {
    return {
      valid: true,
      passwordShow: false,
      credentials: {
        id: null,
        password: null,
        fcmToken: null,
      },
      idRules: [
        v => !!v || "아이디를 입력해주세요.",
      ],
      passwordRules: [
        v => !!v || "비밀번호를 입력해주세요.",
      ]
    }
  },
  created() {
    // get kakao oauth authorization code
    console.log('created() : get Auth Code!!')
    let authCode = this.$route.query.code;
    authCode && this.kakaoAuth(authCode);
  },
  methods: {
    ...mapActions(accounts, ['userUpdate']),
    ...mapActions('openviduStore', ['initSession']),
    getFcmToken() {
      const messaging = getMessaging();
      const PUBLIC_VAPID_KEY = 'BNEXCWddmnyA6pokCD8W5cGv9JBI6gA2IeDlf7RbP9VzVoXN23r8J-ULN-bdkAyS6gB0aVw7DUNokhdSUuNfdmU';
      return getToken(messaging, { vapidKey: PUBLIC_VAPID_KEY });
    },
    login() {
      this.getFcmToken()
        .then(token => {
          if (token) {
            this.credentials.fcmToken = token
            axios({
              method: 'post',
              url: `${process.env.VUE_APP_API_URL}/users/login`,
              data: this.credentials
            })
            .then(res => {
              localStorage.setItem('jwt', res.data.accessToken)
              this.userUpdate(res.data.user)
              this.initSession(res.data.user)
              this.$router.push({name:'Main'})
            })
            .catch(err => {
              console.log(err)
              this.credentials.id = null
              this.credentials.password = null
            })
            console.log('fcm getToken ', token);
          } else {
            // Show permission request UI
            console.log('No registration token available. Request permission to generate one.');
          }
        }
      ).catch((err) => {
        console.log('An error occurred while retrieving token. ', err);
        // ...
      });
      
    },
    // goPasswordFind: function () {
    //   this.$router.push({name:'PasswordFind'})
    // },
    oAuth() {
      let REST_API_KEY = '54ef6bedc90c5d1d07c7813bdd123278';
            // let REDIRECT_URI = `http://localhost:3000/login`;
      let REDIRECT_URI = `${process.env.VUE_APP_BASE_URL}/login`;
      window.location.replace(
        `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}`
      );
    },
    kakaoAuth(authCode) {
      console.log('Starting KAKAO Auth ', authCode)
      // TODO: 현재 경로 라우터 히스토리에서 제거
      
      // axios.get(`http://localhost:8080/api/oauth/kakao?code=${authCode}`)
      axios.get(`${process.env.VUE_APP_API_URL}/oauth/kakao?code=${authCode}`)
        .then((response) => {
          console.log('oAuth response', response)
          const { join, info } = response.data;

          join ? 
            this.getFcmToken()
              .then(token => {
                if (token) {
                  axios.post(`${process.env.VUE_APP_API_URL}/users/social/kakao/login`, {
                    id: info.id,
                    fcmToken: token
                  }).then(res => {
                    localStorage.setItem('jwt', res.data.accessToken)
                    this.userUpdate(res.data.user)
                    this.initSession(res.data.user)
                    this.$router.push({name:'Main'})
                  }).catch(err => {
                    console.log(err)
                    this.credentials.id = null
                    this.credentials.password = null
                  })
                } else {
                  // Show permission request UI
                  console.log('No registration token available. Request permission to generate one.');
                }
              }).catch((err) => {
                console.log('An error occurred while retrieving token. ', err.response);
              }
            )
          : this.$router.push({
              name: 'SignupKakao',
              params: {
                oAuth: true,
                info
              }
            })
          }
        )
        .catch(error => {
          console.log(error.response);
        })
    }
  }
}
</script>

<style>
.login-box {
  width: 450px;
  background: #FBF8F8
}
</style>
