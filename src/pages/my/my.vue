<template>
  <BlankNavbar/>
  <view v-if="userInfo">
    <uni-card :title="userInfo.nickname" :isFull="true" :sub-title="userInfo.username" :extra="userInfo.bio"
              :thumbnail="userInfo.profile">
      <text class="number" @click="showLikes(userInfo.id)">{{ userInfo.likes }}</text>
      获赞 |
      <text class="number" @click="showFriends(userInfo.friends)">{{ userInfo.friends }}</text>
      朋友 |
      <text class="number" @click="showFollowing(userInfo.following)">{{ userInfo.following }}</text>
      关注 |
      <text class="number" @click="showFollower(userInfo.follower)">{{ userInfo.follower }}</text>
      粉丝 |
      <view v-if="true/*checkFollow(userInfo.id)*/" style="display: inline-block;float: right">
        <button class="button">
          <image class="icon_img" src="@/static/follow.png" @click="follow(userInfo.id)"></image>
          关注
        </button>
      </view>
      <view v-else style="display: inline-block;float: right">
        <button class="button">
          <image class="icon_img" src="@/static/unfollow.png" @click="unfollow(userInfo.id)"></image>
          已关注
        </button>
      </view>
    </uni-card>

    <uni-card :isFull="true">
      <image class="icon_img" src="@/static/address.png"></image>
      {{ userInfo.address }} |
      <image class="icon_img" src="@/static/school.png"></image>
      {{ userInfo.school }} |
      <image v-if="userInfo.sex===1" class="icon_img" src="@/static/male.png"></image>
      <image v-else class="icon_img" src="@/static/female.png"></image>
      |
      <button class="button" style="float: right">
        <image class="icon_img" src="@/static/message.png" @click="message(userInfo.id)"></image>
        私信
      </button>
    </uni-card>

    <view class="video-container">
      <view v-for="(video, index) in userInfo.videoList" :key="index" class="video-item">
        <view class="video-cover-container">
          <!-- 显示视频封面 -->
          <image :src="video.cover" @click="playVideo(video.id)" class="video-cover"></image>
          <!-- 显示点赞量 -->
          <view class="like-count">
            <image class="icon_img" src="@/static/likes.png"></image>
            <text>{{ video.likes }}</text>
          </view>
        </view>
      </view>
    </view>

  </view>
</template>

<script>
import {useMemberStore} from '@/stores'
import {getUserInfoById} from "@/service/UserApi";
import BlankNavbar from "@/pages/index/components/BlankNavbar.vue";

export default {
  components: {BlankNavbar},
  data() {
    return {
      memberStore: useMemberStore(),
      userInfo: null,// 用于存放获取到的用户数据
    }
  },
  methods: {
    // 异步请求获取用户信息
    async getUser() {
      const memberStore = useMemberStore()
      const res = await getUserInfoById(memberStore.profile?.id)
      console.log('请求成功：', res.data)
      this.userInfo = res.data
      console.log('请求成功：userinfo', this.userInfo)
    },

    showLikes() {
      uni.showModal({
        confirmText: '确定',
        title: '\"' + this.userInfo.nickname + '\"共获' + this.userInfo.likes + '个赞',
        showCancel: false,
      })
    },
    // TODO: 播放视频
    playVideo(videoId) {
      console.log('播放视频', videoId)
      // 根据视频 ID 进行播放操作
      // 可根据实际需求调用播放视频的函数或跳转至视频播放页面等操作

    },

    //   TODO: 展示相关信息(需要再写一个页面，进行跳转）
    showFriends(id) {

    },
    showFollowing(id) {

    },
    showFollower(id) {

    },

    //   TODO: 关注和取关
    follow(id) {

    },
    unfollow(id) {

    },

    // TODO: 私信(需要先写一个聊天页面，进行跳转）
    message(id) {

    }

  },
  onShow() {
    console.log('进来了哦')
    // 在页面加载时进行条件判断
    if (this.memberStore.profile == null) {
      uni.redirectTo({
        url: '/pages/my/login/login' // 跳转到登陆页面
      });
    }
    this.userInfo = this.getUser()
  },
}

</script>

<style>
.icon_img {
  width: 28rpx;
  height: 28rpx;
  vertical-align: middle; /* 设置垂直对齐方式为中间对齐 */
}

.number {
  font-weight: bold;
}

.button {
  font-size: 20rpx;
  width: 150rpx;
  height: 40rpx;
  display: inline-block;
  vertical-align: middle; /* 设置垂直对齐方式为中间对齐 */
}

/* 使用 flex 布局实现每行三个视频封面 */
.video-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  /* 可以添加其他样式，如边距等 */
}

.video-item {
  width: calc(33.33% - 10px); /* 每行显示三个视频，考虑边距，这里设置为占据三分之一的宽度 */
  margin-bottom: 10px; /* 设置视频项之间的下边距 */
  /* 可以添加其他样式，如边框、内边距等 */
}

/* TODO: 配合后端限制视频封面宽高比，让图片正常显示 */
.video-cover {
  width: 100%; /* 视频封面图片宽度填充 */
  height: 360rpx; /* 高度自适应 */
  /* 可以添加其他样式，如圆角等 */
}

.video-cover-container {
  position: relative; /* 设置相对定位 */
  display: inline-block; /* 行内块级元素 */
  width: 100%;
  height: 100%;
}

.like-count {
  position: absolute; /* 设置绝对定位 */
  bottom: 5px; /* 距离底部 5px */
  left: 5px; /* 距离左侧 5px */
  color: rgba(255, 255, 255, 0.75); /* 文字颜色 */
  padding: 5px; /* 内边距 */
  font-size: 23rpx; /* 文字大小 */
  display: flex; /* 使用 flex 布局 */
}

</style>
