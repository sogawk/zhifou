package com.util;

import com.bean.EntityType;

public class RedisKeyUtil {
    public static String getLikeKey(int entityType, int entityId) {
        return "like:" + entityType + ":" + entityId;
    }

    public static String getDisLikeKey(int entityType, int entityId) {
        return "dislike:" + entityType + ":" + entityId;
    }

    public static String getEventQueueKey() {
        return "Event_Queue";
    }
}
