<template>
  <v-card class="my-10 mx-auto p-3 text-center rounded-card" color="FBF8F8">
    
    <v-form ref="form" v-model="valid" lazy-validation @submit.prevent="login">
      <h2>주유소</h2>
      <v-text-field
      v-model="credentials.id"
      :counter="16"
      :rules = "idRules"
      label="아이디"
      required>
      </v-text-field>

      <v-text-field
      v-model="credentials.password"
      :counter="20"
      :rules="passwordRules"
      label="비밀번호"
      required></v-text-field>
      <br>
      <v-btn color="#1CFD9F">로그인</v-btn>
    </v-form>

    <v-btn plain>비밀번호 찾기</v-btn>    
   

  </v-card>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Login',
  data: function () {
    return {
      valid: true,
      credentials: {
        id: null,
        password: null
      },
      idRules: [
        v => !!v || '아이디는 필수입니다',
        v => (v && v.length <= 16) || '아이디는 16자리를 넘길 수 없습니다',
      ],
      password: '',
      passwordRules: [
        v => !!v || '비밀번호는 필수입니다',
        v => (v && v.length <= 20) || '비밀번호는 20자리를 넘길 수 없습니다',
      ]
    }
  },
  methods: {
    login: function () {
      axios({
        method: 'post',
        url: `${process.env.VUE_APP_API_URL}/api/v1/auth/login`,
        data: this.credentials
      })
        .then(res => {
          console.log(res)
          localStorage.setItem('jwt', res.data.accessToken)
          this.$router.push({name:'Main'})
        })
        .catch(err => {
          console.log(err)
          alert('아이디와 비밀번호를 정확히 입력해 주세요 ')
          this.credentials.id = null
          this.credentials.password = null
        })
    }
  }
}
</script>

<style>

</style>
