import {http} from "@/utils/http";
import type {VideoPublishType, VideoType} from "@/types/VideoType";

export const getVideoLists = () => {
	return http<VideoType[]>({
		method: 'GET',
		url: '/video/list',
	})
}

export const getFriendsVideoList = () => {
	return http<VideoType[]>({
		method: 'GET',
		url: '/video/list/friends',
	})
}

export const getVideoListsWithAuthor = (id: number) => {
	return http<VideoType[]>({
		method: 'GET',
		url: `/video/list?author=${id}`,
	})
}

export const publishVideo = (data: VideoPublishType) => {
	return http({
		method: 'POST',
		url: '/video/upload',
		data,
	})
}