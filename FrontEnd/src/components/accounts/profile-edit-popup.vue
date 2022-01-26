<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="650px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          color="#4DB6AC"
          dark
          v-bind="attrs"
          v-on="on"
          rounded
        >
          프로필 수정
        </v-btn>
      </template>

      <v-alert
      :value="isAlert"
        dense
        type="error"
      >
        정보를 다시 확인해주세요!
      </v-alert>


      <v-alert
      :value="isSuccess"
        dense
        type="success"
      >
        정보가 저장되었습니다
      </v-alert>

      <v-card class="p-2" >
        <v-card-title class="justify-content-center">
          <span class="h2">프로필 수정</span>
        </v-card-title>
        <v-spacer></v-spacer>

        <v-card-text>
          <v-container class="rounded-lg">

            <v-form class="form-box" ref="update">

              <v-row>
                
                <v-col>
                  <div>
                    <v-img src="@/assets/chat.png" width="30%"></v-img>
                  </div>
                  <div>
                    <a href="/">이미지 변경하기</a>
                  </div>
                </v-col>
                <v-col>
                    <v-text-field
                      label="닉네임*"
                      v-model="userInfo.nickname"
                      :rules="rules.nicknameRule"
                      :counter="10"
                      required
                    ></v-text-field>

                    <v-text-field
                      label="자기소개"
                      v-model="userInfo.description"
                      :counter="10"
                    ></v-text-field>
                </v-col>

                  

                <v-col cols="12">
                  <v-text-field
                    type="email"
                    label="이메일*"
                    v-model="userInfo.email"
                    :rules="rules.emailRule"
                    required
                  ></v-text-field>
                </v-col>

                <v-col cols="12">
                  <v-text-field
                    label="휴대전화* ex) 010-1234-1234"
                    v-model="userInfo.phone"
                    :rules="rules.phoneRule"
                    required
                  ></v-text-field>
                </v-col>


                <v-col cols="6">
                  <v-text-field
                    v-model="password"
                    :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="passwordShow ? 'text' : 'password'"
                    label="비밀번호*"
                    :rules="rules.passwordRule"
                    hint="영어, 숫자, 특수문자를 모두 포함해야합니다 (9-16자)"
                    required
                    @click:append="passwordShow = !passwordShow"
                  ></v-text-field>
                  <v-text-field
                    :append-icon="passwordConfirmationShow ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="passwordConfirmationShow ? 'text' : 'password'"
                    label="비밀번호 확인*"
                    v-model="passwordConfirmation"
                    @click:append="passwordConfirmationShow = !passwordConfirmationShow"
                    :rules="rules.passwordConfirmationRule"
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="6">
                  <v-select
                    v-model="userInfo.regionId"
                    :rules="rules.regionRule"
                    :items="regions"
                    label="지역*"
                    item-text="name"
                    item-value="region_id"
                    required
                  ></v-select>
                </v-col>
                

              </v-row>
              <h5>*필수항목입니다</h5>
            </v-form>
        
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue darken-1"
            text
            @click="[dialog = false, isSuccess=false, isAlert=false]"
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

const accounts = 'accounts'

export default {
  name: 'ProfileEditPopup',
  props: {
    user: Object
  },
  data: function () {
    return {
      isAlert: false,
      isSuccess: false,
      userInfo: null,
      dialog: false,
      passwordShow: false,
      passwordConfirmationShow: false,
      passwordConfirmation: '',
      password: '',
      regions: [
          {region_id: 1, name: '서울'},
          {region_id: 2, name: '부산'},
          {region_id: 3, name: '대구'},
          {region_id: 4, name: '인천'},
          {region_id: 5, name: '광주'},
          {region_id: 6, name: '대전'},
          {region_id: 7, name: '울산'},
          {region_id: 8, name: '세종'},
        ],
      rules: {
        emailRule: [
          v => !!v || '이메일을 입력해주세요.',
          v => /.+@.+/.test(v) || '이메일 형식에 맞지않습니다.',
        ],
        passwordRule: [
          v => !!v || "비밀번호를 입력해주세요.",
          v => !(v && v.length < 9 && v.length > 15) || "비밀번호는 9자에서 16자 사이로 입력가능합니다.",
          v => /^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/.test(v) || "비밀번호는 영어, 숫자, 특수문자를 모두 포함해야 합니다."
        ],
        passwordConfirmationRule: [
          v => !!v || "비밀번호 확인을 입력해주세요.",
          v => v === this.password || "비밀번호가 일치하지 않습니다."
        ],
        birthRule: [
          v => !!v || "생년월일을 입력해주세요."
        ],
        regionRule: [
          v => !!v || "지역을 입력해주세요."
        ],
        phoneRule: [
          v => !!v || "휴대전화 번호를 입력해주세요."
        ],
        nicknameRule: [
          v => !!v || "닉네임을 입력해주세요.",
          v => !(v && v.length > 10) || "닉네임은 10자까지 입력 가능합니다.",
          v => !/[~!@#$%^&*()_+|<>?:{}]/.test(v) || "닉네임에는 특수문자를 사용할 수 없습니다.",
        ],
      }
    }
  },
  created: function () {
    this.userInfo = this.user
  },
  methods: {
    ...mapActions(accounts, ['userUpdate']),
    setToken: function (){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },

// 유효성 검사가 안되고 바로 정보가 update된다는 점~~~~~~~~~

    updateUser: function () {
      console.log('update함수 들어옴')

      const item = {credentials: {
                  nickname: this.userInfo.nickname,
                  description: this.userInfo.description,
                  email: this.userInfo.email,
                  regionId: this.userInfo.regionId,
                  phone: this.userInfo.phone,
                  password: this.password
      }}

     
      console.log('업데이트 요청 직전')

      const validation = this.$refs.update.validate()
      if (validation) {
        axios({
            method: 'PUT',
            url: `${process.env.VUE_APP_API_URL}/user/info`,
            data: item.credentials,
            headers: this.setToken()
          })
            .then(res => {
              console.log('axios들어옴 하하하')
              this.isSuccess=true
              this.isAlert=false
              console.log(res.data.user)
              this.userUpdate(res.data.user)
  
            })
            .catch(err => {
              this.isAlert = true
              this.isSuccess = false
              console.log('axios 틀렸잖앙')
              console.log(err)
            })

      } else {
        this.isAlert=true
        this.isSuccess=false
    }
  }
}
}
</script>

<style scoped>



</style>
