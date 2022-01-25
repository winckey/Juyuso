<template>
  <v-dialog
    transition="dialog-bottom-transition"
    v-model="dialog"
    width="600"
  >
    <template v-slot:activator="{ on, attrs }">
      <button
        v-on="on"
        v-bind="attrs">
        <div class="table-container">
          <img 
            src="@/assets/table.png" 
            alt=""
            >
          <div class="table-info">
            {{ roomInfo.meetingTitle }}
          </div>
          <div class="table-hashtag">
            <v-chip
              class="ma-2"
              color=""
            >
              {{ roomInfo.hashtag[0] }}
            </v-chip>
          </div>
        </div>
      </button>
    </template>

    <v-card height="500">
      <v-card-title class="d-flex justify-content-center">
        <span>
        {{ roomInfo.meetingTitle }}
        </span>
      </v-card-title>
      <v-chip
        v-for="hashtag in roomInfo.hashtag"
        :key="hashtag"
        class="ma-2"
        color="#FAC372"
      >
        {{ hashtag }}
      </v-chip>

      <!-- <v-divider></v-divider> -->
      <v-card-actions class="enter-btn">
        <v-btn
          color="#1CFD9F"
          rounded
          @click="enterRoom"
        >
          입장
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: 'TableDetailPopup',
  props: {
    roomInfo: Object,
  },
  data: function () {
    return {
      dialog: false
    }
  },
  computed:  {
    ...mapState('accounts', [
      'user'
    ])
  },
  methods: {
    ...mapActions('openviduStore', [
      'joinSession'
    ]),
    enterRoom: function () {
      let roomInfo = {
        sessionId: String(this.roomInfo.meetingId),
        isCreate: true,
      }
      console.log(roomInfo)
      this.joinSession(roomInfo)
      this.$router.push({ name: 'Table', params: { roomId: this.roomInfo.meetingId }})
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
    top: 20%;
    transform: translate(-50%);
  }
  
  .table-hashtag {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%)
  }

  .enter-btn {
    position: absolute;
    bottom: 5%;
    left: 50%;
    transform: translate(-50%);
  }

</style>
