<template>
  <div class="d-flex justify-content-center my-auto">
    <div class="commit-box">
      <div class="mx-auto p-3">

        <div class="d-flex justify-content-between" >
          <!-- <p>{{user.nickname}}</p> -->
          <h2>음주 기록</h2>
          <div @click="goMyPage" style="cursor:pointer" v-if="isUser">
            <h2 style="display:inline">마이 페이지 돌아가기</h2>
            <v-icon large color="white" class="pb-3">
              mdi-arrow-up-bold
            </v-icon>
          </div>
        </div>

        <div class="outer-box m-3">
          <div class="p-2">
             <div id="app" class="my-5">
                <calendar-heatmap
                  :values="date"
                  :start-date="2021-1-22" 
                  :end-date="end_date"
                  :range-color="[
                    '#ebedf0',
                    '#dae2ef',
                    '#c0ddf9',
                    '#73b3f3',
                    '#3886e1',
                    '#17459e',
                  ]"
                  tooltip-unit="소주+맥주"
                />
              </div>
          </div>
        </div>
        
        <div class="d-flex justify-content-center p-4" v-if="isUser">
          <h1>너의 간은 녹는 중ㅋ</h1>   
        </div>
        <div class="d-flex justify-content-center p-3 mx-auto" style="width:100px;" v-if="isUser">
          <TodayAlcohol/>
        </div>

      </div>

    </div>
  </div>
</template>

<script>
import axios from 'axios'
import TodayAlcohol from '@/components/accounts/today-alcohol.vue'
import {mapState} from 'vuex'

export default {
  name: 'MyData',
  props: {
    user: Object
  },
  components: {
    TodayAlcohol
  },
  data: function() {
    return {
      userInfo: null,
      date: [{ date: '2021-6-27', count: 6 },{ date: '2021-9-21', count: 6 }, { date: '2021-2-21', count: 4 }],
    }
  },
  methods: {
    goMyPage: function () {
      this.$router.push({name: 'MyPage', params: {userId: this.user.id}})
    }
    
  },
  computed: {
    ...mapState('accounts', {stateUser:'user'}),
    end_date: function () {
      var today = new Date();
      var year = today.getFullYear();
      var month = ('0' + (today.getMonth() + 1)).slice(-2);
      var day = ('0' + today.getDate()).slice(-2);
      var dateString = year + '-' + month  + '-' + day;   
      return dateString
    },
    isUser: function (){
      if (this.stateUser.id === this.userInfo.id) {
        return true
      } else {
        return false
      }
    }
  },
  created: function () {
    console.log('created시작')
    if (this.$route.params.user) {
      this.userInfo = this.$route.params.user
      console.log('유저 정보 담기 완료')
    }
    axios({
      method: 'GET',
      url: `${process.env.VUE_APP_API_URL}/drinking/history/${this.userInfo.userId}`,
      headrs: {Authorization: `Bearer ${localStorage.getItem('jwt')}`}
    })
      .then(res => {
        console.log(res.data)
        this.date = res.data
        // this.$router.go()
      })
      .catch(err => {
        console.log(err)
      })
  }
}
</script>

<style scoped>
.commit-box {
  width: 80%;
  margin-top: 10vh;
  color: white;
}

.outer-box{
  border: 1px solid white;
  border-radius: 10px;
  color: purple;
}

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3ea0;
  margin-top: 60px;
}

</style>
