<template>
  <div>
    <v-dialog
        transition="dialog-bottom-transition"
        v-model="dialog"
        width="700"
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
          <v-container>
            <v-row>
              <v-col offset="1" cols="2">
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
              <v-col offset="1" cols="2">
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
              <v-col offset="3" cols="4">
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
                  v-model="roomInfo.passwordConfirmation"
                  outlined
                  rounded
                  dense
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col offset="1" cols="2">
                <v-subheader>해시태그</v-subheader>
              </v-col>
              <v-col cols="8">
                <v-text-field
                  label="해시태그"
                  v-model="roomInfo.hashtagInput"
                  outlined
                  rounded
                  dense
                  @keyup.enter="addHastag"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col offset="3" cols="8">
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
              <v-col offset="1" cols="2">
                <v-subheader>테마선택</v-subheader>
              </v-col>
              <v-col cols="8">
                <v-text-field
                  label="테마선택"
                  v-model="roomInfo.name"
                  outlined
                  rounded
                  dense
                ></v-text-field>
              </v-col>
            </v-row>

          </v-container>
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
  </div>
</template>

<script>
export default {
  name: 'MenuPopup',
  data: function () {
    return {
      dialog: false,
      roomInfo: {
        name: null,
        isSecret: false,
        password: null,
        passwordConfirmation: null,
        hashtagInput: null,
        hashtags: [],
        theme: null,
      }
    }
  },
  methods: {
    addHastag: function () {
      this.roomInfo.hashtags.push(this.roomInfo.hashtagInput)
      this.roomInfo.hashtagInput = null
    },
    deleteHashtag: function (hashtag) {
      this.roomInfo.hashtags = this.roomInfo.hashtags.filter(v => v != hashtag)
    }
  }
}
</script>

<style>

</style>
