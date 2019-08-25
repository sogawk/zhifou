package com.util;

import com.bean.EntityType;

public class RedisKeyUtil {
    //点赞
    public static String getLikeKey(int entityType, int entityId) {
        return "like:" + entityType + ":" + entityId;
    }

    //踩赞
    public static String getDisLikeKey(int entityType, int entityId) {
        return "dislike:" + entityType + ":" + entityId;
    }

    //异步队列
    public static String getEventQueueKey() {
        return "Event_Queue";
    }

    //某个实体的粉丝的key
    public static String getFollowerKey(int entityType, int entityId) {
        return "FOLLOWER:" + entityType + ":" + entityId;
    }

    //获取某个用户的关注列表
    public static String getFolloweeKey(int userId, int entityType) {
        return "FOLLOWEE:" + entityType + ":" + userId;
    }

    //获取时间轴的key
    public static String getTimeLineKey(int userId) {
        return "TIMELINE:" + userId;
    }
}
