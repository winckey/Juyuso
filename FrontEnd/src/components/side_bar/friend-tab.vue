<template>
  <div>
    <v-tabs
      fixed-tab
      centered
      v-model="tab">
    <v-tab
      v-for="(item, idx) in items"
      :key="idx">
      <v-icon>{{ item.src }}</v-icon>
    </v-tab>
    </v-tabs>
    <v-tabs-items
      v-model="tab">
      <v-tab-item>
        <FriendList
          v-if="friendsList"
          :friends="friendsList.friendRequestList"
          :tab="-1"/>
        <v-divider></v-divider>
        <FriendList
          v-if="friendsList"
          :friends="friendsList.friendList"
          :tab="tab"/>
      </v-tab-item>
      <v-tab-item>
        <FriendList
          v-if="result"
          :friends="result"
          :tab="tab"/>
        <v-divider></v-divider>
      </v-tab-item>
      <v-tab-item>
        <!-- <FriendList
          v-if="searchList"
          :friends="searchList.friendRequestList"
          :tab="tab"/> -->
        <v-divider></v-divider>
        <FriendList
          v-if="searchList"
          :friends="searchList.friendList"
          :notFriends="searchList.notFriendList"
          :tab="tab"/>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
import FriendList from '@/components/side_bar/friend-list.vue'
import { mapState } from 'vuex'
export default {
  name: 'FriendTab',
  components: {
    FriendList
  },
  data: function () {
    return {
      tab: 0,
      items: [
        {src: 'mdi-account-multiple-outline', name: 'friend'},
        {src: 'mdi-chat-processing-outline', name: 'chat'},
        {src: 'mdi-magnify', name: 'search'},
      ],
      result: []
    }
  },
  computed: {
    ...mapState('friends', [
      'friendsList',
      'searchList'
    ])
  }
}
</script>

<style>

</style>