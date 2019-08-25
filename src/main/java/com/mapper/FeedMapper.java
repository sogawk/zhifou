package com.mapper;

import com.bean.Feed;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedMapper {
    int addFeed(Feed feed);

    Feed getFeedById(int id);

    List<Feed> selectUserFeeds(@Param("maxId")int maxId ,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);
}
