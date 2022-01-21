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
                    v-model="roomInfo.name"
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
                    v-model="roomInfo.isSecret"
                    inset
                    color="success"
                    :label="`${roomInfo.isSecret ? 'On' : 'Off'}`"
                  ></v-switch>
                </v-col>
              </v-row>
              <v-row v-if="roomInfo.isSecret">
                <v-col offset="2" cols="4">
                  <v-text-field
                    label="비밀번호"
                    v-model="roomInfo.password"
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
                    v-for="hashtag in roomInfo.hashtags"
                    :key="hashtag"
                    class="ma-2"
                    close
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
            >
              테이블 예약하기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
</template>

<script>
export default {
  name: 'TableOrderPopup',
  data: function () {
    return {
      dialog: false,
      roomInfo: {
        name: null,
        isSecret: false,
        password: null,
        hashtags: [],
        theme: 1,
      },
      passwordConfirmation: null,
      hashtagInput: '',
      rules: {
        nameRule: [
          v => !!v || "방이름을 입력해주세요."
        ],
        passwordRule: [
          v => !(this.roomInfo.isSecret && v) || "비밀방 비밀번호를 입력해주세요."
        ],
        passwordConfirmationRule: [
          v => !(this.roomInfo.isSecret && v == this.passwordConfirmation) || "비밀번호가 일치하지 않습니다."
        ],
        hastagRule: [
          v => !/.+\s.+/.test(v) || '해시태그는 공백을 포함할 수 없습니다.',
          v => !(v && v.length > 10) || '해시태그는 10자이상 입력할 수 없습니다.',
          () => !(this.roomInfo.hashtags.length > 10) || '해시태그는 10개이상 입력할 수 없습니다.',
          v => this.roomInfo.hashtags.indexOf(v) == -1 || '중복된 해시태그는 입력할 수 없습니다.'
        ]
      }
    } 
  },
  methods: {
    addHastag: function () {
      console.log(this.$refs.tableOrderForm.validate())
      console.log(this.hashtagInput)
      if (this.$refs.tableOrderForm.validate() && this.hashtagInput != '') {
        this.roomInfo.hashtags.push(this.hashtagInput)
        this.hashtagInput = ''
      }
    },
    deleteHashtag: function (hashtag) {
      this.roomInfo.hashtags = this.roomInfo.hashtags.filter(v => v != hashtag)
    },
    setTheme: function (num) {
      this.roomInfo.theme = num
      // console.log(num)
    }
  }

}
</script>

<style>

</style>
