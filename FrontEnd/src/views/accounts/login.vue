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
            required></v-text-field>
            
            <span class="d-flex justify-content-center my-3">
              <v-btn @click="login" color="#1CFD9F" rounded>로그인</v-btn>
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
        v => /^[a-zA-Z0-9]*$/.test(v) || "아이디는 영문+숫자만 입력 가능합니다.",
        v => !(v && v.length > 15) || "아이디는 15자까지 입력 가능합니다."
      ],
      passwordRules: [
        v => !!v || "비밀번호를 입력해주세요.",
        v => !(v && v.length < 9 && v.length > 15) || "비밀번호는 9자에서 16자 사이로 입력가능합니다.",
        v => /^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/.test(v) || "비밀번호는 영어, 숫자, 특수문자를 모두 포함해야 합니다."
      ]
    }
  },
  methods: {
    login: function () {
      axios({
        method: 'post',
        url: `${process.env.VUE_APP_API_URL}/user/login`,
        data: this.credentials
      })
        .then(res => {
          console.log(res)
          localStorage.setItem('jwt', res.data.accessToken)
          this.$router.push({name:'Main'})
        })
        .catch(err => {
          console.log(err)
          // alert('아이디와 비밀번호를 정확히 입력해 주세요 ')
          this.credentials.id = null
          this.credentials.password = null
        })
    },
    goPasswordFind: function () {
      this.$router.push({name:'PasswordFind'})
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
