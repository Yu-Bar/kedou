<template>
  <view>
    <CustomNavbar/>
    <KedouSwiper/>
  </view>
</template>

<script>
  import CustomNavbar from './components/CustomNavbar.vue'
  export default {
      components: {
        CustomNavbar,
      },
    }
</script>

<style>

  view {
    height: 100%;
  }

</style>
  
