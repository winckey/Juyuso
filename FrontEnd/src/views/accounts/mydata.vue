<template>
  <div class="d-flex justify-content-center my-auto">
    <div class="commit-box">
      <div class="mx-auto p-3">

        <div class="d-flex justify-content-between" >
          <!-- <p>{{user.nickname}}</p> -->
          <h2>니 음주 기록</h2>
          <div @click="goMyPage" style="cursor:pointer">
            <h2 style="display:inline">마이 페이지로 돌아가기</h2>
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
        
        <div class="d-flex justify-content-center p-4">
          <h1>너의 간은 녹는 중ㅋ</h1>   
        </div>
        <div class="d-flex justify-content-center p-3 mx-auto" style="width:100px;">
          <TodayAlcohol/>
        </div>

      </div>

    </div>
  </div>
</template>

<script>
// import axios from 'axios'
import TodayAlcohol from '@/components/accounts/today-alcohol.vue'
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
      date: [{ date: '2021-6-27', count: 6 },{ date: '2021-9-21', count: 6 }, { date: '2021-2-21', count: 4 }],
    }
  },
  methods: {
    goMyPage: function () {
      console.log('goWallet')
      console.log(this.user.id)
      this.$router.push({name: 'MyPage', params: this.user.id})
    }
    
  },
  computed: {
    end_date: function () {
      var today = new Date();
      var year = today.getFullYear();
      var month = ('0' + (today.getMonth() + 1)).slice(-2);
      var day = ('0' + today.getDate()).slice(-2);
      var dateString = year + '-' + month  + '-' + day;   

      console.log(dateString)
      return dateString
    }
  },
  // created: function () {
  //   axios({
  //     method: 'get',
  //     url: `${process.env.VUE_APP_API_URL}/drinking/${this.user.id}`,
  //     headrs: {Authorization: `Bearer ${localStorage.getItem('jwt')}`}
  //   })
  //     .then(res => {
  //       console.log(res.data)
  //       this.date = res.data
  //     })
  //     .catch(err => {
  //       console.log(err)
  //     })
  // }
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
  /* background-color: whitesmoke; */
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
