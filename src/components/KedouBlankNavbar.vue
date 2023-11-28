<script setup lang="ts">
	// 获取屏幕边界到安全区域的距离
	const { safeAreaInsets } = uni.getSystemInfoSync()
</script>

<template>
	<view class="navbar" :style="{ paddingTop: safeAreaInsets?.top + 'px' }">
	</view>
</template>

<style lang="scss">
	/* 自定义导航条 */
	.navbar {
		// background-image: url(@/static/images/navigator_bg.png);
		// background-size: cover;
		position: relative;
		display: flex;
		flex-direction: column;
		margin-bottom: 0%;
		padding-bottom: 0%;
		bottom: 0%;
		background: white;
		height: 20%;
	}
</style>