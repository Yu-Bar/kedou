<template>
  <view>
    <uni-card title="基础卡片" sub-title="副标题" extra="额外信息"
      thumbnail="https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/unicloudlogo.png">
      <text>这是一个带头像和双标题的基础卡片，此示例展示了一个完整的卡片。</text>
    </uni-card>
  </view>
  <view class="my">
    <view>科抖信息：{{ memberStore.profile }}</view>
    <button @tap="
      memberStore.setProfile({
        nickname: '钰酱科抖',
        token: '123456'
      })
      " size="mini" plain type="primary">
      保存用户信息
    </button>
    <button @tap="memberStore.clearProfile()" size="mini" plain type="warn">清理用户信息</button>
    <button @tap="getData" size="mini" plain type="default">测试请求</button>
  </view>
</template>

<script lang="ts">
import { useMemberStore } from '@/stores'
import {http} from '@/utils/http'

export default {
  data() {
    return {
      memberStore: useMemberStore(),
    }
  },
  methods: {
    // 测试请求
    getData : async () => {
      const res = await http<AnyArray>({
        method:'GET',
        url:'/member/profile',
      })
      console.log('请求成功：' , res.result)
    }
  }
}
</script>

<style></style>
