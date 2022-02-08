<template>
  <div>
    
    <div class="container">
      <div class="row justify-content-around row-cols-md-4 row-cols-sm-3">
        <div 
          class="col my-5"
          :key="table.id"
          v-for="table in tableList">
          <TableDetailPopup
            :roomInfo="table"/>
        </div>
      </div>
    </div>
    <v-pagination
      v-model="page"
      :length="totalPage"
      :total-visible="7"
      color="#00692a"
      circle
      @input="getTableList"
      style="margin-bottom: 150px"
    ></v-pagination>
  </div>
  <!-- <v-container fluid>
    <v-row>
      <v-col
        align-self-center
        cols="3"
        :key="table.id"
        v-for="table in tableList">
        <TableDetailPopup
          :roomInfo="table"/>
      </v-col>
    </v-row>
  </v-container> -->
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
      tableList: [],
      page: 1,
      totalPage: 1,
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
        headers: { Authorization: `Bearer ${token}`},
        params: {
          page: this.page - 1
        }
      })
      .then( res => {
        this.tableList = res.data.content
        this.totalPage = res.data.totalPages
        console.log(res.data)
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