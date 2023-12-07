import type {UnwrapRef} from "@vue/runtime-core";
import type {LoginResult} from "@/types/LoginResult";
import {useMessageStore} from "@/stores/modules/message";

class WebSocketService {
    private static instance: WebSocketService | null = null;
    private socketTask: UniApp.SocketTask | null = null;
    private url: string = '';

    private constructor() {
        // WebSocket 连接初始化在 connect 方法中执行
    }

    public static getInstance(): WebSocketService {
        if (!WebSocketService.instance) {
            WebSocketService.instance = new WebSocketService();
        }
        return WebSocketService.instance;
    }

    connect(userId: UnwrapRef<LoginResult["id"]> | undefined) {
        // 根据当前用户的 ID 设置 WebSocket 连接的 URL
        this.url = `ws://localhost:8077/ws/${userId}`; // 替换成后端的 WebSocket 地址和用户 ID 微信小程序是使用WSS
        this.socketTask = uni.connectSocket({
            url: this.url,
            complete: () => {
                console.log('WebSocket 连接已建立');
            }
        });

        this.socketTask.onMessage((res) => {
            const messageStore = useMessageStore()
            console.log('Received message:', res.data)
            // 处理收到的消息逻辑
            const message = JSON.parse(res.data)
            messageStore.addSessionBySessionAndUserId(message.session,message.createUser)
            messageStore.addMessageToSession({
                session: message.session,
                createUser: message.createUser,
                content: message.content,
                videoId: message.videoId,
                createTime: message.createTime
            })
        });

        this.socketTask.onClose(() => {
            console.log('WebSocket 连接已关闭');
        });
    }

    send(message: string) {
        if (this.socketTask) {
            this.socketTask.send({ data: message });
        } else {
            console.error('WebSocket 连接未打开');
        }
    }

    close() {
        if (this.socketTask) {
            this.socketTask.close({
                code: 1000,
                reason: 'Closed by client'
            });
            WebSocketService.instance = null; // 重置实例为 null，以便下次重新创建
        }
    }
}

export default WebSocketService;
