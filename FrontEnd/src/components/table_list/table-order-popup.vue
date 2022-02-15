<template>
  <div>
    <v-dialog
      transition="dialog-bottom-transition"
      v-model="dialog"
      width="700"
    >
      <template v-slot:activator="{ on: dialog, attrs }">
        <v-tooltip top :open-on-click="false" :open-on-focus="false">
          <template v-slot:activator="{ on: tooltip }">
            <v-btn
              fab
              dark
              color="#4DB6AC"
              class="float-right" style="margin-right: 1vw" 
              v-bind="attrs"
              v-on="{ ...tooltip, ...dialog }"
            >
              <v-icon dark>
                mdi-plus
              </v-icon>
            </v-btn>
          </template>
          <span>방 개설하기</span>
        </v-tooltip>
      </template>

      <v-card class="p-2">
        <v-card-title class="d-flex justify-content-center">
          <span style="font-size: 1.5rem">
          테이블 추가
          </span>
        </v-card-title>
        <v-divider></v-divider>
        <v-form ref="tableOrderForm">
          <v-container class="px-5">
            <v-row>
              <v-col cols="2">
                <v-subheader>방 이름</v-subheader>
              </v-col>
              <v-col cols="8">
                <v-text-field
                  label="방 이름"
                  :rules="rules.nameRule"
                  v-model="roomInfo.meetingName"
                  outlined
                  rounded
                  dense
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2">
                <v-subheader>비밀방</v-subheader>
              </v-col>
              <v-col cols="2">
                <v-switch
                  v-model="isSecret"
                  inset
                  color= "#4DB6AC"
                  :label="`${isSecret ? 'On' : 'Off'}`"
                ></v-switch>
              </v-col>
            </v-row>
            <v-row v-show="isSecret">
              <v-col offset="2" cols="4">
                <v-text-field
                  label="비밀번호"
                  :rules="rules.passwordRule"
                  v-model="roomInfo.meetingPassword"
                  outlined
                  rounded
                  dense
                ></v-text-field>
              </v-col>
              <v-col cols="4">
                <v-text-field
                  label="비밀번호 확인"
                  :rules="rules.passwordConfirmationRule"
                  v-model="passwordConfirmation"
                  outlined
                  rounded
                  dense
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2">
                <v-subheader>해시태그</v-subheader>
              </v-col>
              <v-col cols="8">
                <v-text-field
                  label="해시태그"
                  :rules="rules.hastagRule"
                  v-model="hashtagInput"
                  outlined
                  rounded
                  dense
                  @keyup.enter="addHastag"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col offset="2" cols="9">
                <v-chip
                  v-for="hashtag in roomInfo.hashTag"
                  :key="hashtag"
                  class="ma-2"
                  close
                  color="#4DB6AC"
                  dark
                  @click:close="deleteHashtag(hashtag)"
                >
                  {{ hashtag }}
                </v-chip>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2">
                <v-subheader>테마선택</v-subheader>
              </v-col>
            </v-row>
            <v-item-group>
              <v-row>
                <v-col
                  v-for="n in 4"
                  :key="n"
                  md="3"
                >
                  <v-item v-slot="{ toggle }">
                    <v-card
                      class="d-flex justify-content-center align-center theme-card"
                      dark
                      height="100"
                      width="200"
                      @click="[toggle(), selectTheme(n)]"
                      :style="[backgroundToggle(n)]"
                    >
                      <div>
                        <v-icon size=100 color="#1CFD9F" v-if="n==roomInfo.img">
                          mdi-check
                        </v-icon>
                      </div>
                    </v-card>
                  </v-item>
                </v-col>
              </v-row>
            </v-item-group>
          </v-container>
        </v-form>
        <v-card-actions class="d-flex justify-content-center m-2">
          <v-btn
            color="#4DB6AC"
            dark
            rounded
            @click="[dialog=false, createRoom()]"
            style="font-size: 1.2rem"
          >
            테이블 예약하기
          </v-btn>
        </v-card-actions>    
      </v-card>
      
    </v-dialog>
    <TablePreview
    :roomInfo="roomInfo"
    ref="tablepreview"/>
  </div>
  
</template>

<script>
import axios from 'axios'
import { mapState, mapActions } from 'vuex'
import TablePreview from '@/components/table_list/table-preview.vue'

export default {
  name: 'TableOrderPopup',
  components: {
    TablePreview
  },
  props: [],
  data: function () {
    return {
      dialog: false,
      roomInfo: {
        active: true,
        common: true,
        meetingName: '',
        meetingPassword: '',
        hashTag: [],
        img: 1,
      },
      isSecret: false,
      passwordConfirmation: '',
      hashtagInput: '',
      rules: {
        nameRule: [
          v => !!v || "방이름을 입력해주세요."
        ],
        passwordRule: [
          v => !!(this.isSecret && v) || "비밀방 비밀번호를 입력해주세요."
        ],
        passwordConfirmationRule: [
          v => !!(this.isSecret && v == this.roomInfo.meetingPassword) || "비밀번호가 일치하지 않습니다."
        ],
        hastagRule: [
          v => !/.+\s.+/.test(v) || '해시태그는 공백을 포함할 수 없습니다.',
          v => !(v && v.length > 10) || '해시태그는 10자이상 입력할 수 없습니다.',
          () => !(this.roomInfo.hashTag.length > 10) || '해시태그는 10개이상 입력할 수 없습니다.',
          v => this.roomInfo.hashTag.indexOf(v) == -1 || '중복된 해시태그는 입력할 수 없습니다.'
        ]
      },
      themeItems:[
        {
          themeImg:'@/assets/beach.jpg',
          imgName:'beach'
        },
        {
          themeImg:'@/assets/camping.jpg',
          imgName:'camping'
        },
        {
          themeImg:'@/assets/bar.jpg',
          imgName:'bar'
        },
        {
          themeImg:'@/assets/boat.jpg',
          imgName:'boat'
        }
      ],
    } 
  },
  computed: {
    ...mapState('accounts', [
      'user'
    ]),
    ...mapState('openviduStore', [
      'OV',
      'session',
      'mainStreamManager',
      'publisher',
      'subscribers',
    ])
  },
  methods: {
    ...mapActions('openviduStore', [
      'joinSession',
      'leaveSession',
    ]),
    ...mapActions('table',[
      'setTheme',
    ]),
    addHastag: function () {
      console.log(this.$refs.tableOrderForm)

      if (this.$refs.tableOrderForm.validate() && this.hashtagInput != '') {
        this.roomInfo.hashTag.push(this.hashtagInput)
        this.hashtagInput = ''
      }
    },
    deleteHashtag: function (hashtag) {
      this.roomInfo.hashTag = this.roomInfo.hashTag.filter(v => v != hashtag)
    },
    selectTheme: function (num) {
      this.roomInfo.img = num
      console.log(this.roomInfo.img)
    },
    backgroundToggle(theme) {
      if (this.roomInfo.img == theme) {
        return { backgroundImage: 'linear-gradient( rgba(255, 255, 255, 0.5), rgba(255, 255, 255, 0.5) ), '+ 'url(' + require(`@/assets/theme/${theme}.jpg`) + ')' }
      }
      else {
        return { backgroundImage: 'url(' + require(`@/assets/theme/${theme}.jpg`) + ')' }
      }
    },
    createRoom: function () {
      if (this.$refs.tableOrderForm.validate()) {
        this.roomInfo.common = !this.isSecret
        const token = localStorage.getItem('jwt')
        axios({
          method: 'POST',
          headers: { Authorization: `Bearer ${token}`},
          url: `${process.env.VUE_APP_API_URL}/meeting/create`,
          data: this.roomInfo,
        })
        .then( res => {
          console.log(res)
          this.$refs.tablepreview.dialog = true
        })
        .catch( err => {
          console.log(err)
        })
      }
    }
  },
}
</script>

<style scoped>
  .theme-card {
    background-size: cover;
  }
</style>
