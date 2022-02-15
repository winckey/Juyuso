<template>
  <v-menu
    bottom
    v-model="menu"
    :close-on-content-click="false"
    offset-y
  >
    <template v-slot:activator="{ on, attrs }">
      <v-btn 
        @click="null" 
        icon 
        dark
        v-bind="attrs"
        v-on="on">
        <v-icon>mdi-information-outline</v-icon>
      </v-btn>
    </template>

    <v-card
      width="380"
      class="p-4 card-background"
      :style="{backgroundImage: 'url('+ require(`@/assets/theme/${roomInfo.theme}.jpg`)+')'}"
      >
        <div class="inner-card-background p-3">
          <v-card-title class="d-flex justify-content-center">
            <span>
            {{ roomInfo.meetingTitle }}
            <v-icon>{{ roomInfo.common ? '' : 'mdi-lock-outline'}}</v-icon>
            </span>
          </v-card-title>
          <v-card-text>
            <div>
              방번호 : {{ roomInfo.meetingId }}
              <v-tooltip right :open-on-click="false" :open-on-focus="false">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn 
                    icon 
                    v-bind="attrs"
                    v-on="on"
                    @click="copyText">
                    <v-icon small>mdi-content-copy</v-icon>
                  </v-btn>
                </template>
                <span>방번호 복사하기</span>
              </v-tooltip>
              
              <span v-show="showCopyText" class="copy-text">
                복사되었습니다!
              </span>
            </div>
            <div>
              방장 : 
              <v-avatar
                class="image-border"
                size="36">
                <v-img :src="userProfileImg"></v-img>
              </v-avatar>
              {{ roomInfo.nickName }}
            </div>
            <div>
              해시태그 : 
              <v-chip
                v-for="hashtag in roomInfo.hashtag"
                :key="hashtag"
                class="ma-2"
                color="#4DB6AC"
                small
                dark
              >
                {{ hashtag }}
              </v-chip>
            </div>
            <div v-if="!roomInfo.common">
              비밀번호 : 
              <v-btn v-show="!showPassword" rounded small @click="showPassword = true">
                비밀번호 확인  
              </v-btn>
              <span v-show="showPassword">
                {{ roomInfo.meetingPassword }}
              </span>
            </div>
          </v-card-text>
        </div>
      </v-card>
  </v-menu>
</template>

<script>
export default {
  name: 'RoomInfoMenu',
  data: function () {
    return {
      menu: false,
      showPassword: false,
      showCopyText: false
    }
  },
  props: {
    roomInfo: Object
  },
  methods: {
    copyText() {
      this.showCopyText = true
      navigator.clipboard.writeText(this.roomInfo.meetingId)
      setTimeout(() => {
        this.showCopyText = false
      }, 2000);

    }
  },
  computed: {
    userProfileImg: function () {
      return `${process.env.VUE_APP_IMG_URL}/${this.roomInfo.userImg}`
    }
  },
  watch: {
    menu() {
      if (!this.menu) {
        this.showPassword = false
        this.showCopyText = false
      }
    }
  }


}
</script>

<style scoped>
  .card-background {
    background-size: cover
  }
  
  .inner-card-background {
    background: rgba(255, 255, 255, 0.93);
    height: 100%
  }
  .copy-text {
    color: #878787;
    transition-property: all;
    transition-duration: .5s;
  }

</style>