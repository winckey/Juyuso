<template>
  <v-row class="table-list"
    :style="{ backgroundImage: 'url(' + require(`@/assets/main_theme.png`) + ')'}">
    <!-- 방 목록 -->
    <table-matrix class="px-16 py-8" />
  </v-row>
</template>

<script>
import TableMatrix from '@/components/table_list/table-matrix.vue'

export default {
  name: 'TableList',
  components: {
    TableMatrix
  },
}
</script>

<style scoped>
  .table-list {
    background-attachment: fixed;
    background-size : cover;
  }

</style>
