<template>
  <v-row justify="space-around">
     <v-dialog
        transition="dialog-bottom-transition"
        max-width="600"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="#AED"
            v-bind="attrs"
            v-on="on"
            rounded
          >차단친구 관리</v-btn>
        </template>
        <template v-slot:default="dialog">
          <v-card>
            <v-toolbar
              color="primary"
              dark
            >Opening from the bottom</v-toolbar>
            <v-card-text>
              <div class="text-h2 pa-12">Hello world!</div>
            </v-card-text>
            <v-card-actions class="justify-end">
              <v-btn
                text
                @click="dialog.value = false"
              >Close</v-btn>
            </v-card-actions>
          </v-card>
        </template>
      </v-dialog>

    
  </v-row>
</template>

<script>
export default {
  name: 'BlockedFriend',
  data: function () {
    return {
      
    }
  }
}
</script>

<style>

</style>