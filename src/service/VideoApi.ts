import {http} from "@/utils/http";
import type {VideoType} from "@/types/VideoType";

export const getVideoLists = () => {
	return http<VideoType[]>({
		method: 'GET',
		url: '/video/list',
		data: {

		}
	})
}