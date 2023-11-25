<script setup lang="ts">
	// 获取屏幕边界到安全区域的距离
	const { safeAreaInsets } = uni.getSystemInfoSync()
</script>

<template>
	<view class="navbar" :style="{ paddingTop: safeAreaInsets?.top + 'px' }">
		<!-- 搜索条 -->
		<view class="search">
			<view class="logo">
				<image class="logo-image" src="@/static/logo.png"></image>
			</view>
			<text class="icon-search">搜索</text>
			<text class="icon-scan"></text>
		</view>
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
		padding-top: 20px;
		margin-bottom: 0%;
		padding-bottom: 0%;
		bottom: 0%;
		background: none;
		height: 20%;

		.logo {
			display: flex;
			align-items: center;
			height: 40rpx;
			padding-left: 10rpx;
			padding-top: 20rpx;
			background: none;

			.logo-image {
				width: 50rpx;
				height: 50rpx;
				margin-bottom: 15rpx;
			}
		}

		.search {
			display: flex;
			align-items: center;
			justify-content: space-between;
			// padding: 0 10rpx 0 26rpx;
			height: 64rpx;
			margin: 0rpx 5%;
			color: #fff;
			font-size: 28rpx;
			border-radius: 32rpx;
			background-color: rgba(255, 255, 255, 0.5);
		}

		.icon-search {
			&::before {
				margin-right: 10rpx;
			}
		}

		.icon-scan {
			font-size: 30rpx;
			padding: 15rpx;
		}
	}
</style>