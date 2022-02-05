<template>
  <div>
    <v-dialog
      transition="dialog-bottom-transition"
      v-model="dialog"
      width="600"
    >
      <template v-slot:activator="{ on, attrs }" v-if="!search">
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
              <v-icon>{{ roomInfo.common ? '' : 'mdi-lock-outline'}}</v-icon>
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

      <v-card height="500" class="p-4">
        <v-card-title class="d-flex justify-content-center">
          <span>
          {{ roomInfo.meetingTitle }}
          <v-icon>{{ roomInfo.common ? '' : 'mdi-lock-outline'}}</v-icon>
          </span>
        </v-card-title>
        <v-card-text>
          방장 : {{ roomInfo.nickName }}
        </v-card-text>
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
import { mapActions, mapState } from 'vuex'
import TablePreview from '@/components/table_list/table-preview.vue'
import TablePassword from '@/components/table_list/table-password.vue'

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
      if (!this.roomInfo.common) {
        this.$refs.tablepassword.dialog = true
      }
      else {
        this.$refs.tablepreview.dialog = true
      }
    },
    passwordConfirm: function () {
      this.$refs.tablepreview.dialog = true
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