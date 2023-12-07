// src/types/member.d.ts

/** 消息类型 */
export type MessageType = {
    /** 所属会话ID */
    session: number
    /** 是否是自己发送的消息 */
    createUser: number
    /** 消息内容 */
    content: string
    /** 分享视频 */
    videoId: number
    /** 发送时间 */
    createTime: Date
}

/** 聊天会话类型 */
export type SessionType = {
    /** 会话ID */
    session: number
    /** 聊天的对方用户id */
    userId: number
    /** 聊天的对方用户昵称 */
    nickname: string
    /** 聊天的对方用户id */
    profile: string
    /** 消息列表 */
    messageList: MessageType[]
}


/** 发送给服务器的消息类型 */
export type MessageDTOType = {
    /** 所属会话ID */
    session: number
    /** 是否是自己发送的消息 */
    receiver: number
    /** 消息内容 */
    content: string
    /** 分享视频 */
    videoId: number
}