// src/services/login.ts


import { http } from '@/utils/http'
import type {UserChatType} from "@/types/UserType";
import type {MessageDTOType, MessageType, SessionType} from "@/types/MessageType";

/**
 * 获取聊天所需的用户信息
 * @param userId
 */
export const getUserChatInfoList = (userIds: number[]) => {
    return http<UserChatType[]>({
        method: 'GET',
        url: `/user/chat?ids=${userIds.join(",")}`,
    })
}

/**
 * 发消息
 * @param userId
 */
export const sendMessage = (message: MessageDTOType) => {
    return http<MessageType>({
        method: 'POST',
        url: '/message',
        data: message
    })
}

/**
 * 获取会话
 * @param userId
 */
export const createSession = (userId: number) => {
    return http<SessionType>({
        method: 'POST',
        url: `/message/session?userId=${userId}`,
    })
}

/**
 * 获取历史会话
 * @param userId
 */
export const getAllSession = () => {
    return http<SessionType[]>({
        method: 'GET',
        url: `/message`,
    })
}



