import {http} from "@/utils/http";
import type {VideoPublishType, VideoType} from "@/types/VideoType";

export const likeVideo = (videoId: number) => {
	return http({
		method: 'POST',
		url: `/likes?videoId=${videoId}`,
	})
}

export const dislikeVideo = (videoId: number) => {
	return http({
		method: 'DELETE',
		url: `/likes?videoId=${videoId}`,
	})
}

export const getLikeVideoList = (userId: number) => {
	return http<VideoType[]>({
		method: 'GET',
		url: `/likes/list?userId=${userId}`,
	})
}