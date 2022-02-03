<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="500px"
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


      <v-card class="p-2" >
        <v-card-title class="justify-content-center">
          <span class="h2">비밀번호 변경</span>
        </v-card-title>
        <v-spacer></v-spacer>

        <v-card-text>
          <v-container class="rounded-lg">
              <v-form>
                
                <v-row>

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
                        <v-col cols="4">
                            <div class="d-flex align-self-center">
                                <v-btn @click="passwordValid">확인하기</v-btn>
                            </div>
                        </v-col>
                    </v-row>

                  <div v-if="isValid" ref="password">
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
            @click="[dialog = false, isSuccess=false]"
          >
            닫기
          </v-btn>
          <v-btn
            color="blue darken-1"
            text
            @click="updateUser"
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
            console.log(this.nowPassword)
            axios({
                method: 'POST',
                url: `${process.env.VUE_APP_API_URL}/user/pw/check`,
                data: {password: this.nowPassword},
                headers: {Authorization: `Bearer ${localStorage.getItem('jwt')}`}
            })
                .then(res => {
                    console.log(res)
                    this.isValid = true
                })
                .catch(err => {
                    console.log(err)
                })
        },
        updateUser: function () {
            const item = {credentials: {
                        nickname: this.user.nickname,
                        description: this.user.description,
                        email: this.user.email,
                        regionId: this.user.regionId,
                        phone: this.user.phone,
                        password: this.password
            }}

            console.log('업데이트 요청 직전')
            if (this.password === this.passwordConfirmation) {
                axios({
                        method: 'PUT',
                        url: `${process.env.VUE_APP_API_URL}/user/info`,
                        data: item.credentials,
                        headers: {Authorization: `Bearer ${localStorage.getItem('jwt')}`}
                    })
                        .then(res => {
                        console.log('axios들어옴 하하하')
                        this.isSuccess=true
                        console.log(res.data.user)
                        this.userUpdate(res.data.user)
                        })
                        .catch(err => {
                        console.log('axios 틀렸잖앙')
                        this.isSuccess = false
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