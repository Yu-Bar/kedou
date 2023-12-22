# "科抖"短视频平台项目

> Author: [Yu-bar](https://github.com/Yu-Bar) 
>
> Date: 2023.11.24

## 1 介绍

"科抖"是一款前后端分离的短视频平台应用，USTC自己的抖音！

实现功能：

- 刷视频
- 发布视频
- 视频点赞、收藏、评论
- 个人空间
- 关注和粉丝功能
- 私信功能

  <div class="image-container" style="display: flex;justify-content: space-around;align-items: center;">
    <img src=".\doc\img\demonstration1.gif" alt="项目演示1" style="zoom:35%;" >
    <img src=".\doc\img\demonstration2.gif" alt="项目演示2" style="zoom:35%;" >
    <img src=".\doc\img\demonstration3.gif" alt="项目演示3" style="zoom:35%;" >
    <img src=".\doc\img\demonstration4.gif" alt="项目演示3" style="zoom:35%;" >
  </div>




### 1.1 技术选型

- 后端：JDK17 + SpringBoot3 \+ MySql + Redis + RabbitMQ + 阿里云OSS
- 前端：uni-app + vue3 + uni-ui + nodejs + Pinia



## 2 使用说明

- 后端：

  1. 将 `doc/kedou.sql` 中的sql文件导入

  2. 配置参数：配置好 `src/main/resources/application-template.yml` 文件中的所有参数，并修改`src/main/resources/application-template.yml`  中的

     ```yaml
     spring:
       profiles:
         active: dev # 这里是你的配置文件后缀，你可以把application-template.yml修改为application-dev.yml
     ```

     

- 前端：

  环境：nodejs: v20.9.0  pnpm:8.10.5

  1. 项目使用pnpm进行包管理，执行下面的命令下载所需依赖

     ```bash
     pnpm install
     ```

  2. 修改 `src/utils/http.ts` 文件中的请求基地址为你的后端服务器地址

     ```
     // 请求基地址
     const baseURL = 'http://localhost:8077/user'
     ```

  3. 修改 `src/service/WebSocketService.ts` 文件中的 WebSocket 连接地址为你后端连接的WebSocket 端点位置

     ```bash
      this.url = `ws://localhost:8077/ws/${userId}`;
     ```

  4. 微信小程序调试：

     1. 下载微信开发者工具

     2. 执行命令

        ```bash
        pnpm dev:mp-weixin
        ```

     3. 执行上面命令后，会在工程路径下生成一个dist目录，打开微信小程序，导入项目，路径为：`dist/dev/mp-weixin`  ，之后就可以在微信开发者工具的模拟器中预览了

     

## 3 架构设计

<img src=".\doc\img\Architecture.png" alt="架构设计" style="zoom: 33%;" />



## 4 模块设计

[接口文档](doc/用户端接口.md) 

说明：

- [ ] 待实现     

- [x] 已实现

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

  - 首页观看：无需指定用户ID，按**时间倒序**排列（分页查询）
  - 个人主页下观看视频：在获取用户信息时一并获取对应的作品视频列表，按**时间倒序**排列(在查询用户时，一并查询)
  - 还需要查询作者头像和作者昵称来展示：**所以在video表中冗余一条数据来保存作者头像url和作者昵称，但是在用户修改头像和昵称时需要注意要同时修改其发布视频的作者头像url** 

  >path: '/list'
  >
  >method: GET

- [x] 观看朋友视频： 

  >path: '/list/friends'
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

  个人主页下观看视频：在获取用户信息时一并获取对应的作品视频列表，按**时间倒序**排列(在查询用户时，一并查询)

  >path: '/{userId}'
>
  >method: GET

- [x] 注册：传入账号密码，返回用户对象

  还需要为新注册的用户创建一个信箱（消息队列），用来接收其他人发给新用户的消息

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

<img src=".\doc\img\MessageSystem.png" alt="消息收发" style="zoom: 80%;" />

BaseUrl : "/user/message"

- [x] 获取当前用户的所有会话：用于用户的聊天记录漫游

  >path: '/'
  >
  >method: GET
  >
  >Body:

- [x] 发送消息：传入接收用户ID和消息内容

  ==新消息通知，使用消息队列存储新消息，再使用websocket，当用户在线时进行转发== 

  > path: '/{userId}'
  >
  > method: POST
  >
  > Body:

- [x] 创建会话：创建一个当前用户和指定用户之间的会话，如果会话已经存在，则返回历史会话，如果不存在就新建一个

  > path: '/session'
  >
  > method: POST
  >
  > Params:userId

### 4.8 分享模块

...待设计



## 5 数据库设计

<img src=".\doc\img\DataBase.png" alt="数据库设计"  />



## 6 技巧总结

### 6.1 前端篇

#### 6.1.1 数据格式化

Q：如何实现下面这样的数据格式化？当数字大于9999时显示××万

<img src=".\doc\img\DataFormat.png" alt="数据格式化" style="float: left; zoom: 67%;" />

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



#### 6.1.2 用户关系处理

和后端约定好这些常量

```java
public class RelationConstant {
    // 路人
    public static final Integer PASSERBY = 0;
    // 关注
    public static final Integer FOLLOWING = 1;
    // 粉丝
    public static final Integer FOLLOWER = 2;
    // 朋友
    public static final Integer FRIEND = 3;
}
```

在处理取关操作时，将当前用户与取关用户的关系常量-1，就可以变成正确的关系状态。

- 关注 -> 路人 (1 - 1 = 0) 
- 朋友 -> 粉丝 (3 - 1 = 2) 
- 0 状态和 2 状态只有关注操作，没有取关操作

同样，在处理关注操作时，将当前用户与取关用户的关系常量+1，就可以变成正确的关系状态。



### 6.2 后端篇

#### 6.2.1 WebSocket注入问题

websocket里无法注入其它@Compenent 、@Service、@Resource、@Autowired、@Value等单例组件

spring管理的都是单例（singleton），和 websocket （多对象）相冲突。
websocket 不是单例，是动态生成的，因此无法注入单例组件。

像 controller 里面有 service， service 里面有 dao。因为 controller，service ，dao 都有是单例，所以注入时不会报 null。但是 websocket 不是单例，所以使用spring注入一次后，后面的对象就不会再注入了，会报null。



解决方法：

```java
@ServerEndpoint("/websocket")
@Component
public class WebSocketServer {

    private static MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        WebSocketServer.messageService = messageService;
    }
}
```



#### 6.2.2 后端部署时WebSocket和Nginx的冲突

NGINX 中的 `proxy_read_timeout` 是针对普通 HTTP 请求的超时设置，并不直接适用于 WebSocket 连接。对于 WebSocket 连接，NGINX 有不同的配置方式来处理超时问题。

当 NGINX 用作 WebSocket 代理时，可以考虑使用 `proxy_connect_timeout`、`proxy_send_timeout` 和 `proxy_read_timeout` 等参数进行 WebSocket 的超时设置。但是需要注意的是，对于 WebSocket 连接，通常情况下并不希望设置超时时间为**永不超时**，因为这可能会导致连接资源无限期占用，最好还是设置一个合理的超时时间来避免资源浪费。

在 NGINX 中配置 WebSocket 代理时，你可以像下面这样设置超时时间：

```nginx
location /websocket-route {
    proxy_pass http://backend_server;
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "upgrade";
    proxy_connect_timeout 7d; # 设置连接超时时间为7天，表示基本不超时
    proxy_send_timeout 7d;    # 设置发送超时时间为7天
    proxy_read_timeout 7d;    # 设置读取超时时间为7天
}
```

这里 `proxy_connect_timeout`、`proxy_send_timeout` 和 `proxy_read_timeout` 分别设置了连接、发送和读取的超时时间为7天，这几乎等同于永不超时。但是请注意，这种设置可能会导致资源的长时间占用，并可能不适用于所有场景。你需要根据实际情况和系统需求来调整这些超时时间。

总体来说，为了避免资源浪费，最好还是根据你的应用需求设置一个合理的超时时间，使其既能确保 WebSocket 连接的稳定性，又不会长时间占用服务器资源。