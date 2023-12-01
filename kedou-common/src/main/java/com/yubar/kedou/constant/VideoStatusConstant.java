package com.yubar.kedou.constant;
/**
 * Author:Yu-Bar
 * Date:2023/11/29-12:19
 */

/**
 *@ClassName VideoStatusConstant
 *@Description 视频状态常量
 *@Author Yu-Bar
 *@Date 2023/11/29 12:19
 *@Version 1.0
 **/
public class VideoStatusConstant {

    /**
     * 视频状态 0待审核 1审核未通过 2已发布 3封禁
     */
    public static final Integer REVIEWING = 0;
    public static final Integer FAIL_REVIEW = 1;
    public static final Integer PUBLISH = 2;
    public static final Integer BLOCKED = 3;

    /**
     * 视频删除状态 0未删除 1删除
     */
    public static final Integer UN_DELETE = 1;
    public static final Integer DELETE = 1;

    /**
     * 公开状态 0私密 1公开
     */
    public static final Integer OPEN = 1;
    public static final Integer PRIVACY = 0;
}
