// src/types/member.d.ts

/** 小程序登录 登录用户信息 */
export type LoginResult = {
    /** 用户ID */
    id: number
    /** 昵称 */
    nickname?: string
    /** 登录凭证 */
    token: string
}