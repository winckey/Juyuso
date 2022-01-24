<template>
  <v-container>
    <v-row
      class="mb-4">
      <v-col 
        :key="table.id"
        v-for="table in tableList">
        <TableDetailPopup
          :roomInfo="table"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import TableDetailPopup from '@/components/table_list/table-detail-popup.vue'
import axios from 'axios'

export default {
  name: 'TableMatrix',
  components:{
    TableDetailPopup
  },
  data: function () {
    return {
      tableList: []
    }
  },
  
  mounted: function () {
    this.getTableList()
  },
  methods: {
    getTableList () {
      const token = localStorage.getItem('jwt')
      axios({
        method: 'GET',
        url: `${process.env.VUE_APP_API_URL}/meeting/search`,
        headers: { Authorization: `Bearer ${token}`}
      })
      .then( res => {
        this.tableList = res.data.content
        console.log(res.data.content)
      })
      .catch( err => {
        console.log(err)
      })
    },
  }
}
</script>

<style>

</style>