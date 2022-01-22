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
            <p>dd</p>
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
  data: function () {
    return {

    }
  },
  methods: {
    goTodayAlcohol: function () {
      this.$router.push({name:'TodayAlcohol'})
    }
  }
}
</script>

<style scoped>
.commit-box {
  width: 800px;
  color: white;
}

.outer-box{
  border: 1px solid white;
  border-radius: 10px;
}


</style>
