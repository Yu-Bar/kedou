import {http} from "@/utils/http";
import type {CommentCommitType, CommentType} from "@/types/CommentType";

export const getCommentListByVideoId = (videoId: number) => {
	return http<CommentType[]>({
		method: 'GET',
		url: `/comment/list/${videoId}`,
	})
}

export const commitComment = (data: CommentCommitType) => {
	return http<CommentType[]>({
		method: 'POST',
		url: `/comment/commit`,
		data,
	})
}