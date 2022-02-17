<template>
  <div class="d-flex justify-content-center my-auto">
    <div class="signup-box rounded-lg mx-auto p-4">
      <v-container class="py-0">
        <v-form ref="signupForm">
          <v-row class="my-0">
            <v-col class="pb-0">
              <v-radio-group
                class="pa-0 ma-0"
                label="성별"
                :rules="rules.genderRule"
                v-model="credentials.gender"
                :disabled="info.gender!=null"
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
          <v-row class="my-0">
            <v-col class="pt-0">
              <v-text-field
                label="닉네임"
                v-model="credentials.nickname"
                :error-messages="errors.nickname"
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row class="my-0">
          <v-text-field
            type="email"
            label="이메일"
            v-model="credentials.email"
            :rules="rules.emailRule"
            :disabled="info.email!=null"
          ></v-text-field>
          </v-row>
          <!-- DATE -->
          <v-row class="my-0">
            <v-col cols="7">
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
            <v-col cols="5">
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
          <v-row class="my-0">
            <v-text-field
              :rules="rules.phoneRule"
              label="휴대전화"
              v-model="credentials.phone"
              hint="숫자만 입력하세요 (11자리)"
              @input="onPhoneChange"
            ></v-text-field>
          </v-row>
        </v-form>
        <span class="my-2 d-flex justify-content-end">
          <v-btn to="/login" class="mr-3" color="grey" dark rounded>
            취소
          </v-btn>
          <v-btn @click="onSignup" color="indigo lighten-2" dark rounded>
            회원가입
          </v-btn>
        </span>
      </v-container>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import api from '@/common/api'
import { phoneFormatter } from '@/common/util'

export default {
  name: 'SignupKakao',
  props: ['oAuth', 'info'],
  data: function () {
    return {
      errors: {
        nickname: [],
      },
      isValid: {
        nickname: false,
      },
      datePicker: false,
      credentials: {
        id: '',
        email: '',
        birthDate: '',
        gender: '',
        nickname: '',
        regionId: '',
        phone: '',
        provider: 'kakao'
      },
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
          v => /^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/.test(v) || '이메일 형식에 맞지 않습니다.',
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
          v => !!v || "휴대전화 번호를 입력해주세요.",
          v => /^010-?([0-9]{4})-?([0-9]{4})$/.test(v) || "13자리의 휴대전화 번호를 (숫자만) 입력하세요."
        ],
      }
    }
  },
  created() {
    !this.oAuth ? this.$router.push({ name : 'Login' }) : null;
    if (!this.oAuth) return;
    this.credentials.id = this.info.id;
    this.credentials.email = this.info.email;
    this.credentials.nickname = this.info.nickname;
    this.credentials.gender = this.info.gender;
  },
  watch: {
    'credentials.nickname'(v) {
      if (v == null || v == '' || !v.trim()) {
        this.errors.nickname = ["닉네임을 입력해주세요."];
        this.isValid.nickname = false;
      } else if (v && v.length > 10) {
        this.errors.nickname = ["닉네임은 10자까지 입력 가능합니다."];
        this.isValid.nickname = false;
      } else if (/[~!@#$%^&*()_+|<>?:{}]/.test(v)) {
        this.errors.nickname = ["닉네임에는 특수문자를 사용할 수 없습니다."];
        this.isValid.nickname = false;
      } else {
        api.get(`/users/nickname/${v}`)
          .then(({ data }) => {
            this.isValid.nickname = !data.duplicate;
            this.errors.nickname = data.duplicate ? ['이미 사용 중인 닉네임입니다.'] : []
          }
        )
      }
    },
  },
  methods: {
    ...mapActions('accounts', ['loginKakao', 'signup']),
    ...mapActions('openviduStore', ['initSession']),
    makeToast(message) {
      this.$toast.open({
        position: 'bottom',
        message,
        type: 'error',
        duration: 2000,
      });
    },
    onPhoneChange() {
      this.credentials.phone = phoneFormatter(this.credentials.phone)
    },
    onSignup() {
      const validateCheck = this.$refs.signupForm.validate()
      if (!this.isValid.nickname) {
        this.makeToast('닉네임을 다시 입력하세요.');
      } else if (!validateCheck) {
        this.makeToast('가입 양식을 다시 한 번 확인해주세요.');
      } else {
        this.signup(this.credentials)
          .then(() => {
            this.$toast.open({
              position: 'bottom',
              message: '가입이 완료되었습니다!',
              type: 'success',
              duration: 1500,
            });
            /* 가입 완료 후, 바로 로그인 */
            this.loginKakao({
              id: this.credentials.id,
            }).then(response => {
              const {
                status,
                data: {
                  user
                }
              } = response;

              if (status == 200) {
                this.initSession(user);
                this.$router.replace({ name : 'Main' });
              }
            })
          })
      }
    },
  }
}
</script>

<style scoped>
  .signup-box {
    width: 400px;
    background: #FBF8F8
  }
</style>