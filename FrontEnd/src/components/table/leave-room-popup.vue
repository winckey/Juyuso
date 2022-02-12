<template>
  <v-dialog
      v-model="dialog"
      persistent
      max-width="290"
    >
      <v-card>
        <v-card-title tag="span" class="d-flex justify-content-center">❗ 경고 ❗</v-card-title>
        <v-card-text class="text-center">방을 나가시겠습니까?</v-card-text>
        <v-card-actions class="d-flex justify-content-center">
          <v-btn
            color="green darken-1"
            text
            @click="cancel"
          >
            취소
          </v-btn>
          <v-btn
            color="red darken-1"
            text
            @click="agree"
          >
            나가기
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
</template>

<script>
export default {
  name: 'LeaveRoomPopup',
  data: function () {
    return {
      dialog: false,
      resolve: null,
      reject: null,
    }
  },
  methods: {
    open() {
      this.dialog = true
      return new Promise((resolve, reject) => {
        this.resolve = resolve;
        this.reject = reject;
      });
    },
    agree() {
      this.resolve(true);
      this.dialog = false;
    },
    cancel() {
      this.resolve(false);
      this.dialog = false;
    }
  }
}
</script>

<style>

</style>