package com.service.impl;

import com.bean.Feed;
import com.mapper.FeedMapper;
import com.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    FeedMapper feedMapper;

    @Override
    public Feed getFeedById(int id) {
        return feedMapper.getFeedById(id);
    }

    @Override
    public List<Feed> getUsersFeeds(int maxId, List<Integer> userIds, int counts) {
        return feedMapper.selectUserFeeds(maxId, userIds, counts);
    }

    @Override
    public boolean addFeed(Feed feed) {
        feed.setId(feedMapper.addFeed(feed));
        return feed.getId() > 0;
    }
}
