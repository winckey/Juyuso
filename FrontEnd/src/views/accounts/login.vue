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
              <v-btn @click="goSignup" color="indigo lighten-2"  rounded>회원가입</v-btn>
            </span>

            
          </v-form>

          <span class="d-flex justify-content-center">
            <v-btn plain @click="goPasswordFind">비밀번호 찾기</v-btn>    
          </span>


      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import {mapActions} from 'vuex'

const accounts = 'accounts' 

export default {
  name: 'Login',
  data: function () {
    return {
      valid: true,
      passwordShow: false,
      credentials: {
        id: null,
        password: null
      },
      idRules: [
        v => !!v || "아이디를 입력해주세요.",
      ],
      passwordRules: [
        v => !!v || "비밀번호를 입력해주세요.",
      ]
    }
  },
  methods: {
    ...mapActions(accounts, ['userUpdate']),
    ...mapActions('openviduStore', ['initSession']),
    login: function () {
      axios({
        method: 'post',
        url: `${process.env.VUE_APP_API_URL}/users/login`,
        data: this.credentials
      })
        .then(res => {
          console.log(res.data.user.age)
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
    },
    goPasswordFind: function () {
      this.$router.push({name:'PasswordFind'})
    },
    goSignup: function () {
      this.$router.push({name: 'Signup'})
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
