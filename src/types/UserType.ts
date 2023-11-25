// src/types/member.d.ts

import type {VideoType} from "@/types/VideoType";

/** 获取用户信息 */
export type UserType = {
    /** 用户ID */
    id: number
    /** 昵称 */
    nickname: string
    /** 用户名 */
    username: string
    /** 喜欢 */
    likes: number
    /** 朋友 */
    friends: number
    /** 关注 */
    following: number
    /** 粉丝 */
    follower: number
    /** 头像 */
    profile: string
    /** 简介 */
    bio: string
    /** 地址 */
    address: string
    /** 性别 */
    sex: number
    /** 生日 */
    birthday: Date
    /** 学校 */
    school: string
    /** 账号状态 */
    status: number
    /** 发布视频列表 */
    videoList : VideoType[]
}