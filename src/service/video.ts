import {http} from "@/utils/http";

export const getVideoLists= () => {
	return http({
		method: 'GET',
		url: '/video/list',
		data: {

		}
	})
}