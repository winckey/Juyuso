<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="600px"
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
        {{ alertMessage }}
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
                  <div class="d-flex justify-content-center">
                    <v-avatar>
                      <img
                        :src="myImage"
                        :alt="profileImg"
                      >
                    </v-avatar>
                  </div>
                  
                   <v-file-input
                    v-model="profileImg"
                    accept="image/*"
                    label="프로필 이미지 업로드"
                    @change="uploadImage()"
                    prepend-icon="mdi-camera"
                  ></v-file-input>
                </v-col>

                <v-col>
                  <div class="d-flex flex-column justify-content-end">
                    <v-text-field
                      label="닉네임*"
                      v-model="userInfo.nickname"
                      :rules="rules.nicknameRule"
                      :counter="10"
                      required
                    ></v-text-field>
                    <v-btn 
                    @click="duplicate" 
                    rounded
                    width="100">
                    <span v-if="!nicknameCheck">중복확인</span>
                    <v-icon v-else large color="#1CFD9F">mdi-check</v-icon>
                    </v-btn>
                  </div>
                </v-col>

                  
                <v-col cols="12">
                  <v-text-field
                      label="자기소개"
                      v-model="userInfo.description"
                      :counter="10"
                      :rules="rules.descriptionRule"
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
                    label="휴대전화*"
                    v-model="userInfo.phone"
                    :rules="rules.phoneRule"
                    required
                    @input="phoneChange"
                  ></v-text-field>
                </v-col>

                <v-col cols="12">
                  <v-select
                    v-model="defaultSelected.region_id"
                    :rules="rules.regionRule"
                    :items="regions"
                    label="지역*"
                    item-text="name"
                    item-value="region_id"
                    required
                  ></v-select>
                </v-col>
                

              </v-row>
              <p>*필수항목입니다</p>
            </v-form>
        
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue darken-1"
            text
            @click="[dialog = false, isSuccess=false, isAlert=false, reloadMypage()]"
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
import { phoneFormatter } from '@/common/util'

export default {
  name: 'ProfileEditPopup',
  props: {
    user: Object
  },
  data: function () {
    return {
      alertMessage: null,
      isAlert: false,
      isSuccess: false,
      userInfo: null,
      dialog: false,
      myImage: null,
      profileImg: null,
      nicknameCheck: false,
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
        birthRule: [
          v => !!v || "생년월일을 입력해주세요."
        ],
        regionRule: [
          v => !!v || "지역을 입력해주세요."
        ],
        phoneRule: [
          v => !!v || "휴대전화 번호를 입력해주세요.",
          v => /^010-?([0-9]{4})-?([0-9]{4})$/.test(v) || "13자리의 휴대전화 번호를 (숫자만) 입력해주세요."
        ],
        nicknameRule: [
          v => !!v || "닉네임을 입력해주세요.",
          v => !(v && v.length > 10) || "닉네임은 10자까지 입력 가능합니다.",
          v => !/[~!@#$%^&*()_+|<>?:{}]/.test(v) || "닉네임에는 특수문자를 사용할 수 없습니다.",
        ],
        descriptionRule: [
          v => !(v && v.length > 10) || "자기소개는 10자까지 입력 가능합니다."
        ]
      },
      
    }
  },
  created: function () {
    this.userInfo = this.user
    this.myImage = `${process.env.VUE_APP_IMG_URL}/${this.userInfo.imgUrl}`
  },
  computed: {
    defaultSelected: function () {
      return {region_id: this.userInfo.region.id, name: this.userInfo.region.name }
    },
    
  },
  methods: {
    ...mapActions('accounts', ['userUpdate']),
    phoneChange: function () {
      this.userInfo.phone = phoneFormatter(this.userInfo.phone)
    },

    updateUser: function () {
      const item = {credentials: {
          description: this.userInfo.description,
          email: this.userInfo.email,
          nickname: this.userInfo.nickname,
          phone: this.userInfo.phone,
          regionId: this.defaultSelected.region_id,
      }}

      const validation = this.$refs.update.validate()
      if (validation) {
        axios({
            method: 'PUT',
            url: `${process.env.VUE_APP_API_URL}/users/me`,
            data: item.credentials,
            headers: {Authorization: `Bearer ${localStorage.getItem('jwt')}`}
          })
            .then(res => {
              this.isSuccess=true
              this.isAlert=false
              this.userUpdate(res.data.user)
  
            })
            .catch(() => {            
              this.alertMessage = "입력 형식을 다시 확인해주세요!"
              this.isAlert = true
              setTimeout(() => this.isAlert=false, 3000)
              this.isSuccess = false
            })

      } else {
        this.alertMessage = "입력 형식을 다시 확인해주세요!"
        this.isAlert=true
        this.isSuccess=false
      }
    },
  
    uploadImage: function () {
      const image = new FormData()
      image.append('img', this.profileImg)
      if (this.profileImg.size < 630000) {
          axios({
            method: 'POST',
            url: `${process.env.VUE_APP_API_URL}/users/img`,
            data: image,
            headers: { 'Content-Type': 'multipart/form-data', 'Authorization': `Bearer ${localStorage.getItem('jwt')}`}
          })
            .then(res => {
              this.myImage = `${process.env.VUE_APP_IMG_URL}/${res.data.imgUrl}`
              this.userInfo.imgUrl = `${process.env.VUE_APP_IMG_URL}/${res.data.imgUrl}`
              this.$emit('changeProfileImage', res.data.imgUrl)
            })
      } else {
        this.alertMessage = "이미지 크기가 용량을 초과했습니다!"
        this.isAlert = true
        setTimeout(() => this.isAlert=false, 3000)
      }

    },
    reloadMypage: function () {
      this.$router.go()
    },
    duplicate: function () {
      axios({
        method: 'GET',
        url: `${process.env.VUE_APP_API_URL}/users/nickname/${this.userInfo.nickname}`,
      })
      .then(res => {
        if (!res.data.duplicate) {
          this.nicknameCheck = true

        } else {
          this.alertMessage = "이미 존재하는 닉네임입니다!"
          this.isAlert = true
          setTimeout(() => this.isAlert=false, 2000)
        }
      })
    }
  }
}
</script>

<style scoped>



</style>
