<template>
  <image src="/static/goback.png" @click="goBack" class="goback-icon">后退</image>
  <view class="container" :style="{ height: screenHeight + 'px', display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center' }">
    <view class="logo">
      <image
          src="@/static/logo.png"
      ></image>
    </view>
    <view class="input-container">
      <view class="input-row">
        <text class="label">账号：kedou_</text>
        <input v-model="account" type="text" placeholder="请输入kedou号" class="input-field" />
      </view>
      <view class="input-row">
        <text class="label">密码：</text>
        <input v-model="password" type="password" placeholder="请输入密码" class="input-field" />
      </view>
    </view>
    <button v-if="sign" @click="signIn" class="login-btn">注册</button>
    <button v-else @click="login" class="login-btn">登录</button>
    <text v-if="!sign" @click="transToSign">注册账号</text>
  </view>
</template>

<script>
import {useMemberStore} from "@/stores";
import {postLoginPassWordAPI, signPassWordAPI} from "@/service/UserApi";

export default {
  data() {
    return {
      account: '',
      password: '',
      screenHeight: uni.getSystemInfoSync().windowHeight, // 获取屏幕高度
      sign: false,
    };
  },
  methods: {
    async login() {
      console.log('账号',this.account)
      console.log('密码',this.password)
      const res = await postLoginPassWordAPI({
        account: 'kedou_' + this.account,
        password: this.password
      })
      if(res.code == 1){
        // console.log(res)
        const memberStore = useMemberStore()
        memberStore.setProfile(res.data)
        await uni.showToast({
          icon: 'none',
          title: `欢迎回到科抖，${res.data.nickname}`
        })
        uni.navigateBack({
          delta: 2, // 关闭当前页面，如果是关闭上两个页面可以传入2
          success: function () {
            // 关闭成功的回调
            uni.navigateTo({
              url: '/pages/index/index?activeIndex=0'
            })
          }
        });
      }else{
          await uni.showToast({
            title: res.msg,
            icon: 'none'
          });
      }
    },
    // 注册
    async signIn() {
      const res = await signPassWordAPI({
        account: 'kedou_' + this.account,
        password: this.password
      })
      if(res.code == 1){
        // console.log(res)
        const memberStore = useMemberStore()
        memberStore.setProfile(res.data)
        await uni.showToast({
          icon: 'none',
          title: `欢迎来到科抖，${res.data.nickname}`
        })
        await uni.navigateBack({
          delta: 2, // 关闭当前页面，如果是关闭上两个页面可以传入2
          success: function () {
            // 关闭成功的回调
            uni.navigateTo({
              url: '/pages/index/index?activeIndex=0'
            })
          }
        });
      }else{
        await uni.showToast({
          title: res.msg,
          icon: 'none'
        });
      }
    },
    goBack() {
      // 返回主页
      uni.redirectTo({
        url: '/pages/index/index?activeIndex=0' // 跳转到首页页面
      });
    },
    transToSign(){
      this.sign =true;
    }
  }
};
</script>

<style>
.container {
  width: 100%;
  background-color: #1f1f1f;
  flex-direction: column;
  color: #ffffff;
}

.logo {
  margin-bottom: 150rpx; /* 调整 Logo 与输入框之间的间距 */
}

image {
  width: 220rpx;
  height: 220rpx;
  margin-top: 15vh;
}

.input-container {
  display: flex;
  flex-direction: column;
  margin-bottom: 100rpx;
}

.input-row {
  display: flex;
  align-items: center;
  margin-bottom: 10rpx;
}

.label {
  padding-right: 10rpx;
}

.input-field {
  padding: 10rpx;
  color: #ffffff;
}

.login-btn {
  width: 60%;
  padding: 30rpx;
  font-size: 30rpx;
  color: white;
  border: none;
  border-radius: 20rpx;
  background-color: #00fad9;
  background-image: linear-gradient(192deg, #00fad9 0%, #8EC5FC 50%, #E0C3FC 100%);
  margin-bottom: 400rpx;
}

.goback-icon {
  position: fixed;
  top: 10rpx; /* 距离顶部的距离 */
  left: 10rpx; /* 距离左侧的距离 */
  width: 28px; /* 图片宽度 */
  height: 28px; /* 图片高度 */
  z-index: 9999; /* 图片层级 */
  opacity: 80%;
}
</style>