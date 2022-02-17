<template>
  <div class="d-flex justify-content-center my-auto">
    <div class="login-box rounded-lg">
      <div class="mx-auto p-5">
        <v-form ref="loginForm" v-model="valid" lazy-validation @submit.prevent="onLogin">
          <span>
            <h2 class="mt-8 mb-8 text-center">🍻 적셔 🍻</h2>
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
        <div>
          <div class="d-flex flex-column my-3">
            <v-btn class="white--text my-2" @click="onLogin" color="#4DB6AC" rounded>로그인</v-btn> 
            <v-btn class="white--text" @click="$router.push({ name: 'Signup' })" color="indigo lighten-2" rounded>회원가입</v-btn>
          </div>

          <div style="text-align:center">
            <span>or</span>
          </div>
          
          <div class="my-3">
            <v-btn width="100%" @click="oAuth" color="#f9e000" rounded>
              <img src="@/assets/kakao_symbol.png" width="5%">
              &nbsp;카카오로 시작하기
            </v-btn>
          </div>
          <div class="text-center">
            <router-link class="text-decoration-none font-sm" to="#">비밀번호를 잊으셨나요?</router-link>
          </div>
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
import api from '@/common/api'
import { mapActions } from 'vuex'

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
    let authCode = this.$route.query.code;
    authCode && this.kakaoAuth(authCode);
    authCode && (this.isLoading = true);
  },
  methods: {
    ...mapActions(accounts, ['login', 'loginKakao', 'userUpdate']),
    ...mapActions('openviduStore', ['initSession']),
    onLogin() {
      if (!this.$refs.loginForm.validate()) {
        this.$toast.open({
          position: 'bottom',
          message: '아이디, 비밀번호를 입력하세요.',
          type: 'error',
          duration: 2000,
        });
        return;
      }

      this.login(this.credentials)
        .then(response => {
          const {
            status,
            data: {
              user
            }
          } = response;

          if (status == 200) {
            this.initSession(user);
            this.$router.replace({ name : 'Main' });
          }
        })
        .catch(({ response })=> {
          const {
            data: {
              code
            }
          } = response;

          if (code === 'USER_NOT_FOUND') {
            this.$toast.open({
              position: 'bottom',
              message: '아이디 또는 비밀번호를 다시 확인하세요.',
              type: 'error',
              duration: 2000,
            });
          } else {
            this.$toast.open({
              position: 'bottom',
              message: '서버에 문제가 발생하였습니다. 다시 시도하세요.',
              type: 'error',
              duration: 2000,
            });
          }
        })
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
      // api.get(`http://localhost:8080/api/oauth/kakao?code=${authCode}`)
      api.get(`/oauth/kakao?code=${authCode}`)
        .then((response) => {
          const { join, info } = response.data;

          join ? 
            this.loginKakao({
              id: info.id,
            }).then(response => {
              const {
                status,
                data: {
                  user
                }
              } = response;

              if (status == 200) {
                this.initSession(user);
                this.$router.replace({ name : 'Main' });
                this.isLoading = false;
              }
            }).catch(error => {
                console.log(error.response);
            })
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
