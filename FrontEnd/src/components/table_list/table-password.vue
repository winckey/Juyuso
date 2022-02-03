<template>
  <div>
    <v-dialog
        v-model="dialog"
        max-width="290"
      >
      <div>
        <v-alert
          v-if="alert"
          shaped
          dense
          type="error">
          {{ alertMessage }}
        </v-alert>
      </div>
        <v-card>
          <v-card-title>
            비밀번호
          </v-card-title>

          <v-card-text>
            <v-text-field
              :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
              :type="passwordShow ? 'text' : 'password'"
              @click:append="passwordShow = !passwordShow"
              v-model="passwordInput"
              @keyup.enter="confirmPassword">

            </v-text-field>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
</template>

<script>
export default {
  name: 'TablePassword',
  props: {
    password: String,
  },
  data: function () {
    return {
      alertMessage: '',
      dialog: false,
      passwordInput: '',
      alert: false,
      passwordShow: false,
    }
  },
  methods: {
    confirmPassword: function () {
      if (this.password == this.passwordInput) {
        this.dialog = false
        this.$emit('passwordConfirm')
      }
      else {
        this.alert = true
        this.alertMessage = "비밀번호가 틀렸습니다."
        setTimeout(() => {
          this.alert = false
          this.alertMessage = ''
        }, 2000)
      }
    }
  }
}
</script>

<style>

</style>