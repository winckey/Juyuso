<template>
  <div>
    
    <!-- 방 개설 팝업창 -->
    <div class="menu-popup">
      <div>방 개설하기</div>
      <div> ∨ </div>
      <menu-popup/>
    </div>
  </div>
</template>

<script>
import MenuPopup from '@/components/table_list/menu-popup.vue'

export default {
  name: 'TableList',
  components: {
    MenuPopup
  }
}
</script>

<style scoped>

  .menu-popup {
    position: fixed;
    transition-property: all;
    transition-duration: .3s;
    bottom: -13%;
    left: 50%;
    text-align: center;
    color: #1B1B32;
    transform: translate(-50%);
  }

  .menu-popup:hover {
    position: fixed;
    bottom: -9%;
    left: 50%;
    transform: translate(-50%);
    text-align: center;
    color: white;
  }


</style>
