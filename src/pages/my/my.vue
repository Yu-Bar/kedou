<template>
  <KedouBlankNavbar/>
  <view class="my-style">
    <view v-if="userInfo">
      <uni-card :title="userInfo.nickname" :isFull="true" :sub-title="userInfo.username" :extra="userInfo.bio"
                :thumbnail="userInfo.profile">
        <text class="number" @click="showLikes(userInfo.id)">{{ userInfo.likes }}</text>
        获赞 |
        <text class="number" @click="showFriends(userInfo.id)">{{ userInfo.friends }}</text>
        朋友 |
        <text class="number" @click="showFollowing(userInfo.id)">{{ userInfo.following }}</text>
        关注 |
        <text class="number" @click="showFollower(userInfo.id)">{{ userInfo.follower }}</text>
        粉丝 |
        <view style="display: inline-block;float: right">
          <button class="button" @click="logout">
            <image class="icon_img" src="@/static/logout.png"></image>
            退出
          </button>
        </view>
      </uni-card>

      <uni-card :isFull="true">
        <view class="info-container">
          <view v-if="userInfo.address != null && userInfo.address != ''" class="info-item">
            <image class="icon_img" src="@/static/address.png"></image>
            {{ userInfo.address }} |
          </view>
          <view v-if="userInfo.school != null && userInfo.school != ''" class="info-item">
            <image class="icon_img" src="@/static/school.png"></image>
            {{ userInfo.school }} |
          </view>
          <view v-if="userInfo.sex != null && userInfo.sex != ''" class="info-item">
            <image v-if="userInfo.sex===1" class="icon_img" src="@/static/male.png"></image>
            <image v-else class="icon_img" src="@/static/female.png"></image>
            |
          </view>
          <button class="button" @click="setInfo">
            <image class="icon_img" src="@/static/setting.png"></image>
            设置
          </button>
        </view>
      </uni-card>


      <view class="nav-bar">
        <view v-for="(title, index) in navTitles"
              :key="index"
              class="nav-item"
              :class="{ 'active': currentTab === title }"
              @click="switchContent(title)">
          {{ title }}
        </view>
      </view>

      <scroll-view class="nav-content" scroll-y="true" v-show="currentTab === '作品'">
          <view class="video-container">
            <view v-for="(video, index) in userInfo.videoList" :key="index" class="video-item">
              <view class="video-cover-container">
                <!-- 显示视频封面 -->
                <image :src="video.cover" @click="playVideo(index)" class="video-cover"></image>
                <!-- 显示点赞量 -->
                <view class="like-count">
                  <image class="icon_img" src="@/static/likes.png"></image>
                  <text>{{ formatNumber(video.likes) }}</text>
                </view>
              </view>
            </view>
          </view>
        </scroll-view>

      <scroll-view class="nav-content" scroll-y="true" v-show="currentTab === '喜欢'">
        <view class="video-container">
          <view v-for="(video, index) in likeVideoList" :key="index" class="video-item">
            <view class="video-cover-container">
              <!-- 显示视频封面 -->
              <image :src="video.cover" @click="playLikeVideo(index)" class="video-cover"></image>
              <!-- 显示点赞量 -->
              <view class="like-count">
                <image class="icon_img" src="@/static/likes.png"></image>
                <text>{{ formatNumber(video.likes) }}</text>
              </view>
            </view>
          </view>
        </view>
      </scroll-view>

      <scroll-view class="nav-content" scroll-y="true" v-show="currentTab === '收藏'">
        <view class="video-container">
          <view v-for="(video, index) in starVideoList" :key="index" class="video-item">
            <view class="video-cover-container">
              <!-- 显示视频封面 -->
              <image :src="video.cover" @click="playStarVideo(index)" class="video-cover"></image>
              <!-- 显示点赞量 -->
              <view class="like-count">
                <image class="icon_img" src="@/static/likes.png"></image>
                <text>{{ formatNumber(video.likes) }}</text>
              </view>
            </view>
          </view>
        </view>
      </scroll-view>

      </view>

  </view>
  <!--  <KedouTabbar :activeIndex="4" class="custom-tab-bar"></KedouTabbar>-->
</template>

<script>
import {useMemberStore} from '@/stores'
import {getUserInfoById} from "@/service/UserApi";
import {getLikeVideoList, likeVideo} from "@/service/LikesApi";
import {getStarVideoList} from "@/service/StarsApi";
import {getFollowerSetById, getFollowingSetById, getFriendSetById} from "@/service/RelationApi";

export default {
  data() {
    return {
      memberStore: useMemberStore(),
      userInfo: null,// 用于存放获取到的用户数据
      currentTab: '作品',
      navTitles: ['作品', '私密', '收藏', '喜欢'],
      videoContainerHeight: 'calc(100vh - 260rpx)',
      likeVideoList: [],
      starVideoList: [],
      user: null,
    }
  },
  computed: {
    formatNumber() {
      return (value) => {
        if (value > 9999) {
          let formattedNumber = (value / 10000).toFixed(1); // 将数字格式化为指定位数的小数
          if (formattedNumber.endsWith('.0')) {
            formattedNumber = formattedNumber.slice(0, -2); // 去掉小数末尾的'.0'
          }
          return `${formattedNumber}万`
        } else {
          // console.log('format3')
          return value.toString();
        }
      };
    },
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
    async getLikesList() {
      const res = await getLikeVideoList(this.userInfo.id)
      if(res.code == 1)
        this.likeVideoList = res.data
      else {
        await uni.showToast({
          title: res.msg,
          icon: 'none'
        })
      }
    },
    async getStarList() {
      const res = await getStarVideoList(this.userInfo.id)
      if(res.code == 1)
        this.starVideoList = res.data
      else {
        await uni.showToast({
          title: res.msg,
          icon: 'none'
        })
      }
    },
    switchContent(title) {
      this.currentTab = title;
      // 查看喜欢列表
      if(title === '喜欢'){
       this.getLikesList()
      }
      // 查看收藏列表
      if(title === '收藏'){
        this.getStarList()
      }
    },
    showLikes() {
      uni.showModal({
        confirmText: '确定',
        title: '\"' + this.userInfo.nickname + '\"共获' + this.userInfo.likes + '个赞',
        showCancel: false,
      })
    },
    // 退出登陆
    logout() {
      console.log('退出按钮被点击');
      this.memberStore.clearProfile();
      console.log('用户数据已清除');
      uni.navigateTo({
        url: '/pages/my/login/login' // 跳转到其他页面
      });
    },
    // TODO： 设置用户信息
    setInfo() {
      console.log('设置')
    },

    // 根据视频 ID 进行播放操作
    playVideo(videoNum) {
      // 跳转至视频播放页面
      uni.navigateTo({
        url: `/components/KedouSwiper?videoList=${encodeURIComponent(JSON.stringify(this.userInfo.videoList))}&videoNum=${videoNum}`
      });
    },

    playLikeVideo(videoNum) {
      // 跳转至视频播放页面
      uni.navigateTo({
        url: `/components/KedouSwiper?videoList=${encodeURIComponent(JSON.stringify(this.likeVideoList))}&videoNum=${videoNum}`
      });
    },

    playStarVideo(videoNum) {
      // 跳转至视频播放页面
      uni.navigateTo({
        url: `/components/KedouSwiper?videoList=${encodeURIComponent(JSON.stringify(this.starVideoList))}&videoNum=${videoNum}`
      });
    },

    //  展示相关信息(需要再写一个页面，进行跳转）
    async showFriends(id) {
      const res = await getFriendSetById(id)
      if(res.code == 1){
        // 跳转至朋友列表
        await uni.navigateTo({
          url: `/pages/relation/relation?profileSet=${encodeURIComponent(JSON.stringify(res.data))}&active=0&user=${this.userInfo.id}`
        });
      }else{
        await uni.showToast({
          title: res.msg,
          icon: 'none'
        })
      }
    },

    async showFollowing(id) {
      const res = await getFollowingSetById(id)
      if(res.code == 1){
        // 跳转至朋友列表
        await uni.navigateTo({
          url: `/pages/relation/relation?profileSet=${encodeURIComponent(JSON.stringify(res.data))}&active=1&user=${this.userInfo.id}`
        });
      }else{
        await uni.showToast({
          title: res.msg,
          icon: 'none'
        })
      }
    },

    async showFollower(id) {
      const res = await getFollowerSetById(id)
      if(res.code == 1){
        // 跳转至朋友列表
        await uni.navigateTo({
          url: `/pages/relation/relation?profileSet=${encodeURIComponent(JSON.stringify(res.data))}&active=2&user=${this.userInfo.id}`
        });
      }else{
        await uni.showToast({
          title: res.msg,
          icon: 'none'
        })
      }
    },

    // 对应的底部 tab 被选中时执行
    showByTab() {
      console.log('进来了哦')
      // 在页面加载时进行条件判断
      if (this.memberStore.profile == null) {
        uni.redirectTo({
          url: '/pages/my/login/login' // 跳转到登陆页面
        });
      }else{
        if(this.userInfo === null)
          this.getUser()
        this.reloadInfo()
        console.log('监听onReloadMyInfo')
        // 监听自定义事件 onBackPress
        uni.$on('onReloadMyInfo', this.reloadInfo);
      }
    },
    // 返回页面时重新加载信息
    reloadInfo() {
      console.log('重新加载用户信息')
      if(this.currentTab == '喜欢')
        this.getLikesList()
      if(this.currentTab == '收藏')
        this.getStarList()
    }
  },
  onUnload() {
    console.log('取消监听onReloadMyInfo')
    // 取消监听自定义事件 onBackPress
    uni.$off('onReloadMyInfo', this.reloadInfo);
  },
}

</script>

<style>
.my-style {
  background: white;
  height: calc(100vh - 100rpx); /* Adjust height to accommodate the navbar */
}

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
//justify-content: space-between; justify-content: flex-start;
  /* 可以添加其他样式，如边距等 */
}

.video-item {
  width: calc(33.33% - 10rpx); /* 每行显示三个视频，考虑边距，这里设置为占据三分之一的宽度 */
  /* margin-bottom: 1rpx;  设置视频项之间的下边距 */
  /* 可以添加其他样式，如边框、内边距等 */
}


/* TODO: 配合后端限制视频封面宽高比，让图片正常显示 */
.video-cover {
  width: 100%; /* 视频封面图片宽度填充 */
  height: 360rpx; /* 高度自适应 */
  /* 可以添加其他样式，如圆角等 */
  margin-left: 15rpx;
}

.video-cover-container {
  position: relative; /* 设置相对定位 */
  display: inline-block; /* 行内块级元素 */
  width: 100%;
  height: 100%;
}

.like-count {
  position: absolute; /* 设置绝对定位 */
  bottom: 10rpx; /* 距离底部 5px */
  left: 10rpx; /* 距离左侧 5px */
  color: rgba(255, 255, 255, 0.75); /* 文字颜色 */
  padding: 10rpx; /* 内边距 */
  font-size: 23rpx; /* 文字大小 */
  display: flex; /* 使用 flex 布局 */
}

.info-container {
  display: flex;
  flex-wrap: nowrap; /* 防止换行 */
}

.info-item {
  display: flex;
  align-items: center;
}


/* 导航栏相关样式 */
.nav-bar {
  display: flex;
  justify-content: space-around;
  align-items: center;
  background-color: #f0f0f0;
  padding: 5rpx 0;
}

.nav-item {
  cursor: pointer;
  padding: 8px 16px;
  border-bottom: 2px solid transparent;
  transition: border-color 0.3s;
  transition: color 0.3s;
  color: #767676;
}

.nav-item.active {
  border-color: #16151a;
  color: #1a1a1c;
}

.nav-content {
  max-height: 63vh; /* Adjust the maximum height for scroll view */
}

</style>
