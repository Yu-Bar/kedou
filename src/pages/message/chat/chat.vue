<template>
  <KedouBlankNavbar/>

	<view>
    <view v-if="session != null" class="message-list" >
      <view class="fixed-content">
        <view class="chat-user-info">
          <image src="/static/dark_goback.png" @click="goBack" class="goback-icon">后退</image>
          <image :src="session.profile" class="chat-user-profile"  @click="getIntoUserSpace(session.userId)"></image>
          <text class="chat-user-nickname">{{session.nickname}}</text>
        </view>
        <view class="divider"></view>
      </view>

      <scroll-view ref="scrollView" scroll-y="true" class="chat-scroll">

        <view v-if="session.messageList != null">
          <view v-for="(message, index) in session.messageList" :key="index" class="message-item">
            <view v-if="message.createUser == memberStore.profile?.id" class="right-message">
              <text class="message-chat-content">{{message.content}}</text>
              <image :src=" memberStore.profile?.profile" class="message-user-profile" @click="getIntoUserSpace(message.createUser)"></image>
            </view>
            <view v-else class="left-message">
              <image :src="session.profile" class="message-user-profile"  @click="getIntoUserSpace(message.createUser)"></image>
              <text class="message-chat-content">{{message.content}}</text>
            </view>
          </view>
        </view>

          <view class="left-message" style="visibility: hidden">
            <image :src="memberStore.profile?.profile" class="message-user-profile" ></image>
            <text class="message-chat-content">没有消息</text>
          </view>
          <view class="left-message" style="visibility: hidden">
            <image :src="memberStore.profile?.profile" class="message-user-profile" ></image>
            <text class="message-chat-content">没有消息</text>
          </view>

      </scroll-view>
      <view id="scrollToBottom"></view>
      <view class="message-commit">
        <textarea v-model="messageContent" class="message-input" placeholder="发送消息"></textarea>
        <button class="submit-button" @click="sendMessage">发送</button>
      </view>

    </view>
	</view>
</template>

<script>
	import {useMessageStore} from "@/stores/modules/message";
  import {useMemberStore} from "@/stores";
  import {sendMessage} from "@/service/MessageApi";

  export default {
		data() {
			return {
				messageStore: useMessageStore(),
        session: null,
        messageContent: '',
        memberStore: useMemberStore(),
			}
		},
    watch: {
      // 有新消息时自动滚动到底部
        session: {
          handler: function(val, oldVal) {
            console.log('session更新')
            setTimeout(this.scrollToBottom,200)
          },
          deep: true
        },
    },
		methods: {
      goBack() {
        // 返回上一个页面
        uni.navigateBack();
      },
      async sendMessage() {
        if (this.messageContent != '') {
          const res = await sendMessage({
            session: this.session.session,
            receiver: this.session.userId,
            content: this.messageContent,
          })
          if (res.code == 1) {
            this.messageStore.addMessageToSession(res.data)
            this.messageContent = ''
            // this.scrollToBottom();
          } else {
            await uni.showToast({
              title: res.msg,
              icon: 'error'
            })
          }
        }
      },
      // 进入用户主页
      getIntoUserSpace(userId) {
        console.log('进入用户主页', userId)
        uni.navigateTo({
          url: `/pages/userspace/userspace?user=${userId}`
        })
      },
      // 滚动到底部的逻辑
      scrollToBottom() {
        this.$nextTick(() => {
          uni.createSelectorQuery().in(this).select('#scrollToBottom').boundingClientRect(rect => {
            if (rect) {
              console.log("滚动到底部")
              uni.pageScrollTo({
                selector: '#scrollToBottom',
                duration: 500,
              });
            }
          }).exec();
        });
      },
      scrollToBottomNow() {
        this.$nextTick(() => {
          uni.createSelectorQuery().in(this).select('#scrollToBottom').boundingClientRect(rect => {
            if (rect) {
              console.log("滚动到底部")
              uni.pageScrollTo({
                selector: '#scrollToBottom',
                duration: 0, // 立即滚动
              });
            }
          }).exec();
        });
      },
    },
    onLoad(options) {
      if(options.sessionId){
        this.session = this.messageStore.getSession(parseInt(options.sessionId))
      }
    },
    mounted() {
      this.scrollToBottomNow();
    },
}
</script>

<style>
.fixed-content {
  position: sticky;
  top: 50rpx;
  background: none;
  height: 70rpx;
  z-index: 999; /* 确保在顶部 */
  /* ...其他样式... */
}

.goback-icon {
  width: 28px; /* 图片宽度 */
  height: 28px; /* 图片高度 */
  z-index: 9999; /* 图片层级 */
  opacity: 80%;
}

.chat-user-info {
  margin-left: 10rpx;
  display: flex;
  align-items: center;
}

.chat-user-profile {
  border-radius: 50%; /* 将图片裁剪为圆形 */
  width: 70rpx;
  height: 70rpx;
  margin-left: 30rpx;
}

.chat-user-nickname {
  margin-left: 10rpx;
}

/* 分隔线样式 */
.divider {
  margin-top: 10rpx;
  height: 3rpx;
  background-color: #ccc; /* 灰色 */
  /* 可以根据需求设置其他样式，如宽度、边距等 */
}

.chat-scroll {
  position: relative;
  margin-top: 10rpx;
}

.message-item {
  display: flex;
  flex-direction: column; /* 设置垂直方向排列 */
}

.left-message {
  margin-left: 10rpx;
  display: flex;
  align-items: center;
  margin-top: 30rpx;
}
.left-message > .message-chat-content {
  background: #f3f3f3;
}
.right-message {
  display: flex;
  align-items: center;
  margin-top: 30rpx;
  margin-right: 20rpx;
  justify-content: flex-end; /* 将右侧消息元素放置在父容器的最右侧 */
  align-self: flex-end; /* 将右侧消息元素自身对齐到父容器的最右侧 */
}

.right-message > .message-chat-content {
  background: #527dfd;
  color: #fbfcfb;
}

.message-user-profile {
  border-radius: 50%; /* 将图片裁剪为圆形 */
  width: 70rpx;
  height: 70rpx;
  margin-left: 30rpx;
}

.message-chat-content {
  margin-left: 10rpx;
  border-radius: 20rpx;
  height: 70rpx;
  line-height: 70rpx;
  /* 添加左右内边距 */
  padding-left: 10rpx;
  padding-right: 10rpx;
}

.message-commit {
  padding: 0;
  position: fixed;
  background: #fff;
  display: flex;
  bottom: 0;
  height: 180rpx;
  width: 100%;
  font-size: 35rpx;
}

.message-input {
  width: 80%;
  margin-top: 20rpx;
  padding: 10rpx;
  border-radius: 20rpx;
  height: 80rpx;
  color: black;
  background: #f3f3f3;
}

.submit-button {
  width: 15%;
  margin-right: 10rpx;
  margin-top: 30rpx;
  background-color: #fc2b55;
  color: #fff;
  border: none;
  border-radius: 50rpx;
  cursor: pointer;
  height: 80rpx;
  font-size: 25rpx;
  text-align: center;
  display: grid;
  place-items: center; /* 垂直和水平居中 */
}
</style>
