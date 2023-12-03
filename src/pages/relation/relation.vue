<template>
  <KedouBlankNavbar/>
  <view class="tab-bar">
    <view v-for="(tab, index) in tabs" :key="index" @click="switchTab(index)" class="tar-bar-text">
      <text :class="{'active': activeIndex == index}">
        {{ tab.text }}
      </text>
    </view>
  </view>

	<view>
    <!-- 返回按钮 -->
    <image src="/static/dark_goback.png" @click="goBack" class="goback-icon">后退</image>

    <view v-if="activeIndex == 0" class="profile-list">
      <scroll-view scroll-y="true" class="scroll-Y">
        <view v-for="(profile, index) in profileSet" :key="index" class="profile-item">
          <uni-card @click="getIntoUserSpace(profile.id)" :title="profile.nickname" :isFull="true" :sub-title="profile.bio"
                    :thumbnail="profile.profile" class="custom-card">
            <button type="default" class="profile-text" @click="message(profile.id)">私信</button>
          </uni-card>
        </view>
      </scroll-view>
    </view>

    <view v-if="activeIndex == 1" class="profile-list">
      <scroll-view scroll-y="true" class="scroll-Y">
        <view v-for="(profile, index) in profileSet" :key="index" class="profile-item">
          <uni-card @click="getIntoUserSpace(profile.id)" :title="profile.nickname" :isFull="true" :sub-title="profile.bio"
                    :thumbnail="profile.profile" class="custom-card">
            <button v-if="profile.relation == 3 && memberStore.profile?.id != profile.id" type="default" class="profile-text" @click="unfollow(profile.id,index)">互相关注</button>
            <button v-else-if="profile.relation == 1 && memberStore.profile?.id != profile.id" type="default" class="profile-text" @click="unfollow(profile.id,index)">已关注</button>
            <button  v-else-if="profile.relation == 2 && memberStore.profile?.id != profile.id" type="warn" class="profile-text" @click="follow(profile.id,index)">回关</button>
            <button  v-else-if="memberStore.profile?.id != profile.id" type="warn" class="profile-text" @click="follow(profile.id,index)">关注</button>
          </uni-card>
        </view>
      </scroll-view>
    </view>

    <view v-if="activeIndex == 2" class="profile-list">
      <scroll-view scroll-y="true" class="scroll-Y">
        <view v-for="(profile, index) in profileSet" :key="index" class="profile-item">
          <uni-card @click="getIntoUserSpace(profile.id)" :title="profile.nickname" :isFull="true" :sub-title="profile.bio"
                    :thumbnail="profile.profile" class="custom-card">
            <button v-if="profile.relation == 3 && memberStore.profile?.id != profile.id" type="default" class="profile-text" @click="unfollow(profile.id,index)">互相关注</button>
            <button v-else-if="profile.relation == 1 && memberStore.profile?.id != profile.id" type="default" class="profile-text" @click="unfollow(profile.id,index)">已关注</button>
            <button  v-else-if="profile.relation == 2 && memberStore.profile?.id != profile.id" type="warn" class="profile-text" @click="follow(profile.id,index)">回关</button>
            <button  v-else-if="memberStore.profile?.id != profile.id" type="warn" class="profile-text" @click="follow(profile.id,index)">关注</button>
          </uni-card>
        </view>
      </scroll-view>
    </view>
	</view>
</template>

<script>
import {
  followUserById,
  getFollowerSetById,
  getFollowingSetById,
  getFriendSetById,
  unfollowUserById
} from "@/service/RelationApi";
import {useMemberStore} from "@/stores";

export default {
		data() {
			return {
				profileSet: [],
        isActivePage: false, // 标记当前页面是否是活跃页面
        activeIndex: 0,
        tabs: [
          {text: '朋友'},
          {text: '关注'},
          {text: '粉丝'}
        ],
        userId: null,
        memberStore: useMemberStore(),
			}
		},
		methods: {
      goBack() {
        // 返回上一个页面
        uni.navigateBack();
      },
      async showFriends(index) {
        const res = await getFriendSetById(this.userId)
        if(res.code == 1){
          this.profileSet = res.data
          this.activeIndex = index
        }else{
          await uni.showToast({
            title: res.msg,
            icon: 'none'
          })
        }
      },
      async showFollowing(index) {
        const res = await getFollowingSetById(this.userId)
        if(res.code == 1){
          this.profileSet = res.data
          this.activeIndex = index
        }else{
          await uni.showToast({
            title: res.msg,
            icon: 'none'
          })
        }
      },
      async showFollower(index) {
        const res = await getFollowerSetById(this.userId)
        if(res.code == 1){
          this.profileSet = res.data
          this.activeIndex = index
        }else{
          await uni.showToast({
            title: res.msg,
            icon: 'none'
          })
        }
      },
      async follow(id,index) {
        const res = await followUserById(id)
        if(res.code == 1){
          this.profileSet[index].relation++
        }
        else{
          await uni.showToast({
            title: res.msg,
            icon: 'error'
          })
        }
      },
      async unfollow(id,index) {
        const res = await unfollowUserById(id)
        if(res.code == 1){
          this.profileSet[index].relation--
        }
        else{
          await uni.showToast({
            title: res.msg,
            icon: 'error'
          })
        }
      },
      switchTab(index) {
        switch (index){
          case 0:
            this.showFriends(index);
            break;
          case 1:
            this.showFollowing(index);
            break;
          case 2:
            this.showFollower(index);
            break;
        }
      },
      // 进入用户主页
      getIntoUserSpace(userId) {
        console.log('进入用户主页', userId)
        uni.navigateTo({
          url: `/pages/userspace/userspace?user=${userId}`
        })
      },
		},
    onLoad(options) {
      // 在页面加载时获取传递的参数
      if (options.active) {
        this.profileSet = JSON.parse(decodeURIComponent(options.profileSet))
        this.activeIndex = options.active
        this.userId = options.user
      }
    },
	}
</script>

<style>
/* 顶部导航栏样式 */
.tab-bar {
  left: 0;
  right: 0;
  margin-top: 20rpx;
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: space-around; /* 在容器内平均分配元素 */
  align-items: center;
  box-shadow: 0 -2px 6px rgba(0, 0, 0, 0.1);
  color: #999999;
  z-index: 9999;
}

.tab-bar > view {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  flex: 1; /* 让元素等分容器宽度 */
  margin: 0 10px; /* 设置元素间的间距 */
  text-align: center; /* 文字居中 */
  margin-bottom: 20rpx;
}

.active {
  color: #000000; /* 选中时的颜色 */
  font-size: 36rpx;
}

.tar-bar-text {
  color: #999999;
  font-size: 35rpx;
}


.goback-icon {
  position: fixed;
  top: 30rpx; /* 距离顶部的距离 */
  left: 10rpx; /* 距离左侧的距离 */
  width: 28px; /* 图片宽度 */
  height: 28px; /* 图片高度 */
  z-index: 9999; /* 图片层级 */
  opacity: 80%;
}

.profile-list {
  position: relative;
  background: none;
}

.profile-item {
  background: none;
  color: black;
}

.profile-text {
  float: right;
  height: 55rpx;
  width: 200rpx;
  font-size: 28rpx;
  margin-bottom: 10rpx;
}

/* 覆盖 uni-card 默认样式 */
.custom-card {
  background-color: transparent;
  /* 其他样式 */
}
</style>
