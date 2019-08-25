package com.service;

import com.bean.Feed;
import com.mapper.FeedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FeedService {

    Feed getFeedById(int id);

    List<Feed> getUsersFeeds(int maxId, List<Integer> userIds, int counts);

    boolean addFeed(Feed feed);

}
