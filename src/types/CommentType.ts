// 用户端获取到的评论类型
export type CommentType = {
    id: number
    createUser: number
    nickname: string
    profile: string
    content: string
    likes: number
    dislikes: number
    createTime: Date
}

// 用户端提交的评论类型
export type CommentCommitType = {
    videoId: number
    content: string
}