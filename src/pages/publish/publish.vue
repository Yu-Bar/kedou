<template>
  <view class="upload-container">
    <!-- 标题 -->
    <view class="input-container">
      <text class="input-label">视频标题：</text>
      <input v-model="videoTitle" type="text" placeholder="请输入视频标题" @blur="validateTitle" />
    </view>

    <!-- 描述 -->
    <view class="input-container">
      <text class="input-label">视频描述：</text>
      <textarea v-model="videoDescription" placeholder="请输入视频描述" @blur="validateDescription"></textarea>
    </view>

    <!-- 添加标签按钮 -->
    <view class="input-container">
      <text class="input-label">视频标签：</text>
      <view class="add-tag-btn" @click="addTag" :disabled="tags.length >= 5"># 添加标签</view>
    </view>

    <!-- 显示标签列表 -->
    <view class="tag-list">
      <view v-for="(tag, index) in tags" :key="index" class="tag">{{ tag }}</view>
    </view>

    <!-- 视频和封面上传 -->
    <uni-file-picker v-model="videoFile" :count="1" file-mediatype="video" :sizeType="['original']" :sourceType="['camera']" @success="onVideoFileSelected">
      <view class="upload-btn">选择视频</view>
    </uni-file-picker>
    <uni-file-picker v-model="coverFile" :count="1" file-mediatype="image" :sizeType="['compressed']" :sourceType="['album']" @success="onCoverFileSelected">
      <view class="upload-btn">选择封面</view>
    </uni-file-picker>

    <!-- 提示信息 -->
    <view v-if="showError" class="error-message">{{ errorMessage }}</view>

    <!-- 上传按钮 -->
    <view class="upload-btn" @click="validateAndUpload" :disabled="!canUpload">上传视频</view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      videoTitle: '',
      videoDescription: '',
      tags: [],
      videoFile: [],
      coverFile: [],
      showError: false,
      errorMessage: ''
    };
  },
  computed: {
    canUpload() {
      const isTagLimitReached = this.tags.length >= 5;
      const isTitleValid = this.videoTitle.trim().length <= 32;
      const isDescriptionValid = this.videoDescription.trim().length <= 250;
      const totalLength = this.videoDescription.trim().length + this.tags.join('').length;
      const isTotalLengthValid = totalLength <= 250;

      return !isTagLimitReached && isTitleValid && isDescriptionValid && isTotalLengthValid;
    }
  },
  methods: {
    addTag() {
      if (this.tags.length < 5) {
        this.videoDescription += ' #';
      }
    },
    validateTitle() {
      if (this.videoTitle.trim().length > 32) {
        this.showError = true;
        this.errorMessage = '视频标题不能超过32个字符';
        uni.showToast({
          title: this.errorMessage,
          icon: 'none'
        });
      } else {
        this.showError = false;
        this.errorMessage = '';
      }
    },
    validateDescription() {
      if ((this.videoDescription.trim().length + this.tags.join('').length) > 250) {
        this.showError = true;
        this.errorMessage = '描述和标签的总字符数不能超过250个';
        uni.showToast({
          title: this.errorMessage,
          icon: 'none'
        });
      } else {
        this.showError = false;
        this.errorMessage = '';
      }
    },
    onVideoFileSelected(files) {
      this.videoFile = files;
      console.log('视频文件:', this.videoFile);
      // 显示视频上传进度
      uni.showLoading({
        title: '视频上传中...',
        mask: true
      });
      // TODO: 使用uni.uploadFile上传视频文件
    },
    onCoverFileSelected(files) {
      this.coverFile = files;
      console.log('封面文件:', this.coverFile);
      // 显示封面上传进度
      uni.showLoading({
        title: '封面上传中...',
        mask: true
      });
      // TODO: 使用uni.uploadFile上传封面文件
    },
    validateAndUpload() {
      // 标签数量验证
      if (this.tags.length > 5) {
        this.showError = true;
        this.errorMessage = '标签数量不能超过5个';
        uni.showToast({
          title: this.errorMessage,
          icon: 'none'
        });
        return;
      }

      // 视频标题验证
      this.validateTitle();

      // 视频描述及总字数验证
      this.validateDescription();

      // 判断是否可以上传
      if (this.canUpload) {
        // 执行上传视频的逻辑，包括标题、描述、标签、封面图和视频文件
        // 调用后端API上传视频和相关信息
        // ...
        console.log('标题:', this.videoTitle);
        console.log('描述:', this.videoDescription);
        console.log('标签:', this.tags);
        console.log('视频文件:', this.videoFile);
        console.log('封面文件:', this.coverFile);

        // 上传成功后，隐藏上传进度
        uni.hideLoading();
      }
    }
  }
};
</script>

<style>
/* 样式可以根据你的需求来调整 */
/* ... */
.error-message {
  color: red;
  margin-top: 10px;
}
.input-container {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.input-label {
  width: 80px;
  text-align: right;
  margin-right: 10px;
}

.upload-container {
  padding: 20px;
}

input[type='text'],
textarea {
  width: 100%;
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.add-tag-btn {
  color: #fff;
  background-color: #000;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}

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

.upload-btn {
  color: #fff;
  background-color: #000;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}
</style>
