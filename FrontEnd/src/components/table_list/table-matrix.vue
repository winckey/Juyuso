<template>
  <v-col>
    <v-row>
      <v-col>
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
            label="전체"
            value="0"
            color="#4DB6AC"
          ></v-radio>
          <v-radio
            label="공개방"
            value="1"
            color="#4DB6AC"
          ></v-radio>
          <v-radio
            label="비밀방"
            value="2"
            color="#4DB6AC"
          ></v-radio>
        </v-radio-group>
      </v-col>
      <v-col>
        <TableEnterPopup />
        <TableOrderPopup />
      </v-col>
    </v-row>

    <v-row class="h-auto mt-n8">
      <v-col class="h-30" v-for="table in tableList" :key="table.id" cols="12" xs="12" md="6" lg="4" xl="3" align-self="center">
        <v-card class="transparent" outlined>
          <TableDetailPopup
            :roomInfo="table"
          />
        </v-card>
      </v-col>
    </v-row>
    
    <v-row>
      <v-pagination
        v-model="page"
        :length="totalPage"
        :total-visible="7"
        class="my-2"
        color="#4DB6AC"
        circle
        @input="getTableList"
      />
    </v-row>
  </v-col>
</template>

<script>
import TableDetailPopup from '@/components/table_list/table-detail-popup.vue'
import TableOrderPopup from '@/components/table_list/table-order-popup.vue'
import TableEnterPopup from '@/components/table_list/table-enter-popup.vue'
import api from '@/common/api'

export default {
  name: 'TableMatrix',
  components:{
    TableOrderPopup,
    TableDetailPopup,
    TableEnterPopup
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
    getTableList() {
      let params = {
        page: this.page - 1,
      }

      this.filter == 1 ? 
        params.common = true : 
          this.filter == 2 ? 
            params.common = false : 
              null
      
      api.get('/meeting/search', {
        params,
      }).then( res => {
          this.tableList = res.data.content
          this.totalPage = res.data.totalPages
      }).catch( err => {
          console.log(err)
      })
    },
  }
}
</script>

<style scoped>

  .filter-radio {
    width: auto;
    padding: 5px;
    margin-top: 0;
    margin-left: 3vw;
    display: inline-block;
    background: rgba(255, 255, 255, 0.7);
    border-radius: 10px;
  }

</style>