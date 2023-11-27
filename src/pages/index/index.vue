<template>
	<view>
		<CustomNavbar />
		<KedouSwiper ref="kedouSwiperRef" />
	</view>
</template>

<script>
	import CustomNavbar from './components/CustomNavbar.vue'
	export default {
    data() {
      return {
        isActivePage: false, // 标记当前页面是否是活跃页面
      };
    },
		components: {
			CustomNavbar,
		},
    onTabItemTap(item) {
      if (this.isActivePage){
        // 重新获取数据或清空页面数据
        console.log('当前页面的 TabBar 被点击了，执行刷新操作');
        // 通过改变 reloadKey 的值来触发 KedouSwiper 组件的重新加载
        this.$refs.kedouSwiperRef.reloadComponent()
      }
    },
    onShow() {
      setTimeout(() => {
        this.isActivePage = true; // 略微延迟，确保准确获取到状态
      }, 100); // 当页面显示时，将 isActivePage 设置为 true
    },
    onHide() {
      this.isActivePage = false; // 当页面隐藏时，将 isActivePage 设置为 false
    },
	}
</script>

<style>
	page {
		height: 100%;
		background-color: #000000;
	}

	view {
		color: #ffffff;
	}
</style>