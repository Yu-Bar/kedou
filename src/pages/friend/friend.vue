<template>

    <KedouBlankNavbar/>
   <view class="friend-style">
    <view class="ttt">
      friend
      <!--    <KedouTabbar :activeIndex="1" class="custom-tab-bar"></KedouTabbar>-->
    </view>
  </view>

</template>

<script>
export default {
  data() {
    return {}
  },
  methods: {
    // 对应的底部 tab 被选中时执行
    showByTab() {

    }
  },
  onShow() {
    console.log('页面显示时执行的逻辑');
    // 在页面显示时执行的逻辑
  },
  onHide() {
    console.log('页面隐藏时执行的逻辑');
    // 在页面隐藏时执行的逻辑
  }
}
</script>

<style>
.friend-style {
  background: white;
  height: 100vh;
}
</style>
