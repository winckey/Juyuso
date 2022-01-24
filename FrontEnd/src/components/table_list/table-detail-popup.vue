<template>
  <v-dialog
    transition="dialog-bottom-transition"
    v-model="dialog"
    width="800"
  >
    <template v-slot:activator="{ on, attrs }">
      <button
        v-on="on"
        v-bind="attrs">
      <img 
        src="@/assets/table.png" 
        alt=""
        >
      </button>
    </template>

    <v-card>
      <v-card-title class="grey lighten-2 d-flex justify-content-center">
        <span>
        {{ roomInfo.title }}
        </span>
      </v-card-title>
      <v-form ref="tableOrderForm">
        <v-container>
          
        </v-container>
      </v-form>
      <v-divider></v-divider>
      <v-card-actions class="d-flex justify-content-center">
        <v-btn
          color="#1CFD9F"
          rounded
          @click="createRoom"
        >
          입장
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'TableDetailPopup',
  props: {
    roomInfo: Object,
  },
  data: function () {
    return {
      dialog: false
    }
  }
}
</script>

<style>

</style>
