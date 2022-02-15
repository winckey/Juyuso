<template>
  <div class="d-flex justify-content-center my-auto">
    <div class="login-box rounded-lg">
      <div class="mx-auto p-4">
        <v-form ref="loginForm" v-model="valid" lazy-validation @submit.prevent="onLogin">
          <span>
            <h2 class="mt-8 mb-10 text-center">🍻 적셔 🍻</h2>
          </span>
          <v-text-field
          class="mb-4"
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
          :rules="passwordRules"
          label="비밀번호"
          required
          @keyup.enter="onLogin"></v-text-field>
        </v-form>
        <div class="d-flex justify-space-between my-4 mx-50">
          <v-btn class="white--text" @click="onLogin" color="#4DB6AC" rounded>로그인</v-btn>
          <v-btn class="white--text" @click="$router.push({ name: 'Signup' })" color="indigo lighten-2"  rounded>회원가입</v-btn>
        </div>

        <div class="my-4 mx-50">
          <v-btn width="100%" @click="oAuth" color="amber" rounded>
            <img src="@/assets/kakao_symbol.png" width="5%">
            &nbsp;카카오로 로그인
          </v-btn>
        </div>
        <div class="text-center">
          <router-link class="text-decoration-none font-sm" to="#">비밀번호를 잊으셨나요?</router-link>
        </div>
      </div>
    </div>
    <v-dialog
      v-model="isLoading"
      persistent
      width="300"
    >
      <v-card
        color="primary"
        dark
      >
        <v-card-text>
          잠시만 기다려 주십시오...
          <v-progress-linear
            indeterminate
            color="white"
            class="mb-0"
          ></v-progress-linear>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
// import axios from 'axios'
import api from '@/common/api'
import { mapActions } from 'vuex'
import { getMessaging, getToken } from 'firebase/messaging'

const accounts = 'accounts' 

export default {
  name: 'Login',
  data: function () {
    return {
      isLoading: false,
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
    // console.log('created() : get Auth Code!!')
    let authCode = this.$route.query.code;
    authCode && this.kakaoAuth(authCode);
    authCode && (this.isLoading = true);
  },
  methods: {
    ...mapActions(accounts, ['login', 'loginKakao', 'userUpdate']),
    ...mapActions('openviduStore', ['initSession']),
    getFcmToken() {
      const messaging = getMessaging();
      const PUBLIC_VAPID_KEY = 'BNEXCWddmnyA6pokCD8W5cGv9JBI6gA2IeDlf7RbP9VzVoXN23r8J-ULN-bdkAyS6gB0aVw7DUNokhdSUuNfdmU';
      return getToken(messaging, { vapidKey: PUBLIC_VAPID_KEY });
    },
    onLogin() {
      if (!this.$refs.loginForm.validate()) {
          this.$toast.open({
          position: 'top',
          message: '아이디, 비밀번호를 입력하세요.',
          type: 'error',
          duration: 2500,
        });
        return;
      }

      console.log('loginbtn')
      // this.getFcmToken()
      //   .then(token => {
      //     if (token) {
            // this.credentials.fcmToken = token
            this.credentials.fcmToken = 'abcd'
            // axios({
            //   method: 'post',
            //   url: `${process.env.VUE_APP_API_URL}/users/login`,
            //   data: this.credentials
            // })
            // .then(res => {
            //   localStorage.setItem('jwt', res.data.accessToken)
            //   this.userUpdate(res.data.user)
            //   this.initSession(res.data.user)
            //   this.$router.replace({name:'Main'})
            // })
            // .catch(err => {
            //   console.log(err)
            //   this.credentials.id = null
            //   this.credentials.password = null
            // })

            this.login(this.credentials)
              .then(response => {
                if (response.status == 200) {
                  this.initSession(response.data.user);
                  this.$router.replace({ name : 'Main' });
                }
              })
              .catch(error => {
                console.log(error.response);
              })

            // console.log('fcm getToken ', token);
      //     } else {
      //       // Show permission request UI
      //       console.log('No registration token available. Request permission to generate one.');
      //     }
      //   }
      // ).catch((err) => {
      //   console.log('An error occurred while retrieving token. ', err);
      //   // ...
      // });
      
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
      api.get(`/oauth/kakao?code=${authCode}`)
        .then((response) => {
          console.log('oAuth response', response)
          const { join, info } = response.data;

          join ? 
            this.getFcmToken()
              .then(token => {
                if (token) {
                  this.loginKakao({
                    id: info.id,
                    fcmToken: token
                  }).then(response => {
                      if (response.status == 200) {
                        this.initSession(response.data.user);
                        this.$router.replace({ name : 'Main' });
                        this.isLoading = false;
                      }
                    }
                  ).catch(error => {
                      console.log(error.response);
                    }
                  )
                  // axios.post(`${process.env.VUE_APP_API_URL}/users/social/kakao/login`, {
                  //   id: info.id,
                  //   fcmToken: token
                  // }).then(res => {
                  //   localStorage.setItem('jwt', res.data.accessToken)
                  //   this.userUpdate(res.data.user)
                  //   this.initSession(res.data.user)
                  //   this.$router.replace({ name: 'Main' })
                  // }).catch(err => {
                  //   console.log(err)
                  //   this.credentials.id = null
                  //   this.credentials.password = null
                  // })
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
        ).catch(({ response }) => {
          const {
            data: {
              code
            }
          } = response;

          this.isLoading = false;
          code === 'OAUTH_EMAIL_DUPLICATE'
            && confirm('이메일로 가입된 계정이 이미 존재합니다. 아이디로 로그인하세요!')
              && this.$router.replace({ name: 'Login' });

          console.log(response)
          // 나머지 에러 처리
          // 오류 발생했다 메시지 띄우고
          // 로그인 페이지로 리다이렉트
          
        })
    }
  }
}
</script>

<style>
  .font-sm {
    font-size: 0.8em;
  }
  .mx-50 {
    margin-left: 7.2rem;
    margin-right: 7.2rem;
  }
  .login-box {
    width: 450px;
    background: #FBF8F8
  }
</style>
