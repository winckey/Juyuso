<template>
  <div>
    <v-dialog
      transition="dialog-bottom-transition"
      v-model="dialog"
      width="290"
    >
      <template v-slot:activator="{ on: dialog, attrs }">
        <v-tooltip top :open-on-click="false" :open-on-focus="false">
          <template v-slot:activator="{ on: tooltip }">
            <v-btn
              fab
              dark
              color="#4DB6AC"
              class="float-right" style="margin-right: 3vw;" 
              v-bind="attrs"
              v-on="{ ...tooltip, ...dialog }"
            >
              <v-icon dark>
                mdi-store-search-outline 
              </v-icon>
            </v-btn>
          </template>
          <span>방 번호로 입장하기</span>
        </v-tooltip>
      </template>

      <v-card class="p-2">
        <v-card-title class="d-flex justify-content-center">
          <span style="font-size: 1.5rem">
          방 번호로 입장하기
          </span>
        </v-card-title>
        <div class="px-5 my-2">

        <v-text-field v-model="meetingId" @keyup.enter="searchRoom">

        </v-text-field>
        </div>
        <v-card-actions class="d-flex justify-content-center m-2">
          <v-btn
            color="#4DB6AC"
            dark
            rounded
            small
            @click="[dialog=false, enterRoom()]"
            style="font-size: 1.2rem"
          >
            입장
          </v-btn>
        </v-card-actions>    
      </v-card>
      
    </v-dialog>
    <TableDetailPopup
      ref="detailpopup"
      :search="true" 
      :roomInfo="roomInfo"/>
  </div>
</template>

<script>
export default {
  name: 'TableEnterPopup',
  data: function () {
    return {
      meetingId: null,
      roomInfo: null,
      dialog: false,
    }
  },
  methods: {
    searchRoom() {
      // axios 연결
    },
    enterRoom() {
      this.$refs.detailpopup.dialog=true
    }
  },
  watch: {
    dialog() {
      if(!this.dialog) {
        this.roomInfo = null
        this.meetingId = null
      }
    }
  }
}
</script>

<style>

</style>