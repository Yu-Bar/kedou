// src/pages/login/login.vue

<script>
export default {
  methods: {
    loginWithPassword() {
      uni.redirectTo({
        url: '/pages/my/login/loginWithPassword' // 跳转到密码登陆页面
      });
    },
    goBack() {
      // 返回首页
      uni.redirectTo({
        url: '/pages/index/index?activeIndex=0' // 跳转到首页页面
      });
    },
  },
};
</script>

<template>
  <image src="/static/goback.png" @click="goBack" class="goback-icon" >后退</image>
  <view class="viewport">
    <view class="logo">
      <image
          src="@/static/logo.png"
      ></image>
    </view>
    <view class="login">
      <!-- 网页端表单登录 -->
      <!-- <input class="input" type="text" placeholder="请输入用户名/手机号码" /> -->
      <!-- <input class="input" type="text" password placeholder="请输入密码" /> -->
      <!-- <button class="button phone">登录</button> -->

      <!-- 小程序端授权登录 -->
      <button class="button phone">
        <text class="icon icon-phone"></text>
        手机号快捷登录
      </button>
      <view class="extra">
        <view class="caption">
          <text>其他登录方式</text>
        </view>
        <view class="options">
          <!-- 通用模拟登录 -->
          <button @tap="loginWithPassword">
            <text class="icon">密码登录</text>
          </button>
        </view>
      </view>
      <view class="tips">登录/注册即视为你同意《服务条款》和《科抖隐私协议》</view>
    </view>
  </view>
</template>

<style lang="scss">
page {
	height: 100%;
	background-color: #242424;
}

view {
	color: #ffffff;
}

.viewport {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 20rpx 40rpx;
}

.logo {
  flex: 1;
  text-align: center;

  image {
    width: 220rpx;
    height: 220rpx;
    margin-top: 15vh;
  }
}

.login {
  display: flex;
  flex-direction: column;
  height: 60vh;
  padding: 40rpx 20rpx 20rpx;

  .input {
    width: 100%;
    height: 80rpx;
    font-size: 28rpx;
    border-radius: 72rpx;
    border: 1px solid #ddd;
    padding-left: 30rpx;
    margin-bottom: 20rpx;
  }

  .button {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 80rpx;
    font-size: 28rpx;
    border-radius: 72rpx;
    color: #fff;

    .icon {
      color: #ffffff;
      font-size: 40rpx;
      margin-right: 6rpx;
    }
  }

  .phone {
    background-color: #00fad9;
    background-image: linear-gradient(192deg, #00fad9 0%, #8EC5FC 50%, #E0C3FC 100%);
  }

  .wechat {
    background-color: #06c05f;
  }

  .extra {
    flex: 1;
    padding: 70rpx 70rpx 0;
    align-content: center;

    .caption {
      width: 440rpx;
      line-height: 1;
      border-top: 5rpx solid rgba(221, 221, 221, 0.75);
      font-size: 26rpx;
      color: #000000;
      position: relative;

      text {
        transform: translate(-40%);
        width: 200rpx;
        height: 30rpx;
        text-align: center;
        border-radius: 10rpx;
        background-color: rgba(255, 255, 255, 0.91);
        position: absolute;
        top: -12rpx;
        left: 50%;
      }
    }

    .options {
      display: flex;
      justify-content: center;
      align-items: center;
      margin-top: 70rpx;

      button {
        padding: 0;
        background-color: transparent;
      }
    }

    .icon {
      font-size: 24rpx;
      display: flex;
      flex-direction: column;
      align-items: center;
      color: #fff;

      &::before {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 80rpx;
        height: 80rpx;
        margin-bottom: 6rpx;
        font-size: 40rpx;
        border: 1rpx solid #444;
        border-radius: 50%;
      }
    }

    .icon-weixin::before {
      border-color: #06c05f;
      color: #06c05f;
    }
  }
}

.tips {
  position: absolute;
  bottom: 80rpx;
  left: 20rpx;
  right: 20rpx;
  font-size: 22rpx;
  color: #ffffff;
  text-align: center;
}

.goback-icon {
  position: fixed;
  top: 70rpx; /* 距离顶部的距离 */
  left: 10rpx; /* 距离左侧的距离 */
  width: 28px; /* 图片宽度 */
  height: 28px; /* 图片高度 */
  z-index: 9999; /* 图片层级 */
  opacity: 80%;
}
</style>