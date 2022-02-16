<template>
  <div style="height: 100%">
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
      v-model="tab"
      class="h-100">
      <v-divider class="my-0"></v-divider>
      <v-tab-item>
        
        <span v-if="friendsList.length!=0 && friendsList.friendRequestList.length!=0" class="px-3">친구 요청</span>
        <FriendList
          v-if="friendsList.length!=0 && friendsList.friendRequestList.length!=0"
          :friends="friendsList.friendRequestList"
          :tab="-1"
          />
        <v-divider v-if="friendsList.length!=0 && friendsList.friendRequestList.length!=0" class="my-0"></v-divider>
        <FriendList
          v-if="friendsList"
          :friends="friendsList.friendList"
          :tab="tab"/>
      </v-tab-item>
      <v-tab-item
        class="h-100">
        <Chatting
          v-if="tab == 1"
          :tab="tab"/>
      </v-tab-item>
      <v-tab-item>
        <!-- <FriendList
          v-if="searchList"
          :friends="searchList.friendRequestList"
          :tab="tab"/> -->
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