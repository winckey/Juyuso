<template>
  <div class="d-flex justify-content-center my-auto">
    <div class="commit-box">
      <div class="mx-auto p-3">

        <div class="d-flex justify-content-between">
          <h2>술이 술술</h2>
          <h2>마이 페이지로 돌아가기</h2>
        </div>

        <div class="outer-box my-3">
          <div class="p-2">
             <div id="app">
                <calendar-heatmap
                  :values="[{ date: '2021-9-27', count: 6 },{ date: '2021-9-21', count: 6 }]"
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
                  tooltip-unit="소주~"
                />
              </div>
          </div>
        </div>

        <div class="d-flex justify-content-center p-4">
          <h1>너의 간은 녹는 중ㅋ</h1>   
        </div>
        <div class="d-flex justify-content-center p-3">
          <v-btn  @click="goTodayAlcohol">오늘 주량 추가</v-btn>
        </div>

      </div>

    </div>
  </div>
</template>

<script>


export default {
  name: 'MyData',
  components: {
  },
  data: function() {
    return {
    }
  },
  methods: {
    goTodayAlcohol: function () {
      this.$router.push({name:'TodayAlcohol'})
    },
    
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
  background-color: whitesmoke;
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
