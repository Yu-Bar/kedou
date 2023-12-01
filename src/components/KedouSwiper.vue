<template>
  <view>
    <!-- 返回按钮 -->
    <image v-if="startIndex !== null" src="/static/goback.png" @click="goBack" class="goback-icon">后退</image>
    <view class="video-swipper" :style="videoStyle">
      <swiper vertical @change="swiperChange" :current="currentIndex" @animationfinish="handleTouchMove"
              style="width: 100vw; height: 100vh;">
        <swiper-item v-for="(video, index) in videoList" :key="index" style="width: 100vw; height: 100vh;">

          <view class="video-indicator-container">
            <view class="indicator-group">
              <view class="video-indicator-item" style="margin-right: 10rpx">
                <image :src="video.profile" @click="getIntoUserSpace(video.createUser)" class="video-author-profile"
                       style="margin-right: 0;width: 90rpx; height: 90rpx"></image>
              </view>
            </view>
            <view class="indicator-group">
              <view class="video-indicator-item">
                <image v-if="video.isLike" src="/static/VideoIcon/likes.png" @click="dislikes(video.id)"></image>
                <image v-else src="/static/VideoIcon/white_likes.png" @click="likes(video.id)"></image>
              </view>
              <view class="indicator">{{ formatNumber(video.likes) }}</view>
            </view>
            <view class="indicator-group">
              <view class="video-indicator-item">
                <image src="/static/VideoIcon/comment.png" @click="comments(video.id)"></image>
              </view>
              <view class="indicator">{{ formatNumber(video.comments) }}</view>
            </view>
            <view class="indicator-group">
              <view class="video-indicator-item">
                <image src="/static/VideoIcon/white_star.png" @click="stars(video.id)"></image>
              </view>
              <view class="indicator">{{ formatNumber(video.stars) }}</view>
            </view>
            <view class="indicator-group">
              <view class="video-indicator-item">
                <image src="/static/VideoIcon/share.png" @click="shares(video.id)"></image>
              </view>
              <view class="indicator">{{ formatNumber(video.shares) }}</view>
            </view>
          </view>

          <view class="video-container" :style="{ top: videoTop }">
            <video :id="'myVideo' + index" :src="video.url" @ended="replayCurrentVideo"
                   controls class="fullscreen-video" @tap="togglePlay" :show-progress="false" :show-play-btn="false"
                   :show-fullscreen-btn="false" :show-mute-btn="false" @loadedmetadata="loadVideo"
                   :show-center-play-btn="false"></video>
          </view>
          <view class="video-discription-container" v-if="!isCommentVisible">
            <view class="video-author-nickname">@{{ video.nickname }}</view>
            <view class="video-description">{{ video.title }}。{{ video.description }}</view>
          </view>
        </swiper-item>
      </swiper>

    </view>
    <!-- 评论列表容器 -->
    <!-- 评论输入框 -->
    <view class="comment-box" :class="{ 'visible': isCommentVisible }">

      <!-- 已有评论列表 -->
      <view v-if="commentList !== null" class="comment-list">
        <scroll-view scroll-y="true" class="scroll-Y">
          <view v-for="(comment, index) in commentList" :key="index" class="comment-item">
            <uni-card :title="comment.nickname" :isFull="true" :sub-title="comment.content"
                      :thumbnail="comment.profile" class="custom-card">
              <text>{{ formattedDateTime(comment.createTime) }}</text>
              <view class="comment-like-dislike">
                <image class="comment-mini-icon" src="/static/VideoIcon/like_gray.png"
                       @click="like_comment(comment.id)"></image>
                <text>{{ comment.likes }}</text>
                <image class="comment-mini-icon" src="/static/VideoIcon/dislike_gray.png"
                       @click="dislike_comment(comment.id)"></image>
                <text>{{ comment.dislikes }}</text>
              </view>
            </uni-card>
          </view>
          <uni-card>
              <text>暂无更多评论，发一条吧</text>
              <text>...</text>
          </uni-card>
        </scroll-view>
      </view>
      <view v-else class="no-comment">
        平等交流，友善表达
      </view>
      <!-- 评论输入框内容 -->
      <!-- 此处放置评论输入框的内容 -->
      <view class="comment-commit">
        <textarea v-model="commentContent" class="comment-input" placeholder="善语结善缘，恶言伤人心"></textarea>
        <button class="submit-button" @click="sendComment(this.videoList[this.currentIndex].id)">发送</button>
      </view>
    </view>

  </view>
</template>

<script>
import {getVideoLists} from "@/service/VideoApi";
import {commitComment, getCommentListByVideoId} from "@/service/CommentApi";
import {dislikeVideo, likeVideo} from "@/service/LikesApi";

// let authorId;
export default {
  // props: {
  //   //视频作者
  //   authorId: {
  //     type: Number, // 定义属性类型为数值
  //     required: false, // 设置为必需的
  //     // default: 0 // 设置默认值
  //   }
  // },
  components: {},
  data() {
    return {
      videoList: [],
      currentIndex: null,
      videoContext: null,
      isPaused: true, // 新增一个标志位用于记录视频状态
      videoTop: '0%', // 控制视频元素的顶部位置
      startIndex: null,
      commentListHeight: 0, // 评论列表容器高度，用于控制展示或收缩
      isCommentVisible: false,  // 是否显示评论列表
      commentContent: '',
      autoPlay: true,
      // TODO 已有的评论列表数据
      commentList: [
        {
          nickname: '加载中',
          content: '加载中',
          profile: '/static/logo_out.png',
          createTime: '加载中',
          likes: 0,
          dislikes: 0,
        }
      ]
    };
  },
  computed: {
    formatNumber() {
      return (value) => {
        if (value > 9999) {
          let formattedNumber = (value / 10000).toFixed(1); // 将数字格式化为指定位数的小数
          if (formattedNumber.endsWith('.0')) {
            formattedNumber = formattedNumber.slice(0, -2); // 去掉小数末尾的'.0'
          }
          return `${formattedNumber}万`
        } else {
          // console.log('format3')
          return value.toString();
        }
      };
    },
    // 格式化时间展示格式
    formattedDateTime() {
      return (dateTimesStr) => {
        // 将后端返回的时间字符串转换为 Date 对象
        const dateTime = new Date(dateTimesStr);

        // 获取当前时间
        const now = new Date();

        // 计算时间差值
        const diffTime = now - dateTime;
        const diffMinutes = Math.floor(diffTime / (1000 * 60));
        const diffHours = Math.floor(diffTime / (1000 * 60 * 60));
        const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));

        if (diffMinutes < 1) {
          // 1分钟以内的时间展示为 '刚刚'
          return '刚刚';
        } else if (diffHours < 1) {
          // 1小时以内的时间展示为 'n分钟前'
          return `${diffMinutes}分钟前`;
        } else if (diffDays < 1) {
          // 今天的时间展示 'n小时前'
          return `${diffHours}小时前`;
        } else if (diffDays === 1 && diffTime < 24 * 60 * 60 * 1000) {
          // 昨天但时间未超过24小时的展示 '昨天 小时:分钟'
          const hours = dateTime.getHours().toString().padStart(2, '0');
          const minutes = dateTime.getMinutes().toString().padStart(2, '0');
          return `昨天 ${hours}:${minutes}`;
        } else if (diffDays >= 1 && diffDays < 2) {
          // 24小时-48小时之间的展示为 '1天前'
          return '1天前';
        } else if (diffDays >= 2 && diffDays < 3) {
          // 48小时-72小时之间的展示为 '2天前'
          return '2天前';
        } else if (diffDays >= 3 && diffDays < 4) {
          // 72小时-96小时之间的展示为 '3天前'
          return '3天前';
        } else if (diffDays >= 4 && diffTime < now - new Date(`${now.getFullYear()}-01-01`)) {
          // 超出96小时后，在今年的就展示为‘月-日’
          const month = dateTime.getMonth() + 1;
          const day = dateTime.getDate();
          return `${month}-${day}`;
        } else {
          // 今年以前的展示为‘年-月-日’
          const year = dateTime.getFullYear();
          const month = dateTime.getMonth() + 1;
          const day = dateTime.getDate();
          return `${year}-${month}-${day}`;
        }
      }
    }
  },
  methods: {
    swiperChange(e) {
      const prevIndex = this.currentIndex; // 记录上一个视频的索引
      this.currentIndex = e.detail.current;
      this.videoContext = uni.createVideoContext(`myVideo${this.currentIndex}`, this);
      // 暂停上一个视频
      if (this.videoContext && prevIndex !== this.currentIndex) {
        const prevVideoContext = uni.createVideoContext(`myVideo${prevIndex}`, this);
        if (prevVideoContext) {
          prevVideoContext.pause();
        }
      }
      if(this.autoPlay)
        this.videoContext.play();
    },
    replayCurrentVideo() {
      if (this.videoContext) {
        this.videoContext.seek(0); // 重置视频播放时间
        this.videoContext.play(); // 重新播放当前视频
        this.isPaused = false; // 更新视频状态
      }
    },
    stopPlay() {
      if (this.videoContext) {
        this.videoContext.pause(); // 如果视频正在播放，则暂停视频
        this.isPaused = true; // 更新视频状态
      }
    },
    continuePlay() {
      if (this.videoContext) {
        this.videoContext.play(); // 如果视频处于暂停状态，则播放视频
        this.isPaused = false; // 更新视频状态
      }
    },
    togglePlay() {
      // 隐藏评论
      if (this.isCommentVisible) {
        this.toggleComment()
      } else {
        // 暂停视频
        // console.log(this.videoContext);
        if (this.videoContext) {
          if (this.isPaused) {
            console.log(this.isPaused)
            this.videoContext.play(); // 如果视频处于暂停状态，则播放视频
            this.isPaused = false; // 更新视频状态
          } else {
            console.log(this.isPaused)
            this.videoContext.pause(); // 如果视频正在播放，则暂停视频
            this.isPaused = true; // 更新视频状态
          }
        }
      }
    },
    loadVideo() {
      // 当视频加载元数据后触发，此时视频已经可以播放
      this.videoContext = uni.createVideoContext(`myVideo${this.currentIndex}`, this);
      if(this.autoPlay)
        this.videoContext.play();
    },
    goBack() {
      // 返回上一个页面
      uni.navigateBack({
        delta: 1,
        success: () => {
          console.log('事件emit')
          uni.$emit('onReloadMyInfo'); // 触发自定义事件 onReloadInfo，刷新用户信息
        }
      });
    },
    // showVideoLists() {
    //   console.log("btn",this.videoList)
    // },
    async getVideoData() {
      const res = await getVideoLists()
      // this.$set(this, 'videoList', res.data)
      this.videoList = res.data
    },
    reloadComponent() {
      console.log("请求后端视频")
      this.getVideoData()
      this.currentIndex = 0
    },

    // 喜欢视频
    async likes(id) {
      const res = await likeVideo(id)
      if(res.code == 1){
        this.videoList[this.currentIndex].isLike = true
        this.videoList[this.currentIndex].likes++
      }
    else{
        await uni.showToast({
          title: '喜欢失败，请检查网络',
          icon: 'error'
        })
      }
    },

    async dislikes(id) {
      const res = await dislikeVideo(id)
      if(res.code == 1) {
        this.videoList[this.currentIndex].isLike = false
        this.videoList[this.currentIndex].likes--
      }
      else {
        await uni.showToast({
          title: '取消喜欢失败，请检查网络',
          icon: 'error'
        })
      }
    },

    toggleComment() {
      // 点击评论按钮时切换评论列表的显示状态
      this.isCommentVisible = !this.isCommentVisible;
      this.setVideoStyle()
    },

    // TODO 进入用户主页
    getIntoUserSpace(userId) {
      console.log('进入用户主页', userId)
    },

    async comments(id) {
      console.log('comments', id)
      const res = await getCommentListByVideoId(id)
      this.commentList = res.data
      this.toggleComment()
      //更新评论数量
      this.videoList[this.currentIndex].comments = this.commentList.length
    },
    // 发表评论
    async sendComment(videoId){
      if (this.commentContent.trim() !== ''){
        const res = await commitComment({
          videoId: videoId,
          content: this.commentContent.trim()
        })
        this.commentList = res.data
        //更新评论数量
        this.videoList[this.currentIndex].comments = this.commentList.length
        this.commentContent = ''
      }
    },
    // TODO 收藏视频
    // async star(id) {
    //   const res = await likeVideo(id)
    //   if(res.code == 1)
    //     this.videoList[this.currentIndex].isLike = true
    //   else
    //     await uni.showToast({
    //       title: '喜欢失败，请检查网络',
    //       icon: 'error'
    //     })
    // },
    // async disStar(id) {
    //   const res = await dislikeVideo(id)
    //   if(res.code == 1)
    //     this.videoList[this.currentIndex].isLike = false
    //   else
    //     await uni.showToast({
    //       title: '取消喜欢失败，请检查网络',
    //       icon: 'error'
    //     })
    // },
    setVideoStyle() {
      // 在评论弹出时设置视频内容的样式
      if (this.isCommentVisible) {
        this.videoStyle = {
          position: 'fixed',
          transform: 'translate(0%, -25%) scale(0.5)' // 缩放视频内容并移动到页面上半部分
          // 可以根据需要修改视频的其他样式，以便适应评论弹出的效果
        };
      } else {
        this.videoStyle = {}; // 恢复视频内容原本的样式
      }
    },
  },
  mounted() {
    if (!this.startIndex) {
      console.log("请求后端视频")
      this.getVideoData()
      this.currentIndex = 0
    }
    // console.log("author",this.authorId)
  },
  onLoad(options) {
    // console.log(options)
    // 在页面加载时获取传递的参数
    if (options.videoList) {
      this.videoList = JSON.parse(decodeURIComponent(options.videoList))
      this.startIndex = options.videoNum
    }
  },
  // onShow() {
  //   if (this.startIndex !== null) {
  //     this.currentIndex = this.startIndex;
  //     // 创建视频上下文并播放指定序号的视频
  //     this.videoContext = uni.createVideoContext(`myVideo${this.currentIndex}`, this);
  //     this.videoContext.play();
  //   }
  // },
  onReady() {
    if (this.startIndex !== null) {
      this.currentIndex = this.startIndex;
    }
    // 创建视频上下文并播放指定序号的视频
    this.videoContext = uni.createVideoContext(`myVideo${this.currentIndex}`, this);
    this.videoContext.play();
    // console.log("当前视频序号",this.currentIndex)
    // console.log("视频videoContext",this.videoContext)
    // 监听视频加载元数据的事件
    // this.videoContext.onLoadedMetadata(() => {
    //   // 在视频加载完元数据后播放视频
    //   this.videoContext.play();
    // });
    console.log("当前视频序号", this.currentIndex);
    console.log("视频videoContext", this.videoContext);
  }
};
// export const getVideoData = async () => {
//   const res = await getVideoLists()
//   videoList = res.data
//   console.log("getVideoData",res.data)
// }
</script>

<style>
.video-indicator-container {
  opacity: 90%;
  position: fixed;
  bottom: 200rpx;
  padding: 10px;
  right: 0;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  /* 设置 z-index 为较高的值 */
  z-index: 9999;
  background: none;
}

.indicator-group {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 10px; /* 控制组之间的垂直距离 */
  background: none;
}

.video-author-profile {
  border-radius: 50%; /* 将图片裁剪为圆形 */
  width: 100rpx;
  height: 100rpx;
}

.video-indicator-item {
  display: flex;
  align-items: center;
  margin-bottom: 10rpx; /* 增加图片和数字之间的间距 */
  background: none;
}

.video-indicator-item image {
  width: 70rpx;
  height: 70rpx;
  margin-right: 0;
}

.indicator {
  text-align: center;
  color: #f7f6f5;
  background: none;
  margin-right: 0;
  width: 110rpx;
}

.video-discription-container {
  position: absolute;
  bottom: 180rpx;
  left: 10rpx;
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 75%; /* 最大宽度为页面宽度的75% */
}

.video-author-nickname {
  margin-bottom: 10px; /* 调整作者昵称与描述之间的间距 */
  z-index: 9999;
  background: none;
  color: #f7f6f5;
  font-size: 40rpx;
}

.video-description {

  margin-top: -5px; /* 通过调整这个值来控制昵称和描述的间距 */
  background: none;
  color: #f7f6f5;
  overflow: hidden;
  text-overflow: ellipsis; /* 超出部分显示省略号 */
  word-wrap: break-word; /* 允许单词内换行 */
  overflow-wrap: break-word; /* 允许单词内换行，适用于更多浏览器 */
}

/* 使 video-container 占满整个页面 */
.video-container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  background: none;
}

/* 视频充满屏幕 */
.fullscreen-video {
  width: 100%;
  height: 100%;
  object-fit: fill;
  cursor: pointer;
  /* 鼠标指针样式为手型 */
  position: relative;
}

/* 取消点击高亮效果 */
view {
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
  margin-top: 0%;
}

.goback-icon {
  position: fixed;
  top: 70rpx; /* 距离顶部的距离 */
  left: 10rpx; /* 距离左侧的距离 */
  width: 28px; /* 图片宽度 */
  height: 28px; /* 图片高度 */
  z-index: 9999; /* 图片层级 */
  opacity: 80%;
  background: none;
}

/* 评论输入框的样式 */
.comment-box {
  border-radius: 20rpx 20rpx 0 0;
  display: block;
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background-color: #fff;
  border-top: 1px solid #ccc;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
  transform: translateY(100%);
  transition: transform 0.3s ease-in-out;
  max-height: 50vh; /* 设置评论框最大高度为屏幕高度的一半 */
  overflow: hidden;
}

.comment-box.visible {
  transform: translateY(0);
  max-height: 50vh; /* 展开评论框 */
  z-index: 9999;
}

.comment-list {
  position: relative;
  background: none;
}

.comment-item {
  background: none;
  color: black;
}

.comment-commit {
  padding: 0;
  position: fixed;
  background: #fff;
  display: flex;
  bottom: 0;
  height: 120rpx;
  width: 100%;
}

.comment-input {
  width: 80%;
  margin: 10rpx;
  padding: 10rpx;
  border-radius: 20rpx;
  height: 50rpx;
  color: black;
  background: #f3f3f3;
}

.submit-button {
  width: 15%;
  margin-right: 10rpx;
  margin-top: 10rpx;
  background-color: #fc2b55;
  color: #fff;
  border: none;
  border-radius: 50rpx;
  cursor: pointer;
  height: 50rpx;
  font-size: 20rpx;
  text-align: center;
}

/* 视频内容的样式 */
.video-swipper {
  position: relative;
  transition: transform 0.3s ease-in-out; /* 添加过渡动画效果 */
  background: none;
}

/* 覆盖 uni-card 默认样式 */
.custom-card {
  background-color: transparent;
  /* 其他样式 */
}

.scroll-Y {
  height: 50vh;
}

.no-comment {
  background: none;
  text-align: center;
  height: 50vh;
  color: black;
  margin-top: 300rpx;
  align-items: center; /* 垂直居中 */
}

.comment-like-dislike {
  float: right;
}

.comment-mini-icon {
  width: 28rpx;
  height: 28rpx;
}
</style>
  