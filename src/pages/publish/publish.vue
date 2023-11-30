<template>
  <view class="upload-container">

    <!-- 视频和上传 -->
<!--    <uni-file-picker v-model="videoFile" limit="1" file-mediatype="video" :sizeType="['compressed']" :sourceType="['camera']" @success="onVideoFileSelected">-->
<!--      <view class="video-upload-area">+选择视频</view>-->
<!--    </uni-file-picker>-->
    <view class="video-upload-area" @click="onUploadVideo">
      <image class="upload-icon" :src="videoImg"></image>
      <text class="upload-text">上传视频</text>
    </view>

    <view class="video-upload-area" @click="onUploadCover">
      <image class="upload-icon" :src="coverImg"></image>
      <text class="upload-text">选择封面</text>
    </view>

    <!-- 标题和封面-->

      <view class="input-container">
<!--        <text class="input-label">视频标题</text>-->
        <input class="input-title" v-model="videoTitle" type="text" placeholder="请输入视频标题" @blur="validateTitle" />
      </view>


    <!-- 描述 -->
    <view class="input-container">
<!--      <text class="input-label">视频描述</text>-->
      <!-- 视频描述输入框 -->
      <textarea class="description-input" v-model="videoDescription" placeholder="请输入视频描述" @input="handleDescriptionInput" @blur="validateDescription"></textarea>

    </view>

    <!-- 添加标签按钮 -->
    <view class="add-tag-btn" @click="addTag">添加标签</view>
    <!-- 显示标签列表 -->
    <view class="tag-list">
      <view v-for="(tag, index) in tags" :key="index" class="tag">{{ tag }}</view>
    </view>

    <!-- 提示信息 -->
<!--    <view v-if="showError" class="error-message">{{ errorMessage }}</view>-->
    <!-- 上传按钮 -->

    <view class="commit-btn" @click="validateAndUpload" :disabled="!canUpload">上传视频</view>
  </view>
</template>

<script>
import {publishVideo} from "@/service/VideoApi";
import {useMemberStore} from "@/stores";

export default {
  data() {
    return {
      videoTitle: '',
      videoFile: null,
      coverFile: null,
      showError: false,
      errorMessage: '',
      videoDescription: '', // 视频描述
      tags: [], // 标签列表
      tagRegex: /#\S+/g, // TODO 识别标签的正则表达式 目前不能匹配中文标签
      iconList: [
        '/static/UploadIcon/upload.png',
        '/static/UploadIcon/success_upload.png',
        '/static/UploadIcon/fail_upload.png',
      ],
      videoImg: '/static/UploadIcon/upload.png',
      coverImg: '/static/UploadIcon/upload.png',
    };
  },
  methods: {
    addTag() {
      if (this.tags.length < 5) {
        this.videoDescription += ' #';
      }
    },
    // 处理视频描述输入
    handleDescriptionInput() {
      // 从视频描述中识别标签
      const matches = this.videoDescription.match(this.tagRegex);

      if (matches) {
        // 更新标签列表
        this.tags = matches.map(tag => tag.trim());
      }
    },
    // 视频标题验证
    validateTitle() {
      if (this.videoTitle.trim().length > 32) {
        this.showError = true;
        this.errorMessage = '视频标题不能超过32个字符';
        uni.showToast({
          title: this.errorMessage,
          icon: 'none'
        });
        return false;
      }
      return true;
    },
    // 视频描述及总字数验证
    validateDescription() {
      if (this.videoDescription.trim().length > 250) {
        this.showError = true;
        this.errorMessage = '描述不能超过 250 个字';
        uni.showToast({
          title: this.errorMessage,
          icon: 'none'
        });
        return false;
      }
      if(this.tags.length > 5){
        this.showError = true;
        this.errorMessage = '最多使用 5 个标签';
        uni.showToast({
          title: this.errorMessage,
          icon: 'none'
        });
        return false;
      }
      return true;
    },
    // 标签数量验证
    validateTags() {
      if (this.tags.length > 5) {
        this.showError = true;
        this.errorMessage = '标签数量不能超过5个';
        uni.showToast({
          title: this.errorMessage,
          icon: 'none'
        });
        return false;
      }
      return true;
    },
    onUploadVideo() {
      // 调用拍照/选择图片
      uni.chooseMedia({
        // 文件个数
        count: 1,
        // 文件类型
        mediaType: ['video'],
        success: (res) => {
          // 本地路径
          const { tempFilePath } = res.tempFiles[0]
          // 文件上传
          uni.uploadFile({
            url: '/common/upload', // [!code ++]
            name: 'file', // 后端数据字段名  // [!code ++]
            filePath: tempFilePath, // 新头像  // [!code ++]
            success: (res) => {
              const result =  JSON.parse(res.data)
              // 判断状态码是否上传成功
              if (result.code === 1) {
                // console.log(res.data)
                this.videoFile = JSON.parse(res.data).data
                uni.showToast({ icon: 'success', title: '视频上传成功' })
                this.videoImg = this.iconList[1]
              } else {
                uni.showToast({ icon: 'error', title: result.msg})
                this.videoImg = this.iconList[2]
              }
            },
          })
        },
      })
    },
    onUploadCover() {
      // 调用拍照/选择图片
      uni.chooseMedia({
        // 文件个数
        count: 1,
        // 文件类型
        mediaType: ['image'],
        success: (res) => {
          // 本地路径
          const { tempFilePath } = res.tempFiles[0]
          // 文件上传
          uni.uploadFile({
            url: '/common/upload', // [!code ++]
            name: 'file', // 后端数据字段名  // [!code ++]
            filePath: tempFilePath, // 新头像  // [!code ++]
            success: (res) => {
              const result =  JSON.parse(res.data)
              // 判断状态码是否上传成功
              if (result.code === 1) {
                // console.log(res.data)
                this.coverFile = JSON.parse(res.data).data
                uni.showToast({ icon: 'success', title: '封面上传成功' })
                this.coverImg = this.iconList[1]
              } else {
                uni.showToast({ icon: 'error', title: result.msg})
                this.coverImg = this.iconList[2]
              }
            },
          })
        },
      })
    },
    async validateAndUpload() {
      // 判断是否可以上传
      if (!this.validateTags() || !this.validateTitle() || !this.validateDescription()){
        return;
      }
      // 判断视频和封面是否已经上传
      if(this.videoImg != this.iconList[1] || this.coverImg != this.iconList[1]){
        uni.showToast({ icon: 'error', title: '请正确上传'})
        return;
      }

      console.log('标题:', this.videoTitle);
      console.log('描述:', this.videoDescription);
      console.log('标签:', this.tags);
      console.log('视频文件:', this.videoFile);
      console.log('封面文件:', this.coverFile);

      const res = await publishVideo({
        title: this.videoTitle,
        url: this.videoFile,
        cover: this.coverFile,
        description: this.videoDescription,
        label: this.tags
      })
      if(res.code == 1){
        // console.log(res)
        await uni.showToast({
          icon: 'none',
          title: '上传成功'
        })
        uni.navigateBack();
      }else{
        await uni.showToast({
          title: res.msg,
          icon: 'none'
        });
      }
    }
  }
};
</script>

<style>

.input-container {
  display: flex;
  align-items: center;
  margin-bottom: 10rpx;
  margin-top: 10rpx;
}

.input-title {
  width: 90%;
  margin-left: 5%;
  height: 50rpx; /* 调整为适当的高度 */
  border: 3rpx solid #ccc;
  padding: 10rpx;
  box-sizing: border-box;
  margin-bottom: 20rpx;
  background-color: rgba(255, 255, 255, 0.5);
}

.upload-container {
  padding-top: 20rpx;
  position: relative;
  background: #f5eaf1;
  height: 100vh;
  margin-top: 0;
}

.description-input {
  width: 90%;
  margin-left: 5%;
  position: relative;
  height: 250rpx; /* 调整为适当的高度 */
  border: 3rpx solid #ccc;
  padding: 10rpx;
  box-sizing: border-box;
  margin-bottom: 20rpx;
  background-color: rgba(255, 255, 255, 0.5);
}

.add-tag-btn {
  float: right;
  margin-right: 20rpx;
  bottom: 0;
  width: 20%;
  border-radius: 20rpx;
  background-color: rgb(0, 0, 1); /* 按钮颜色 */
  color: white;
  padding: 5px 10px;
  cursor: pointer;
  z-index: 9999;
  text-align: center;
}

input[type='text'],

.tag-list {
  display: flex;
  flex-wrap: wrap;
  margin-top: 10px;
}

.tag {
  margin: 5px;
  padding: 5px 10px;
  background-color: #000;
  color: #fff;
  border-radius: 5px;
  cursor: pointer;
}

.video-upload-area {
  position: relative;
  text-align: center; /* 居中对齐 */
  color: #000000;
  background-color: rgba(255, 255, 255, 0.5);
  width: 90%;
  margin-left: 5%;
  height: 300rpx;
  border-radius: 5px;
  border: 3rpx solid #ccc;
  cursor: pointer;
  text-align: center;
}

.upload-icon {
  margin-top: 10rpx;
  width: 180rpx;
  height: 180rpx;
}

.upload-text {
  position: absolute;
  bottom: 50rpx;
  left: 0;
  width: 100%;
  text-align: center; /* 居中对齐 */
}

.commit-btn {
  color: #fff;
  background-color: #000;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
  vertical-align: center;
  position: fixed;
  bottom: 100rpx;
  width: 80%;
  margin-left: 10%;
}


</style>
