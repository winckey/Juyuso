<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      max-width="450px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          color="pink"
          dark
          v-bind="attrs"
          v-on="on"
          rounded
        >
          비밀번호 변경
        </v-btn>
      </template>

      <v-alert
      :value="isSuccess"
        dense
        type="success"
      >
        변경되었습니다
      </v-alert>

      <v-alert
      :value="isAlert"
        dense
        type="error"
      >
        비밀번호를 다시 확인해주세요!
      </v-alert>


      <v-card class="p-2" >
        <v-card-title class="justify-content-center">
          <span class="h2">비밀번호 변경</span>
        </v-card-title>
        <v-spacer></v-spacer>

        <v-card-text>
          <v-container class="rounded-lg">
              <v-form>
                
                <v-row class="d-flex justify-content-center">

                  <v-row v-if="!isValid">
                      <v-col cols="8">
                            <v-text-field
                            label="현재 비밀번호"
                            v-model="nowPassword"
                            :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
                            :type="passwordShow ? 'text' : 'password'"
                            :rules="rules.passwordRule"
                            hint="영어, 숫자, 특수문자를 모두 포함해야합니다 (9-16자)"
                            @click:append="passwordShow = !passwordShow"
                            >
                            </v-text-field>
                      </v-col>
                      <v-col cols="4" class="d-flex align-items-center justify-content-center">
                          <div>
                              <v-btn @click="passwordValid" color="#4DB6AC" dark rounded>확인하기</v-btn>
                          </div>
                      </v-col>
                    </v-row>

                  <div v-if="isValid" ref="password" class="d-flex flex-column align-items-center">
                      <v-col cols="10">
                         <v-text-field
                            v-model="password"
                            :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
                            :type="passwordShow ? 'text' : 'password'"
                            label="새 비밀번호*"
                            :rules="rules.passwordRule"
                            hint="영어, 숫자, 특수문자를 모두 포함해야합니다 (9-16자)"
                            required
                            @click:append="passwordShow = !passwordShow"
                            ></v-text-field>
                      </v-col>
                      <v-col cols="10">
                        <v-text-field
                            :append-icon="passwordConfirmationShow ? 'mdi-eye' : 'mdi-eye-off'"
                            :type="passwordConfirmationShow ? 'text' : 'password'"
                            label="새 비밀번호 확인*"
                            v-model="passwordConfirmation"
                            @click:append="passwordConfirmationShow = !passwordConfirmationShow"
                            :rules="rules.passwordConfirmationRule"
                            required
                            ></v-text-field>
                      </v-col>
                    </div>
                </v-row>
              </v-form>
          </v-container>
        </v-card-text>

        <v-card-actions class="mr-5">
          <v-spacer></v-spacer>
          <v-btn
            color="blue darken-1"
            text
            @click="[dialog = false, isSuccess=false, isAlert=false, isValid=false]"
          >
            닫기
          </v-btn>
          <v-btn
            color="blue darken-1"
            text
            @click="updatePassword"
          >
            저장
          </v-btn>
        </v-card-actions>


        </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import axios from 'axios'
import {mapActions} from 'vuex'


export default {
    name: 'PasswordChange',
    props: {
        user: Object
    },
    data: function () {
        return {
            isValid: false,
            isAlert: false,
            dialog: false,
            isSuccess: false,
            passwordShow: false,
            passwordConfirmationShow: false,
            nowPassword: '',
            passwordConfirmation: '',
            password: '',
            rules: {
                passwordRule: [
                v => !!v || "비밀번호를 입력해주세요.",
                v => !(v && v.length < 9 && v.length > 15) || "비밀번호는 9자에서 16자 사이로 입력가능합니다.",
                v => /^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/.test(v) || "비밀번호는 영어, 숫자, 특수문자를 모두 포함해야 합니다."
                ],
                passwordConfirmationRule: [
                v => !!v || "비밀번호 확인을 입력해주세요.",
                v => v === this.password || "비밀번호가 일치하지 않습니다."
                ]
            }
        }   
    },
    methods: {
        ...mapActions('accounts', ['userUpdate']),
        passwordValid: function () {
            axios({
                method: 'POST',
                url: `${process.env.VUE_APP_API_URL}/users/validate`,
                data: {password: this.nowPassword},
                headers: {Authorization: `Bearer ${localStorage.getItem('jwt')}`}
            })
                .then(res => {
                    this.isValid = res.data.valid
                    this.isAlert = false
                })
                .catch(err => {
                    console.log(err)
                    this.isAlert = true
                })
        },
        updatePassword: function () {
            if (this.password === this.passwordConfirmation) {
                axios({
                        method: 'POST',
                        url: `${process.env.VUE_APP_API_URL}/users/auth`,
                        data: {password: this.password},
                        headers: {Authorization: `Bearer ${localStorage.getItem('jwt')}`}
                    })
                        .then(res => {
                        this.isSuccess=true
                        this.isAlert = false
                        this.nowPassword = ''
                        // console.log(res.data.user)
                        this.userUpdate(res.data.user)
                        })
                        .catch(err => {
                        this.isAlert = true
                        console.log(err)
                        })
            } else {
                this.isSuccess = false
            }

        },
    }
}
</script>

<style scoped>


</style>