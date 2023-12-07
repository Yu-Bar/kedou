// src/services/login.ts


import { http } from '@/utils/http'
import type {ProfileType} from "@/types/UserType";

/**
 * 获取用户的关注集合
 * @param userId
 */
export const getFollowingSetById = (userId: number) => {
    return http<ProfileType>({
        method: 'GET',
        url: `/relation/following/${userId}`,
    })
}

/**
 * 获取用户的粉丝集合
 * @param userId
 */
export const getFollowerSetById = (userId: number) => {
    return http<ProfileType>({
        method: 'GET',
        url: `/relation/follower/${userId}`,
    })
}

/**
 * 获取用户的朋友集合
 * @param userId
 */
export const getFriendSetById = (userId: number) => {
    return http<ProfileType>({
        method: 'GET',
        url: `/relation/friend/${userId}`,
    })
}

/**
 * 关注用户
 * @param userId
 */
export const followUserById = (userId: number) => {
    return http({
        method: 'POST',
        url: `/relation/follow/${userId}`,
    })
}

/**
 * 取关用户
 * @param userId
 */
export const unfollowUserById = (userId: number) => {
    return http({
        method: 'DELETE',
        url: `/relation/follow/${userId}`,
    })
}

