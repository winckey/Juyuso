<template>
  <div class="table-list"
    :style="{ backgroundImage: 'url(' + require(`@/assets/main_theme.png`) + ')'}">
    <!-- 방 목록 -->
    <table-matrix class="h-100" style="padding-bottom: 140px"></table-matrix>
    <!-- 방 개설 팝업창 -->
    <div class="menu-popup">
      <div>방 개설하기</div>
      <div> ∨ </div>
      <TableOrderPopup/>
    </div>
  </div>
</template>

<script>
import TableOrderPopup from '@/components/table_list/table-order-popup.vue'
import TableMatrix from '@/components/table_list/table-matrix.vue'

export default {
  name: 'TableList',
  components: {
    TableOrderPopup,
    TableMatrix
  }
}
</script>

<style scoped>

  .menu-popup {
    position: fixed;
    transition-property: all;
    transition-duration: .3s;
    bottom: -130px;
    left: 50%;
    text-align: center;
    color: #1B1B32;
    transform: translate(-50%);
  }

  .menu-popup:hover {
    position: fixed;
    bottom: -70px;
    left: 50%;
    transform: translate(-50%);
    text-align: center;
    color: white;
  }

  .table-list {
    padding-top: 64px;
    width: 100vw;
    height: 100vh;
    background-attachment: fixed;
    background-size : cover;
  }

</style>
