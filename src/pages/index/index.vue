<template>

  <view class="content">
    <!--      <CustomNavbar />-->
    <KedouSwiper :category="videoCategory[0]" class="kedou-swiper-component" ref="tab0" v-show="activeIndex == 0 && !reloadingVideo"/>
    <KedouSwiper :category="videoCategory[1]" class="kedou-swiper-component" ref="tab1" v-show="activeIndex == 1 && !reloadingVideo"/>
    <message ref="tab3" v-show="activeIndex == 3"/>
    <my ref="tab4" v-show="activeIndex == 4"/>
  </view>


  <!--        <KedouTabbar ref="kedouTabbarRef" :activeIndex="0" class="custom-tab-bar"></KedouTabbar>-->
  <view class="tab-bar">
    <view v-for="(tab, index) in tabs" :key="index" @click="switchTab(index)" class="tar-bar-text">

      <image v-if="index == 2" :src="tab.iconPath" class="center-icon"/>
      <image v-else-if="index === 0 && !isTextVisible" :src="tab.iconPath" class="reload-icon"/>
      <image v-else-if="index === 1 && !isTextVisible1" :src="tab.iconPath" class="reload-icon"/>
      <text v-else :class="{'active': activeIndex == index}">
<!--        {{ (index == 0 && !isTextVisible) || (index == 1 && !isTextVisible1) ? '' : tab.text }}-->
        {{tab.text}}
      </text>

      <view v-if="index == 3 && showBadge" class="badge">{{ unreadCount }}</view>
    </view>
  </view>

</template>

<script>
import CustomNavbar from './components/CustomNavbar.vue'
import message from "@/pages/message/message.vue";
import my from "@/pages/my/my.vue";
import {useMemberStore} from "@/stores";
import WebSocketService from "@/service/WebSocketService";

export default {
  data() {
    return {
      isActivePage: false, // 标记当前页面是否是活跃页面
      activeIndex: 0,
      videoCategory: [
        'index',
        'friend'
      ],
      tabs: [
        {text: '首页',iconPath: '/static/reload.png'},
        {text: '朋友',iconPath: '/static/reload.png'},
        {iconPath: '/static/add.png'},
        {text: '消息'},
        {text: '我'}
      ],
      showBadge: false,
      unreadCount: 0,
      memberStore: useMemberStore(),
      isTextVisible: true ,// 控制文字和图标的显示切换
      isTextVisible1: true ,// 控制文字和图标的显示切换
      reloadingVideo: false,
    };
  },
  components: {
    CustomNavbar,
    message,
    my
  },
  watch: {
    // 监听子组件显示属性的变化
    activeIndex(newValue, oldValue) {
      // console.log('newValue', newValue)
      // console.log('oldValue', oldValue)
      switch (newValue) {
        case 3:
          this.$refs.tab3.showByTab();
          break;
        case 4:
          this.$refs.tab4.showByTab();
          break;
        default:
          break;
      }

    }
  },
  methods: {
    switchTab(index) {
      console.log('switchTab' + this.activeIndex);
      if (index == this.activeIndex) {
        console.log('switchTab重复点击', this.activeIndex);
        if (index == 0) {
          // 如果当前点击的是已经首页，则执行刷新操作
          console.log('执行刷新操作')
          this.$refs.tab0.stopPlay()
          this.$refs.tab0.autoPlay = false
          this.reloadingVideo = true
          this.$refs.tab0.reloadComponent()
          this.playAnimation()
        }else if (index == 1) {
          // 如果当前点击的是已经首页，则执行刷新操作
          console.log('执行刷新操作')
          this.$refs.tab1.stopPlay()
          this.$refs.tab1.autoPlay = false
          this.reloadingVideo = true
          this.$refs.tab1.reloadComponent()
          this.playAnimation1()
        }
        // 更新未读消息数
        this.showBadge = false; // 将小红点隐藏
        this.unreadCount = 0; // 未读消息数归零
      } else {
        // 根据 tarbar 索引来判断点击了哪个页面
        if (index == 2) {
          if(this.memberStore.profile != null){
            uni.navigateTo({
              url: '/pages/publish/publish' // 跳转到 publish 页面
            });
          }
          else{
            uni.navigateTo({
              url: '/pages/my/login/login' // 跳转到 publish 页面
            });
          }
        }else{
          console.log('index',index)
          console.log('activeIndex',this.activeIndex)
          this.$refs.tab0.stopPlay()
          this.$refs.tab1.stopPlay()
          // 回到主页时继续播放视频 切出主页时停止播放视频
          if(index == 0){
            this.$refs.tab0.continuePlay()
          }else if(index == 1){
            this.$refs.tab1.continuePlay()
          }
          this.activeIndex = index
        }
      }
    },
    playAnimation() {
      // 图片转换为图标并播放动画
      this.isTextVisible = false
      setTimeout(() => {
        this.reloadingVideo = false
        this.$refs.tab0.continuePlay()
        this.$refs.tab0.autoPlay = true
        this.isTextVisible = true
      }, 500);
      // 设置定时器将图标切换回文字
      setTimeout(() => {
        this.isTextVisible = true
      }, 1200);
    },
    playAnimation1() {
      // 图片转换为图标并播放动画
      this.isTextVisible1 = false
      setTimeout(() => {
        this.reloadingVideo = false
        this.$refs.tab1.continuePlay()
        this.$refs.tab1.autoPlay = true
      }, 500);
      // 设置定时器将图标切换回文字
      setTimeout(() => {
        this.isTextVisible1 = true
      }, 1200);
    },
  },
  onLoad(options) {
    // console.log(options)
    // 在页面加载时获取传递的参数
    if (options.activeIndex) {
      this.startIndex = options.activeIndex
    }else{
      // 建立 WebSocket 连接，并传入当前用户的 ID
      const memberStore = useMemberStore()
      if(memberStore.profile != null){
        const webSocket = WebSocketService.getInstance()
        webSocket.connect(memberStore.profile?.id)
      }
    }
  },

  beforeDestroy() {
    // 在退出时关闭 WebSocket 连接
    const webSocket = WebSocketService.getInstance()
    webSocket.close()
  },

}
</script>

<style>
page {
  background: black;
}

/* 底部导航栏样式 */
.tab-bar {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 8vh;
  background-color: #000000;
  display: flex;
  justify-content: space-around; /* 在容器内平均分配元素 */
  align-items: center;
  box-shadow: 0 -2px 6px rgba(0, 0, 0, 0.1);
  color: #999999;
}

.tab-bar > view {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  flex: 1; /* 让元素等分容器宽度 */
  text-align: center; /* 文字居中 */
  margin-bottom: 20rpx;
}

.active {
  color: #ffffff; /* 选中时的颜色 */
  font-size: 36rpx;
}

.tar-bar-text {
  color: #999999;
  font-size: 35rpx;
}

.center-icon {
  width: 55rpx;
  height: 55rpx;
  margin-bottom: 10rpx;
}

.reload-icon {
  width: 55rpx;
  height: 55rpx;
  /* 添加旋转动画 */
  animation: rotateAnimation 1.5s linear 0s 1 normal;
}

.badge {
  position: absolute;
  top: 8px;
  right: 16px;
  background-color: red;
  color: white;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
}


@keyframes rotateAnimation {
  0% {
    transform: rotate(0);
  }
  100% {
    transform: rotate(1080deg); /* 绕中心旋转3圈 */
  }
}

</style>