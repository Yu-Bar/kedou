<!--<template>-->
<!--  <view class="tab-bar">-->
<!--    <view v-for="(tab, index) in tabs" :key="index" @click="switchTab(index)" class="tar-bar-text">-->
<!--      <image v-if="index === 2" :src="tab.iconPath" class="center-icon" />-->
<!--      <text v-else :class="{'active': activeIndex === index}">{{ tab.text }}</text>-->
<!--      <view v-if="index === 3 && showBadge" class="badge">{{ unreadCount }}</view>-->
<!--    </view>-->
<!--  </view>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->
<!--  props: {-->
<!--    activeIndex: {-->
<!--      type: Number,-->
<!--      default: 0-->
<!--    }-->
<!--  },-->
<!--  data() {-->
<!--    return {-->
<!--      tabs: [-->
<!--        { text: '首页' },-->
<!--        { text: '朋友' },-->
<!--        { iconPath: '/static/add.png', selectedIconPath: '/static/add.png' },-->
<!--        { text: '消息' },-->
<!--        { text: '我' }-->
<!--      ],-->
<!--      showBadge: false,-->
<!--      unreadCount: 0,-->
<!--    };-->
<!--  },-->
<!--  methods: {-->
<!--    switchTab(index) {-->
<!--      console.log('switchTab'+this.activeIndex);-->
<!--      if (index === this.activeIndex) {-->
<!--        console.log('switchTab重复点击',this.activeIndex);-->
<!--        if(index === 0){-->
<!--          // 如果当前点击的是已经首页，则执行刷新操作-->
<!--          console.log('执行刷新操作');-->
<!--        }-->
<!--        // 更新未读消息数-->
<!--        this.showBadge = false; // 将小红点隐藏-->
<!--        this.unreadCount = 0; // 未读消息数归零-->
<!--      } else {-->
<!--        // 根据 tarbar 索引来判断点击了哪个页面-->
<!--        switch (index){-->
<!--          case 0:-->
<!--            uni.reLaunch({-->
<!--              url: '/pages/index/index' // 跳转到 index 页面-->
<!--            });-->
<!--            break;-->
<!--          case 1:-->
<!--            uni.reLaunch({-->
<!--              url: '/pages/friend/friend' // 跳转到 friend 页面-->
<!--            });-->
<!--            break;-->
<!--          case 2:-->
<!--            uni.reLaunch({-->
<!--              url: '/pages/publish/publish' // 跳转到 publish 页面-->
<!--            });-->
<!--            break;-->
<!--          case 3:-->
<!--            uni.reLaunch({-->
<!--              url: '/pages/message/message' // 跳转到 message 页面-->
<!--            });-->
<!--            break;-->
<!--          case 4:-->
<!--            uni.reLaunch({-->
<!--              url: '/pages/my/my' // 跳转到 my 页面-->
<!--            });-->
<!--            break;-->
<!--          default:-->
<!--            break;-->
<!--        }-->
<!--      }-->
<!--    },-->
<!--    onShow() {-->
<!--      console.log('当前activate',this.activeIndex)-->
<!--    },-->
<!--  }-->
<!--};-->
<!--</script>-->

<!--<style>-->
<!--/* 样式根据需求自行调整 */-->
<!--.tab-bar {-->
<!--  position: absolute;-->
<!--  left: 0;-->
<!--  right: 0;-->
<!--  bottom: 0;-->
<!--  width: 100%;-->
<!--  height: 50px;-->
<!--  background-color: #000000;-->
<!--  display: flex;-->
<!--  justify-content: space-around; /* 在容器内平均分配元素 */-->
<!--  align-items: center;-->
<!--  box-shadow: 0 -2px 6px rgba(0, 0, 0, 0.1);-->
<!--  color: #999999;-->
<!--  margin-bottom: 20rpx;-->
<!--}-->

<!--.tab-bar > view {-->
<!--  display: flex;-->
<!--  flex-direction: column;-->
<!--  align-items: center;-->
<!--  justify-content: center;-->
<!--  flex: 1; /* 让元素等分容器宽度 */-->
<!--  margin: 0 10px; /* 设置元素间的间距 */-->
<!--  text-align: center; /* 文字居中 */-->
<!--}-->

<!--.active {-->
<!--  color: #ffffff; /* 选中时的颜色 */-->
<!--  font-size: 36rpx;-->
<!--}-->

<!--.tar-bar-text {-->
<!--  color: #999999;-->
<!--  font-size: 35rpx;-->
<!--}-->

<!--.center-icon {-->
<!--  width: 55rpx;-->
<!--  height: 55rpx;-->
<!--  margin-bottom: 10rpx;-->
<!--}-->

<!--.badge {-->
<!--  position: absolute;-->
<!--  top: 8px;-->
<!--  right: 16px;-->
<!--  background-color: red;-->
<!--  color: white;-->
<!--  width: 20px;-->
<!--  height: 20px;-->
<!--  border-radius: 50%;-->
<!--  display: flex;-->
<!--  justify-content: center;-->
<!--  align-items: center;-->
<!--}-->
<!--</style>-->
