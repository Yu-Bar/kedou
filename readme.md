# "科抖"短视频平台项目

> Author: [Yu-bar](https://github.com/Yu-Bar) 
>
> Date: 2023.11.24

## 1 介绍

"科抖"是一款短视频平台应用，USTC自己的抖音！

### 1.1 技术选型

- 前端：uni-app + vue3 + uni-ui
- 后端：JDK17 + SpringBoot3 \+ MySql + Redis + RabbitMQ + 阿里云OSS



## 2 使用说明

- 前端：
- 后端：



## 3 架构设计

<img src=".\doc\img\架构设计.png" alt="架构设计" style="zoom: 33%;" />



## 4 模块设计

### 4.0 通用接口

- [x] 上传视频、封面和头像 ：需要一个文件上传接口

  >path: '/common/upload'
  >
  >method: POST
  >
  >Body: MultipartFile

  

### 4.1 视频模块

BaseUrl : "/user/video"

- [x] 投稿视频 ：用mysql记录视频相关信息，OSS存储视频内容

  >path: '/upload'
  >
  >method: POST
  >
  >Body: 

- [x] 观看视频：==无需登陆，但是登陆了也要传递用户信息，用于检查用户对视频的喜欢和收藏情况== 

  - 个人主页下观看：需要指定用户ID，按**时间倒序**排列(在查询用户时，一并查询)
  - 首页观看：无需指定用户ID，按**时间倒序**排列（分页查询）
  - 还需要查询作者头像和作者昵称来展示：**所以在video表中冗余一条数据来保存作者头像url和作者昵称，但是在用户修改头像和昵称时需要注意要同时修改其发布视频的作者头像url** 

  >path: '/list'
  >
  >method: GET

- [ ] 查看个人私密视频

  > path: '/privacy'
  >
  > method: GET
  
- [ ] 智能推送

  ...待设计



### 4.2 评论模块

BaseUrl : "/user/list/comment"

- [x] 查看评论：传入视频ID，返回评论列表 ==无需登陆== 

  >path: '/{videoId}'
  >
  >method: GET

  缓存结构：

  >String ttl: -1
  >
  >key: comment::视频ID
  >
  >value: 评论列表

- [x] 提交评论：传入视频ID和评论内容

  ==同时需要修改视频的评论数量以及刷新评论缓存== 

  >path: '/commit'
  >
  >method: POST
  >
  >Body: 
  
- [ ] 删除评论：传入评论ID

  ==前后端同时都要校验这条评论是否是用户所发or是否是视频创作者的视频下的评论== 

  >path: '/delete/{commentId}'
  >
  >method: DELETE
  >

### 4.3 用户模块

BaseUrl : "/user/user"

- [x] 用户登陆（密码登录）：传入账号密码，返回用户对象

  >path: '/login'
  >
  >method: POST
  >
  >Body: 

- [x] 获取用户信息：传入用户ID，返回用户对象 ==无需登陆（查看自己的需要登陆）== 

  >path: '/{userId}'
  >
  >method: GET

- [x] 注册：传入账号密码，返回用户对象

  >path: '/sign'
  >
  >method: POST
  >
  >Body: 

- [ ] 设置个人信息：传入用户信息，修改用户信息

  >path: '/update'
  >
  >method: PUT
  >
  >Body: 

### 4.4 关系模块

BaseUrl : "/user/relation"

- [x] 查看关注列表：传入用户ID，返回关注列表

  ==需要校验是否为本人 or 关注列表是否公开== 

  >path: '/following/{userId}'
  >
  >method: GET

  缓存结构：

  >Set ttl: -1
  >
  >key: following::用户ID
  >
  >value: 用户关注集合

- [x] 查看粉丝列表：传入用户ID，返回粉丝列表

  ==需要校验是否为本人 or 粉丝列表是否公开== 

  >path: '/follower/{userId}'
  >
  >method: GET

  缓存结构：

  >Set ttl: -1
  >
  >key: follower::用户ID
  >
  >value: 用户粉丝集合

- [x] 查看朋友列表：返回朋友列表

  ==只能本人查看==  无需缓存，只需要求关注和粉丝的交集就可得到结果

  >path: '/friend/{userId}'
  >
  >method: GET

- [x] 添加关注：传入关注用户ID

  >path: '/follow/{userId}'
  >
  >method: POST

- [x] 取消关注：传入取消关注用户ID

  >path: '/follow/{userId}'
  >
  >method: DELETE

### 4.5 喜欢模块

BaseUrl : "/user/likes"

- [x] 查看喜欢列表：传入用户ID，返回喜欢列表 ==无需登陆== 

  ==需要校验是否为本人 or 喜欢列表是否公开== 

  > path: '/list'
  >
  > method: GET
  >
  > params: userId

- [x] 添加喜欢：传入视频ID

  ==需要更新视频表中的喜欢数量== 

  > path: '/'
  >
  > method: POST
  >
  > Params: videoId

- [x] 移除喜欢：传入视频ID

  ==需要更新视频表中的喜欢数量== 

  > path: '/'
  >
  > method: DELETE
  >
  > Params: videoId

### 4.6 收藏模块

BaseUrl : "/user/star"

- [x] 查看收藏列表：传入用户ID，返回收藏列表

  ==需要校验是否为本人 or 收藏列表是否公开== 

  > path: '/list'
  >
  > method: GET
  >
  > params: userId

- [x] 添加收藏：传入视频ID

  ==需要更新视频表中的收藏数量== 

  > path: '/'
  >
  > method: POST
  >
  > Params: videoId

- [x] 移除收藏：传入视频ID

  ==需要更新视频表中的收藏数量== 

  > path: '/'
  >
  > method: DELETE
  >
  > Params: videoId

### 4.7 消息模块

BaseUrl : "/user/message"

- [ ] 发送消息：传入接收用户ID和消息内容

  ==新消息通知，使用消息队列存储新消息，再使用websocket，当用户在线时进行转发== 

  > path: '/{userId}'
  >
  > method: POST
  >
  > Body:

- [ ] 获取消息：通过当前用户ID获取

  > path: '/'
  >
  > method: GET

### 4.8 分享模块

...待设计



## 5 数据库设计

<img src=".\doc\img\数据库设计.png" alt="数据库设计"  />



## 6 技巧总结

### 6.1 前端篇

#### 6.1.1 数据格式化

Q：如何实现下面这样的数据格式化？

<img src=".\doc\img\数据格式化.png" alt="数据格式化" style="float: left; zoom: 67%;" />

要在 Vue 中动态绑定数据并根据数值进行特定的显示转换，可以使用计算属性或者在模板中使用过滤器来实现这个功能。

```vue
<template>
  <div>
    <p>{{ formatNumber(number) }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      number: 121000
    };
  },
  computed: {
    formatNumber() {
      return (value) => {
        if (value > 9999) {
          return `${(value / 10000).toFixed(1)}万`;
        } else {
          return value.toString();
        }
      };
    }
  }
};
</script>
```

==注意：formatNumber这个函数式写在computed里面，而不是methods里面== 



### 6.2 后端篇


