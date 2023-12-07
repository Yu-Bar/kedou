<template>
  <KedouBlankNavbar/>
  <view class="message-style">
    <view v-if="sessionList != null" class="session-list">
      <scroll-view scroll-y="true" class="scroll-Y">
        <view v-for="(session, index) in sessionList" :key="index" class="session-item">
          <uni-card @click="getIntoChat(session.session)" :title="session.nickname" :isFull="true"
                    :sub-title="session.messageList[session.messageList.length - 1].content"
                    :thumbnail="session.profile">

          </uni-card>
        </view>
      </scroll-view>
    </view>
  </view>


</template>

<script>
import WebSocketService from "@/service/WebSocketService";
import {useMessageStore} from "@/stores/modules/message";
import {getUserChatInfoList} from "@/service/MessageApi";

export default {
  data() {
    return {
      messageContent: '',
      messageStore: useMessageStore(),
      sessionList: null,
    }
  },
  methods: {
    // 对应的底部 tab 被选中时执行
    showByTab() {
      this.sessionList = this.messageStore.getAllSessions()
      this.getUseInfoForChat()
      console.log('sessionList', this.sessionList)
    },
    async getUseInfoForChat() {
      const ids = this.messageStore.getAllUserIds()
      const res = await getUserChatInfoList(ids)
      this.messageStore.updateChatUserInfo(res.data)
    },
    getIntoChat(sessionId) {
      uni.navigateTo({
        url: `/pages/message/chat/chat?sessionId=${sessionId}`
      })
    },
    sendMessage() {
      const webSocket = WebSocketService.getInstance()
      webSocket.send(this.messageContent)
      //   uni.connectSocket({
      //     // url: 'wss://yubar.top/kedou/ws/1',
      //     url: 'ws://localhost:8077/ws/'+1,
      //     success: data => {
      //       console.log('socket连接成功')
      //     }
      //   })
      //   // const websocket = WebSocketService.getInstance()
      //   // websocket.send('hello，你好！'+ this.messageContent)
      //   uni.sendSocketMessage({
      //     data: this.messageContent
      //   })
      // }
    },
    onShow() {
      console.log('页面显示时执行的逻辑');
      // 在页面显示时执行的逻辑
    },
    onHide() {
      console.log('页面隐藏时执行的逻辑');
      // 在页面隐藏时执行的逻辑
    },
    onLoad() {
      // uni.connectSocket({
      //   // url: 'wss://yubar.top/kedou/ws/1',
      //   url: 'ws://localhost:8077/ws/'+1,
      //   success: data => {
      //     console.log('socket连接成功')
      //   }
      // })
    },
  }
}
</script>

<style>
.message-style {
  background: white;
  height: calc(100vh - 100rpx); /* Adjust height to accommodate the navbar */
}

.session-list {
  position: relative;
  background: none;
}

.session-item {
  background: none;
  color: black;
}
</style>
