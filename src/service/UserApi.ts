// src/services/login.ts


import { http } from '@/utils/http'
import type {UserType} from "@/types/UserType";

/**
 * 账号密码登陆
 * @param data 请求参数
 */
export const getUserInfoById = (params: number) => {
    return http<UserType>({
        method: 'GET',
        url: `/user/${params}`,
    })
}