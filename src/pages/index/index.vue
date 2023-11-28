<template>

    <view class="content">
      <!--      <CustomNavbar />-->
      <KedouSwiper class="kedou-swiper-component" ref="tab0" v-show="activeIndex === 0"/>
      <friend ref="tab1" v-show="activeIndex === 1"/>
      <message ref="tab3" v-show="activeIndex === 3"/>
      <my ref="tab4" v-show="activeIndex === 4"/>
    </view>


    <!--        <KedouTabbar ref="kedouTabbarRef" :activeIndex="0" class="custom-tab-bar"></KedouTabbar>-->
  <view class="tab-bar">
    <view v-for="(tab, index) in tabs" :key="index" @click="switchTab(index)" class="tar-bar-text">
      <image v-if="index === 2" :src="tab.iconPath" class="center-icon"/>
      <text v-else :class="{'active': activeIndex === index}">{{ tab.text }}</text>
      <view v-if="index === 3 && showBadge" class="badge">{{ unreadCount }}</view>
    </view>
  </view>

</template>

<script>
import CustomNavbar from './components/CustomNavbar.vue'
import friend from "@/pages/friend/friend.vue";
import message from "@/pages/message/message.vue";
import my from "@/pages/my/my.vue";

export default {
  data() {
    return {
      isActivePage: false, // 标记当前页面是否是活跃页面
      activeIndex: 0,
      tabs: [
        {text: '首页'},
        {text: '朋友'},
        {iconPath: '/static/add.png', selectedIconPath: '/static/add.png'},
        {text: '消息'},
        {text: '我'}
      ],
      showBadge: false,
      unreadCount: 0,
    };
  },
  components: {
    CustomNavbar,
    friend,
    message,
    my
  },
  // onTabItemTap(item) {
  //   if (this.isActivePage){
  //     // 重新获取数据或清空页面数据
  //     console.log('当前页面的 TabBar 被点击了，执行刷新操作');
  //     // 通过改变 reloadKey 的值来触发 KedouSwiper 组件的重新加载
  //     this.$refs.kedouSwiperRef.reloadComponent()
  //   }
  // },
  // onShow() {
  //   setTimeout(() => {
  //     this.isActivePage = true; // 略微延迟，确保准确获取到状态
  //   }, 100); // 当页面显示时，将 isActivePage 设置为 true
  // },
  // onHide() {
  //   this.isActivePage = false; // 当页面隐藏时，将 isActivePage 设置为 false
  // },
  watch: {
    // 监听子组件显示属性的变化
    activeIndex(newValue, oldValue) {
      console.log('newValue', newValue)
      console.log('oldValue', oldValue)
      switch (newValue) {
        case 1:
          this.$refs.tab1.showByTab();
          break;
        case 3:
          this.$refs.tab3.showByTab();
          break;
        case 4:
          this.$refs.tab4.showByTab();
          break;
        default:
          break;
      }

      // if (newValue !== oldValue) {
      //   // 在子组件显示时执行的操作
      //   this.handleFriendShowEvent();
    }
  },
  methods: {
    switchTab(index) {
      console.log('switchTab' + this.activeIndex);
      if (index === this.activeIndex) {
        console.log('switchTab重复点击', this.activeIndex);
        if (index === 0) {
          // 如果当前点击的是已经首页，则执行刷新操作
          console.log('执行刷新操作');
        }
        // 更新未读消息数
        this.showBadge = false; // 将小红点隐藏
        this.unreadCount = 0; // 未读消息数归零
      } else {
        // 根据 tarbar 索引来判断点击了哪个页面
        if (index === 2) {
          uni.navigateTo({
            url: '/pages/publish/publish' // 跳转到 publish 页面
          });
        }else{
          this.activeIndex = index
        }
      }
    }
  },
  onLoad(options) {
    // console.log(options)
    // 在页面加载时获取传递的参数
    if (options.activeIndex) {
      this.startIndex = options.activeIndex
    }
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
  height: 60px;
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
  margin: 0 10px; /* 设置元素间的间距 */
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


</style>