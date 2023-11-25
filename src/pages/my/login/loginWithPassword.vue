<template>
  <view class="container">
    <input v-model="account" type="text" placeholder="请输入账号" />
    <input v-model="password" type="password" placeholder="请输入密码" />
    <button @click="login">登录</button>
  </view>
</template>

<script>
import {getVideoLists} from "@/service/VideoApi";
import {postLoginPassWordAPI} from "@/service/LoginApi";
import {useMemberStore} from "@/stores";

export default {
  data() {
    return {
      account: '',
      password: ''
    };
  },
  methods: {
    async login() {
      const res = await postLoginPassWordAPI({
        account: this.account,
        password: this.password
      })
      if(res.code == 1){
        // console.log(res)
        const memberStore = useMemberStore()
        memberStore.setProfile(res.data)
        uni.showToast({
          icon: 'none',
          title: '登陆成功'
        })
        uni.switchTab({
          url: '/pages/my/my' // 跳转到登录成功后的页面
        });
      }else{
          uni.showToast({
            title: '登录失败，请检查账号和密码',
            icon: 'none'
          });
      }
    }
  }
};
</script>

<style>
/* 样式可以根据需要进行调整 */
.container {
  margin: 20px;
}

input,
button {
  margin-bottom: 10px;
  width: 100%;
  padding: 10px;
}
</style>
