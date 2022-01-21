<template>
    <div class="d-flex justify-content-center my-auto">
      <div>

      </div>
      <div class="signup-box rounded-lg">
        <div style="width: 70;" class="mx-auto p-4">
          <v-container>
            <v-form ref="signupForm">
            <v-text-field
              label="닉네임"
              v-model="credentials.nickname"
              :rules="rules.nicknameRule"
            ></v-text-field>

            <v-text-field
              label="아이디"
              v-model="credentials.id"
              :rules="rules.idRule"
            ></v-text-field>
            <v-row>
              <v-col cols="8">
            <v-text-field
              :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
              :type="passwordShow ? 'text' : 'password'"
              label="비밀번호"
              v-model="credentials.password"
              :rules="rules.passwordRule"
              hint="영어, 숫자, 특수문자를 모두 포함해야합니다 (9-16자)"
              @click:append="passwordShow = !passwordShow"
            ></v-text-field>
            <v-text-field
              :append-icon="passwordConfirmationShow ? 'mdi-eye' : 'mdi-eye-off'"
              :type="passwordConfirmationShow ? 'text' : 'password'"
              label="비밀번호확인"
              v-model="passwordConfirmation"
              :rules="rules.passwordConfirmationRule"
              @click:append="passwordConfirmationShow = !passwordConfirmationShow"
            ></v-text-field>
              </v-col>
              <v-col>
            <v-radio-group
              label="성별"
              :rules="rules.genderRule"
              v-model="credentials.gender"
              row
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
              SIGNUP
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
  name: 'Signup',
  data: function () {
    return {
      datePicker: false,
      passwordShow: false,
      passwordConfirmationShow: false,
      credentials: {
        id: 'ayoung092',
        email: 'ayoung0924@naver.com',
        password: 'dkduddl8*',
        birthDate: '1997-10-25',
        gender: 'M',
        nickname: '성아영',
        regionId: '2',
        phone: '01054732511',
      },
      passwordConfirmation: 'dkduddl8*',
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
        idRule: [
          v => !!v || "아이디를 입력해주세요.",
          v => /^[a-zA-Z0-9]*$/.test(v) || "아이디는 영문+숫자만 입력 가능합니다.",
          v => !(v && v.length > 15) || "아이디는 15자까지 입력 가능합니다."
        ],
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
          v => v === this.credentials.password || "비밀번호가 일치하지 않습니다."
        ],
        birthRule: [
          v => !!v || "생년월일을 입력해주세요."
        ],
        genderRule: [
          v => !!v || "성별을 입력해주세요."
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
  methods: {
    signup: function () {
      const validateCheck = this.$refs.signupForm.validate()
      if (validateCheck) {
        // console.log(`${process.env.VUE_APP_API_URL}/user`)
        axios({
          method: 'POST',
          url: `${process.env.VUE_APP_API_URL}/user`,
          data: this.credentials
        })
        .then(() => {
          console.log(this.credentials)
          this.$router.push({ name: 'Main' })
        })
        .catch( err => {
          console.log(err)
        })
      }
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
