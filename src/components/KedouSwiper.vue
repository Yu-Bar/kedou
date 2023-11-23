<template>
    <view>

        <swiper vertical @change="swiperChange" @animationfinish="handleTouchMove" style="width: 100vw; height: 100vh;">
            <swiper-item v-for="(video, index) in videoList" :key="index" style="width: 100vw; height: 100vh;">
                <view v-if="currentIndex === index" class="video-container" :style="{ top: videoTop }">
                    <video id="myVideo" :src="video.url" :autoplay="index === currentIndex" @ended="replayCurrentVideo"
                        controls class="fullscreen-video" @tap="togglePlay" :show-progress="false" :show-play-btn="false"
                        :show-fullscreen-btn="false" :show-mute-btn="false" @loadedmetadata="loadVideo"></video>
                </view>
            </swiper-item>
        </swiper>
    </view>
</template>
  
<script>
import {getVideoLists} from "@/service/video";

export default {
    components: {
    },
    data() {
        return {
            videoList: [
                {
                    url: 'https://yubar.top/video/1.mp4'
                },
                {
                  url: 'https://yubar.top/video/2.mp4'
                },
                {
                  url: 'https://yubar.top/video/3.mp4'
                },
                // 添加更多视频
            ],
            currentIndex: 0,
            videoContext: null,
            isPaused: true, // 新增一个标志位用于记录视频状态
            videoTop: '0%', // 控制视频元素的顶部位置
        };
    },
    methods: {
        // handleTouchMove() {
        //   // 滑动结束后重新定位视频元素的位置
        //   console.log(this.currentIndex);
        //   uni.pageScrollTo({
        //   	scrollTop: 0,
        // 	duration: 300
        //   });
        // 	console.log(this.currentIndex);
        // },

        replayCurrentVideo() {
            if (this.videoContext) {
                this.videoContext.seek(0); // 重置视频播放时间
                this.videoContext.play(); // 重新播放当前视频
                this.isPaused = false; // 更新视频状态
            }
        },
        togglePlay() {
          console.log(getVideoLists().data)
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
        },
		swiperChange(e) {
		    this.currentIndex = e.detail.current;
		    this.videoContext = uni.createVideoContext('myVideo',this);
		    console.log(this.videoContext);
		},
    },
    loadVideo() {
        this.videoContext = uni.createVideoContext('myVideo',this); // 获取视频上下文
    },
  mounted() {
    console.log(getVideoLists())
  }
};
</script>

<style>
/* 使 video-container 占满整个页面 */
.video-container {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
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
    /* 设置为完全透明 */
    background-color: black;
    margin-top: 0%;
}
</style>
  