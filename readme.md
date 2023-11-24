# "科抖"短视频平台项目

> Author: Yubar
>
> Date: 2023.11.24

USTC自己的抖音！

## 1 模块设计

### 1.1 视频模块

BaseUrl : "/user/video"

- [ ] 投稿视频 ：用mysql记录视频相关信息，OSS存储视频内容

  >path: '/upload'
  >
  >method: POST
  >
  >Body: 

- [ ] 观看视频：

  - 个人主页下观看：需要指定用户ID，按时间倒序排列

  - 首页观看：无需指定用户ID，按时间倒序排列

  >path: '/upload/{authorId}'
  >
  >method: GET

  

### 1.2 评论模块

BaseUrl : "/user/comment"

- [ ] 查看评论：传入视频ID，返回评论列表

    >path: '/{videoId}'
    >
    >method: GET
    >

- [ ] 提交评论：传入视频ID和评论内容

  ==同时需要修改视频的评论数量== 

  >path: '/upload/{videoId}'
  >
  >method: POST
  >
  >Body:

### 1.3 用户模块

BaseUrl : "/user/user"

- [x] 用户登陆（密码登录）：传入账号密码，返回用户对象

    >path: '/login'
    >
    >method: GET
    >
    >Body: 

- [x] 获取用户信息：传入用户ID，返回用户对象

  >path: '/{userId}'
  >
  >method: GET
  >


### 1.4 关系模块

BaseUrl : "/user/relation"

- [ ] 查看关注列表：传入用户ID，返回关注列表

  >path: '/following/{userId}'
  >
  >method: GET

- [ ] 查看粉丝列表：传入用户ID，返回粉丝列表

  >path: '/follower/{userId}'
  >
  >method: GET

- [ ] 查看朋友列表：传入用户ID，返回朋友列表

  >path: '/friend/{userId}'
  >
  >method: GET

- [ ] 添加关注：传入关注用户ID

  >path: '/follow/{userId}'
  >
  >method: POST

- [ ] 取消关注：传入取消关注用户ID

  >path: '/follow/{userId}'
  >
  >method: DELETE

### 1.5 喜欢模块

BaseUrl : "/user/likes"

- [ ] 查看喜欢列表：传入用户ID，返回喜欢列表

  > path: '/{userId}'
  >
  > method: GET

- [ ] 添加喜欢：传入用户ID和视频ID

  ==需要更新视频表中的喜欢数量== 

  > path: '/'
  >
  > method: POST
  >
  > Params: 

- [ ] 移除喜欢：传入用户ID和视频ID

  ==需要更新视频表中的喜欢数量== 
  
  > path: '/'
  >
  > method: DELETE
  >
  > Params: 

### 1.6 收藏模块

BaseUrl : "/user/star"

- [ ] 查看收藏列表：传入用户ID，返回收藏列表

  > path: '/{userId}'
  >
  > method: GET

- [ ] 添加收藏：传入用户ID和视频ID

  ==需要更新视频表中的收藏数量== 

  > path: '/'
  >
  > method: POST
  >
  > Params: 

- [ ] 移除收藏：传入用户ID和视频ID

  ==需要更新视频表中的收藏数量== 

  > path: '/'
  >
  > method: DELETE
  >
  > Params: 

### 1.7 消息模块

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
  >

### 1.8 分享模块

...待设计



## 2 数据库设计