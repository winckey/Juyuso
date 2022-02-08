<template>
  <div>
    <v-tabs
      fixed-tab
      centered
      :value="tab">
    <v-tab
      :value="tab"
      @click="changeTab(idx)"
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
          :tab="-1"
          />
        <v-divider></v-divider>
        <FriendList
          v-if="friendsList"
          :friends="friendsList.friendList"
          :tab="tab"/>
      </v-tab-item>
      <v-tab-item>
        <Chatting
          v-if="tab == 1"
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
import Chatting from '@/components/side_bar/chatting.vue'
import { mapState, mapActions } from 'vuex'
export default {
  name: 'FriendTab',
  components: {
    FriendList,
    Chatting
  },
  data: function () {
    return {
      items: [
        {src: 'mdi-account-multiple-outline', name: 'friend'},
        {src: 'mdi-chat-processing-outline', name: 'chat'},
        {src: 'mdi-magnify', name: 'search'},
      ],
      result: []
    }
  },
  methods: {
    ...mapActions('friends', [
      'changeTab'
    ])
  },
  computed: {
    ...mapState('friends', [
      'tab',
      'friendsList',
      'searchList'
    ])
  },
}
</script>

<style>

</style>