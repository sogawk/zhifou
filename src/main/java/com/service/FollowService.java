package com.service;

import java.util.List;

public interface FollowService {
    boolean follow(int userId, int entityType, int entityId);

    boolean unfollow(int userId, int entityType, int entityId);

    List<Integer> getFollowers(int entityType, int entityId, int offset, int count);

    List<Integer> getFollowees(int userId, int entityType, int offset, int count);

    long getFollowerCount(int entityType, int entityId);

    long getFolloweeCount(int UserId, int entityType);

    boolean isFollower(int userId, int entityType, int entityId);
}
