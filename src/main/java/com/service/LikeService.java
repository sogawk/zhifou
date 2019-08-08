package com.service;

public interface LikeService {
    long like(int userId, int entityType, int entityId);

    long disLike(int userId, int entityType, int entityId);

    long getLikeCount(int entityType, int entityId);

    int getStatus(int userId, int entityType, int entityId);
}
