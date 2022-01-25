<template>
  <v-dialog
    transition="dialog-bottom-transition"
    v-model="dialog"
    width="800"
  >
    <template v-slot:activator="{ on, attrs }">
      <button
        v-on="on"
        v-bind="attrs">
      <img 
        src="@/assets/create_room.png" 
        alt=""
        >
      </button>
    </template>

    <v-card>
      <v-card-title class="grey lighten-2 d-flex justify-content-center">
        <span>
        테이블 추가
        </span>
      </v-card-title>
      <v-form ref="tableOrderForm">
        <v-container>
          <v-row>
            <v-col cols="2">
              <v-subheader>방 이름</v-subheader>
            </v-col>
            <v-col cols="8">
              <v-text-field
                label="방 이름"
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
                color="success"
                :label="`${isSecret ? 'On' : 'Off'}`"
              ></v-switch>
            </v-col>
          </v-row>
          <v-row v-show="isSecret">
            <v-col offset="2" cols="4">
              <v-text-field
                label="비밀번호"
                v-model="roomInfo.meetingPassword"
                outlined
                rounded
                dense
              ></v-text-field>
            </v-col>
            <v-col cols="4">
              <v-text-field
                label="비밀번호 확인"
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
            <v-col offset="2" cols="8">
              <v-chip
                v-for="hashtag in roomInfo.hashTag"
                :key="hashtag"
                class="ma-2"
                close
                color="#FAC372"
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
                <v-item v-slot="{ active, toggle }">
                  <v-card
                    :color="active ? 'primary' : ''"
                    class="d-flex align-center"
                    dark
                    height="100"
                    width="200"
                    @click="[toggle(), setTheme(n)]"
                  >
                    <v-scroll-y-transition>
                      <div
                        v-if="active"
                        class="text-h2 flex-grow-1 text-center"
                      >
                        Active
                      </div>
                    </v-scroll-y-transition>
                  </v-card>
                </v-item>
              </v-col>
            </v-row>
          </v-item-group>
        </v-container>
      </v-form>
      <v-divider></v-divider>
      <v-card-actions class="d-flex justify-content-center">
        <v-btn
          color="#FFBC58"
          rounded
          @click="createRoom"
        >
          테이블 예약하기
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from 'axios'
import { mapState, mapActions } from 'vuex'

export default {
  name: 'TableOrderPopup',
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
          v => !(this.isSecret && v) || "비밀방 비밀번호를 입력해주세요."
        ],
        passwordConfirmationRule: [
          v => !(this.isSecret && v == this.roomInfo.meetingPassword) || "비밀번호가 일치하지 않습니다."
        ],
        hastagRule: [
          v => !/.+\s.+/.test(v) || '해시태그는 공백을 포함할 수 없습니다.',
          v => !(v && v.length > 10) || '해시태그는 10자이상 입력할 수 없습니다.',
          () => !(this.roomInfo.hashTag.length > 10) || '해시태그는 10개이상 입력할 수 없습니다.',
          v => this.roomInfo.hashTag.indexOf(v) == -1 || '중복된 해시태그는 입력할 수 없습니다.'
        ]
      }
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
    addHastag: function () {
      console.log(this.$refs.tableOrderForm.validate())
      if (this.$refs.tableOrderForm.validate() && this.hashtagInput != '') {
        this.roomInfo.hashTag.push(this.hashtagInput)
        this.hashtagInput = ''
      }
    },
    deleteHashtag: function (hashtag) {
      this.roomInfo.hashTag = this.roomInfo.hashTag.filter(v => v != hashtag)
    },
    setTheme: function (num) {
      this.roomInfo.img = num
      // console.log(num)
    },
    createRoom: function () {
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
        const roomInfo = {
          userName: '성아영',
          sessionId: String(res.data.meetingId),
          isCreate: true,
        }
        this.joinSession(roomInfo)
        this.$router.push({ name: 'Table', params: { roomId: res.data.meetingId }})
      })
      .catch( err => {
        console.log(err)
      })
      // const roomInfo = {
      //     sessionId: '1',
      //     isCreate: true,
      //   }
      // this.joinSession(roomInfo)
      // this.$router.push({ name: 'Table', params: { roomId: res.data.meetingId }})
    }
  }

}
</script>

<style>

</style>
