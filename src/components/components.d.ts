import KedouSwiper from './KedouSwiper.vue'


declare module 'vue' {
  export interface GlobalComponents {
    KedouSwiper: typeof KedouSwiper
  }
}