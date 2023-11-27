import KedouSwiper from './KedouSwiper.vue'
import KedouBlankNavbar from "@/components/KedouBlankNavbar.vue";


declare module 'vue' {
  export interface GlobalComponents {
    KedouSwiper: typeof KedouSwiper
    KedouBlankNavbar: typeof KedouBlankNavbar
  }
}