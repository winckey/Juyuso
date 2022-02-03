<template>
  <div>
    <v-progress-linear
      v-if="this.titanicGame"
      color="light-blue"
      height="15"
      width="100"
      :value="titanicGame.curAmount"
      striped
    ></v-progress-linear>
    <user-video class="col-md-4" :stream-manager="publisher"/>
    <user-video class="col-md-4" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
    <v-btn
      :disabled="!MyTurn"
      color="white"
      @mousedown="setStartTime"
      @mouseup="setEndTime">클릭</v-btn>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import UserVideo from '@/components/table/user-video.vue'

export default {
  name: 'TitanicGame',
  components: {
    UserVideo
  },
  props: {
    subscribers: Array,
    publisher: Object,
  },
  data: function () {
    return {
      clickStart: 0,
      MyTurn: false,
      myConnectionId: null,
      titanicGame: {
        type: 'Titanic',
        maxAmount: 0,
        curAmount: 0,
        curMember: 0,
        members: [],
      }
    }
  },
  mounted: function () {
    const gameInfo = JSON.parse(this.gameInfo)
    this.titanicGame = {...gameInfo}
    this.myConnectionId = this.publisher.stream.connection.connectionId
  },
  methods: {
    isMyTurn: function () {
      this.MyTurn = this.myConnectionId == this.titanicGame.members[this.titanicGame.curMember]
    },
    setStartTime: function () {
      this.clickStart = new Date().getTime()
    },
    setEndTime: function () {
      let diff = (new Date().getTime() - this.clickStart) / 100
      this.titanicGame.curAmount += diff
      this.titanicGame.curMember = (this.titanicGame.curMember + 1) % this.titanicGame.members.length
      console.log(diff)
      this.sendGameInfo()
    },
    sendGameInfo: function () {
      this.session.signal({
        data: this.titanicGame,
        to: [],
        type: 'gameInfo'
      })
    }
  },
  computed: {
    ...mapState('openviduStore', [
      'session',
      'gameInfo',
    ])
  },
  watch: {
    gameInfo: function () {
      this.titanicGame = {...JSON.parse(this.gameInfo)}
      console.log(this.titanicGame)
      this.isMyTurn()
    }
  }
}
</script>

<style>

</style>