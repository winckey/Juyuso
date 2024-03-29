<template>
  <div v-if="roomInfo">
    <v-dialog
      transition="dialog-bottom-transition"
      v-model="dialog"
      width="500"
    >
      <template v-slot:activator="{ on, attrs }" v-if="!search">
        <button
          style="display: block; width: 90%; margin: auto; vertical-align: middle"
          v-on="on"
          v-bind="attrs">
          <div class="table-container">
            <img
              class="table-image"
              :src="require(`@/assets/${imgUrl}.png`)" 
              alt=""
              >
            <div :class="roomInfo.hashtag.length > 0 ? 'table-info' : 'table-info-center'" >
              {{ roomInfo.meetingTitle }}
              <v-icon>{{ roomInfo.common ? '' : 'mdi-lock-outline'}}</v-icon>
            </div>
            <div class="table-hashtag">
              <v-chip
                v-if="roomInfo.hashtag.length > 0"
                class="ma-2"
                color="#e6f6fa"
              >
                {{ roomInfo.hashtag[0] }}
              </v-chip>
            </div>
          </div>
        </button>
      </template>

      <v-card 
        height="450" 
        class="p-4 card-background"
        :style="{backgroundImage: 'url('+ require(`@/assets/theme/${roomInfo.theme}.jpg`)+')'}"
      >
        <div class="inner-card-background p-3">
          <v-card-title class="d-flex justify-content-center">
            <span style="font-size: 1.2em">
            {{ roomInfo.meetingTitle }}
            <v-icon>{{ roomInfo.common ? '' : 'mdi-lock-outline'}}</v-icon>
            </span>
          </v-card-title>
          <v-card-text class="d-flex justify-content-between">
            <span>
              <v-avatar
                class="image-border"
                size="36">
                <v-img :src="userProfileImg"></v-img>
              </v-avatar>
              방장 : {{ roomInfo.nickName }}
            </span>
            <span>
              <v-icon>
                mdi-account
              </v-icon>
              {{ roomInfo.cnt }} / 6
            </span>
          </v-card-text>
          <v-chip
            v-for="hashtag in roomInfo.hashtag"
            :key="hashtag"
            class="ma-2"
            color="#4DB6AC"
            dark
          >
            {{ hashtag }}
          </v-chip>
          <v-card-actions class="enter-btn">
            <v-btn
              color="#4DB6AC"
              dark
              rounded
              @click="enterRoom"
            >
              입장
            </v-btn>
          </v-card-actions>
        </div>
      </v-card>
    </v-dialog>
    <TablePreview
    :roomInfo="roomInfo"
    ref="tablepreview"/>
    <TablePassword
    :password="roomInfo.meetingPassword"
    ref="tablepassword"
    @passwordConfirm="passwordConfirm"/>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import TablePreview from '@/components/table_list/table-preview.vue'
import TablePassword from '@/components/table_list/table-password.vue'
import axios from 'axios'

export default {
  name: 'TableDetailPopup',
  props: {
    roomInfo: Object,
    search: Boolean,
  },
  components: {
    TablePreview,
    TablePassword
  },
  data: function () {
    return {
      dialog: false,
      newRoomInfo: null,
    }
  },
  computed:  {
    ...mapState('accounts', [
      'user'
    ]),
    imgUrl: function () {
      let imgList = [
        'table_beer', 
        'table_chopsticks', 
        'table_nothing', 
        'table_pot', 
        'table_soju'
      ]
      let idx = Math.floor(Math.random() * 5)
      return imgList[idx]
    },
    userProfileImg: function () {
      return `${process.env.VUE_APP_IMG_URL}/${this.roomInfo.userImg}`
    },
  },
  methods: {
    enterRoom: function () {
      if (this.roomInfo.cnt >= 6) {
        this.$toast.open({
          position: 'top',
          message: '인원이 초과되었습니다.',
          type: 'error',
          duration: 2500,
        });
        return
      }
      if (!this.roomInfo.common) {
        this.$refs.tablepassword.dialog = true
      }
      else {
        this.$refs.tablepreview.dialog = true
      }
    },
    getRoomInfo() {
      axios({
        method: 'GET',
        url: `${process.env.VUE_APP_API_URL}/meeting/${this.roomInfo.meetingId}`,
        headers: { Authorization: `Bearer ${localStorage.getItem('jwt')}`}
      })
      .then( res => {
        this.newRoomInfo = res.data
        this.roomInfo.cnt = this.newRoomInfo.cnt
        this.roomInfo.theme = this.newRoomInfo.theme
      })
    },
    passwordConfirm: function () {
      this.$refs.tablepreview.dialog = true
    }
  },
  watch: {
    dialog() {
      this.dialog && this.getRoomInfo();
    }
  }
}
</script>

<style scoped>
  .table-container {
    position: relative;
  }

  .table-info {
    position: absolute;
    left: 50%;
    top: 35%;
    transform: translate(-50%);
    font-size: 1.3em;
    text-shadow: 2px 2px 2px rgb(187, 187, 187);
  }

  .table-info-center {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -40%);
    font-size: 1.3em;
    text-shadow: 2px 2px 2px rgb(187, 187, 187);
  }
  
  .table-hashtag {
    position: absolute;
    left: 50%;
    top: 60%;
    transform: translate(-50%)
  }

  .enter-btn {
    position: absolute;
    bottom: 5%;
    left: 50%;
    transform: translate(-50%);
  }

  .card-background {
    background-size: cover
  }
  
  .inner-card-background {
    background: rgba(255, 255, 255, 0.93);
    height: 100%
  }

  .table-image {
    width: 90%;
    transition-property: all;
    transition-duration: .2s;
  }

  .table-image:hover {
    transform: scale(1.2);
  }

  .image-border {
    box-shadow: 0 0 8px grey;
    margin-right: 5px
  }
</style>
