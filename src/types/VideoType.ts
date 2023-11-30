// 用户端获取到的视频类型
export type VideoType = {
    id: number
    createUser: number
    nickname: string
    profile: string
    title: string
    url: string
    cover: string
    description: string
    likes: number
    comments: number
    stars: number
    shares: number
    label: string
    createTime: Date
    updateTime: Date
    status: number
    open: number
}

// 上传视频时传输给服务器的数据类型
export type VideoPublishType = {
    title: string
    url: string
    cover: string
    description: string
    label: string[]
}