<template>
  <div class="d-flex justify-content-center my-auto background">
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
          <h1>{{ drinkingMessage }}</h1>   
        </div>
        <div class="d-flex justify-content-center p-3 mx-auto" style="width:100px;" v-if="isUser">
          <TodayAlcohol @updateDrinkingInfo="updateDrinkingInfo"/>
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
      this.$router.push({name: 'MyPage', params: {userId: this.userInfo.id}})
    },
    updateDrinkingInfo: function (item) {
      if (this.date[this.date.length-1]['date'] === this.end_date) {
        this.date[this.date.length-1]['count'] += item['count']
      } else {
        this.date.push(item)
      }
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
    },
    drinkingMessage: function () {
      const dateCount = this.date.length

      if (dateCount >= 10) {
        return '간 meling'
      } else if (dateCount >= 5) {
        return '분발하자'
      } else {
        return '몹시 건강하구나'
      }
    }
  },
  created: function () {
    if (this.$route.params.user) {
      this.userInfo = this.$route.params.user
    }

    axios({
      method: 'GET',
      url: `${process.env.VUE_APP_API_URL}/drinking/history/${this.userInfo.userId}`,
      headers: {Authorization: `Bearer ${localStorage.getItem('jwt')}`}
    })
      .then(res => {
        this.date = res.data
      })
      .catch(err => {
        console.log(err)
      })

  }
}
</script>

<style scoped>

.background {
  background-size : cover;
  height: 100vh;
  background-image: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.5) ), url(https://cdn.pixabay.com/photo/2019/05/19/23/47/clouds-4215608_960_720.jpg);
  
}

.commit-box {
  width: 80%;
  margin-top: 10vh;
  color: white;
}

.outer-box{
  border: 1px solid white;
  border-radius: 10px;

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
