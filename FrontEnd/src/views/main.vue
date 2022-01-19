<template>
  <div>
    <v-btn 
      color="primary"
      @click="goToLogin"
    >로그인</v-btn>
    <v-btn 
      color="primary"
      @click="goToSignup"
    >회원가입</v-btn>
  </div>
</template>

<script>
export default {
  name: 'Main',
  methods: {
    goToLogin: function () {
      this.$router.push({ name: 'Login' })
    },
    goToSignup: function () {
      this.$router.push({ name: 'Signup' })
    }
  }
}
</script>

<style>

</style>
