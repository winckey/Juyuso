<template>
  <div>
    <div class="h-100 d-flex justify-content-center">
      <div class="h-100">
        <div class="matrix-container d-flex justify-content-around">
          <div class="table-grid">
            <div
              style="width: 25%; height: 30%; display: inline-block; padding: 5px"
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
        ></v-pagination>
      </div>
    </div>
    <v-radio-group
      class="filter-radio"
      dense
      v-model="filter"
      row
      hide-details
      hide-spin-buttons
      @change="[page=1, getTableList()]"
    >
      <v-radio
        label="전체보기"
        value="0"
        color="#00692a"
      ></v-radio>
      <v-radio
        label="공개방보기"
        value="1"
        color="#00692a"
      ></v-radio>
      <v-radio
        label="비밀방보기"
        value="2"
        color="#00692a"
      ></v-radio>
    </v-radio-group>
  </div>
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
      filter: "0",
    }
  },
  
  mounted: function () {
    this.getTableList()
  },
  methods: {
    getTableList () {
      const token = localStorage.getItem('jwt')
      let params = {
        page: this.page - 1
      }
      if (this.filter == "1") {
        params.common = true
      }
      else if (this.filter == "2") {
        params.common = false
      }
      axios({
        method: 'GET',
        url: `${process.env.VUE_APP_API_URL}/meeting/search`,
        headers: { Authorization: `Bearer ${token}`},
        params: params
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

<style scoped>
  .matrix-container {
    width: 90vw;
    height: 100%;
  }

  .table-grid {
    display: flex;
    justify-content: space-around;
    flex-flow: wrap;
  }

  .filter-radio {
    padding: 5px;
    position: fixed;
    left: 6vw;
    bottom: 100px;
    display: inline-block;
    background: rgba(255, 255, 255, 0.7);
    border-radius: 10px;
  }

</style>