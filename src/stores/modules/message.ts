import { defineStore } from 'pinia';
import { ref } from 'vue';
import type {SessionType, MessageType} from "@/types/MessageType";
import type {UserChatType} from "@/types/UserType";

export const useMessageStore = defineStore(
    'message',
    () => {
        const sessions = ref<SessionType[]>([]);

        // 获取特定会话
        const getSession = (sessionId: number): SessionType | undefined => {
            return sessions.value.find(session => session.session === sessionId);
        };

        // 通过用户ID获取特定会话
        const getSessionByUserId = (userId: number): SessionType | undefined => {
            return sessions.value.find(session => session.userId === userId);
        };

        // 获取特定会话
        // 添加新的函数实现根据会话ID和用户ID添加新的会话
        const addSessionBySessionAndUserId = (sessionId: number, userId: number) => {
            const existingSession = getSession(sessionId);
            if (!existingSession) {
                const newSession: SessionType = {
                    session: sessionId,
                    userId: userId,
                    messageList: [],
                    nickname: '',
                    profile: ''
                };
                sessions.value.unshift(newSession);
            }
        };

        // 更新与会话有关的用户信息
        const updateChatUserInfo = (userInfoList: UserChatType[]) => {
            for (const session of sessions.value) {
                const userChat = userInfoList.find(user => user.id === session.userId);
                if (userChat) {
                    session.nickname = userChat.nickname;
                    session.profile = userChat.profile;
                }
            }
        };

        // 获取所有session中的userId，返回一个userId的数组
        const getAllUserIds = (): number[] => {
            const userIds: number[] = [];
            for (const session of sessions.value) {
                userIds.push(session.userId);
            }
            return userIds;
        };

        // 获取所有会话
        const getAllSessions = (): SessionType[] => {
            return sessions.value;
        };

        // 删除指定会话
        const deleteSession = (sessionId: number) => {
            const index = sessions.value.findIndex(session => session.session === sessionId);
            if (index !== -1) {
                sessions.value.splice(index, 1);
            }
        };

        // 添加会话
        const addSessionToFront = (session: SessionType) => {
            const existingIndex = sessions.value.findIndex(existingSession => existingSession.session === session.session);
            if (existingIndex !== -1) {
                sessions.value.splice(existingIndex, 1);
            }
            sessions.value.unshift(session);
        };

        const addMessageToSession = (message: MessageType) => {
            const session = getSession(message.session);
            if (session) {
                session.messageList.push(message);
                addSessionToFront(session); // 更新会话顺序
            }
        };

        // 清空所有session
        const clearAllSessions = () => {
            sessions.value = [];
        };

        // 根据传入的SessionType数组加载所有session
        const loadAllSessions = (newSessions: SessionType[]) => {
            sessions.value = newSessions;
        };


        return {
            sessions,
            getSession,
            getSessionByUserId,
            getAllSessions,
            deleteSession,
            addSessionToFront,
            addMessageToSession,
            addSessionBySessionAndUserId,
            updateChatUserInfo,
            getAllUserIds,
            clearAllSessions,
            loadAllSessions
        };
    },
    {
        persist: {
            storage: {
                getItem(key) {
                    return uni.getStorageSync(key);
                },
                setItem(key, value) {
                    uni.setStorageSync(key, value);
                },
            },
        },
    }
);
