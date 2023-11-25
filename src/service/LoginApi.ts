// src/services/login.ts


import { http } from '@/utils/http'
import type {LoginResult} from "@/types/LoginResult";

type LoginParams = {
    account: string
    password: string
}
/**
 * 账号密码登陆
 * @param data 请求参数
 */
export const postLoginPassWordAPI = (data: LoginParams) => {
    return http<LoginResult>({
        method: 'POST',
        url: '/user/login',
        data,
    })
}