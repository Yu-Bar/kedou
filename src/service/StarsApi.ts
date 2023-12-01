import {http} from "@/utils/http";
import type {VideoType} from "@/types/VideoType";

export const starVideo = (videoId: number) => {
	return http({
		method: 'POST',
		url: `/star?videoId=${videoId}`,
	})
}

export const disStarVideo = (videoId: number) => {
	return http({
		method: 'DELETE',
		url: `/star?videoId=${videoId}`,
	})
}

export const getStarVideoList = (userId: number) => {
	return http<VideoType[]>({
		method: 'GET',
		url: `/star/list?userId=${userId}`,
	})
}