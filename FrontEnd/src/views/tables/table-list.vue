<template>
  <div class="table-list">
    
    <!-- 방 목록 -->
    <table-matrix></table-matrix>
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
    bottom: -100px;
    left: 50%;
    text-align: center;
    color: #1B1B32;
    transform: translate(-50%);
  }

  .menu-popup:hover {
    position: fixed;
    bottom: -40px;
    left: 50%;
    transform: translate(-50%);
    text-align: center;
    color: white;
  }

  .table-list {
    margin-top: 60px;
  }

</style>
