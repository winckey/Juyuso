<template>
  <div class="d-flex justify-content-center my-auto">
    <div class="signup-box rounded-lg">
      <div style="width: 70;" class="mx-auto p-4">
        <v-container>
          <v-form ref="signupForm">
            <v-row>
              <v-col cols="9">
                <v-text-field
                  label="닉네임"
                  v-model="credentials.nickname"
                  :rules="rules.nicknameRule"
                  @input="isNameCheck = false"
                ></v-text-field>
              </v-col>
              <v-col cols="3">
                <v-btn
                  :loading="nameLoading"
                  :disabled="nameLoading"
                  @click="nameCheck"
                  rounded>
                  <span v-if="!isNameCheck">중복확인</span>
                  <v-icon v-else large color="#1CFD9F">mdi-check</v-icon>
                </v-btn> 
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-radio-group
                  label="성별"
                  :rules="rules.genderRule"
                  v-model="credentials.gender"
                  row
                  disabled
                >
                  <v-radio
                    label="남"
                    value="M"
                  ></v-radio>
                  <v-radio
                    label="여"
                    value="F"
                  ></v-radio>
                </v-radio-group>
              </v-col>
            </v-row>
            <v-text-field
              type="email"
              label="이메일"
              v-model="credentials.email"
              :rules="rules.emailRule"
              disabled
            ></v-text-field>
            <!-- DATE -->
            <v-row>
              <v-col>
                <v-menu
                  v-model="datePicker"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      :rules="rules.birthRule"
                      v-model="credentials.birthDate"
                      label="생년월일"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="credentials.birthDate"
                    @input="datePicker = false"
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-col>
                <v-select
                  :rules="rules.regionRule"
                  v-model="credentials.regionId"
                  :items="regions"
                  item-text="name"
                  item-value="region_id"
                  label="지역"
                ></v-select>
              </v-col>
            </v-row>
            <v-text-field
            :rules="rules.phoneRule"
              label="휴대전화"
              v-model="credentials.phone"
            ></v-text-field>
          </v-form>
          <span class="d-flex justify-content-end">
            <v-btn 
              @click="signup"
              color="indigo lighten-2"
              dark
              rounded>
              회원가입
            </v-btn>
          </span>
        </v-container>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'SignupKakao',
  props: ['oAuth', 'info'],
  data: function () {
    return {
      datePicker: false,
      idLoading: false,
      nameLoading: false,
      isNameCheck: false,
      credentials: {
        id: '',
        email: '',
        birthDate: '',
        gender: '',
        nickname: '',
        regionId: '',
        phone: '',
        provider: 'kakao',
      },
      passwordConfirmation: '',
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
  created() {
    !this.oAuth ? this.$router.push({ name : 'Login' }) : null;
    this.credentials.id = this.info.id;
    this.credentials.email = this.info.email;
    this.credentials.nickname = this.info.nickname;
    this.credentials.gender = this.info.gender;
  },
  methods: {
    nameCheck: function () {
      if (!this.credentials.nickname.trim()) {return}
      this.nameLoading = true
      axios({
        method: 'get',
        url: `${process.env.VUE_APP_API_URL}/users/nickname/${this.credentials.nickname}`
      })
      .then( res => {
        if (res.data.duplicate) {
          this.$toast.error('이미 존재하는 닉네임입니다', {
            position: "top-center",
            timeout: 2500,
            closeOnClick: true,
            draggable: true,
            draggablePercent: 0.6,
            hideProgressBar: true,
            icon: true,
            rtl: false
          });
        }
        this.isNameCheck = !res.data.duplicate
        this.nameLoading = false
      })
    },
    signup: function () {
      // const validateCheck = this.$refs.signupForm.validate()
      // if (validateCheck && !this.isNameCheck) {
      //   this.$toast.error('닉네임 중복체크를 확인해주세요', {
      //     position: "top-center",
      //     timeout: 2500,
      //     closeOnClick: true,
      //     draggable: true,
      //     draggablePercent: 0.6,
      //     hideProgressBar: true,
      //     icon: true,
      //     rtl: false
      //   });
      //   return
      // }
      // if (validateCheck) {
        // console.log(`${process.env.VUE_APP_API_URL}/user`)
        axios({
          method: 'POST',
          url: `http://localhost:8080/api/users`,
          // url: `${process.env.VUE_APP_API_URL}/users`,
          data: this.credentials
        })
        .then(() => {
          console.log(this.credentials)
          this.$router.push({ name: 'Main' })
        })
        .catch( err => {
          console.log(err)
        })
      // }
    }
  }
  
}
</script>

<style scoped>
  .signup-box {
    width: 540px;
    background: #FBF8F8
  }

</style>
