package com.yubar.kedou.enumeration;

/**
 * 数据库操作类型
 */
public enum OperationType {

    /**
     * 更新操作
     */
    UPDATE,

    /**
     * 插入操作
     */
    INSERT,

    /**
     * 插入（但是只有time，用于新建用户）
     */
    CREATE,
    /**
     * 发布视频
     */
    PUBLISH,

}
