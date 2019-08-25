package com.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.async.EventHandler;
import com.async.EventModel;
import com.async.EventType;
import com.bean.EntityType;
import com.bean.Feed;
import com.bean.Question;
import com.bean.User;
import com.service.FeedService;
import com.service.FollowService;
import com.service.QuestionService;
import com.service.UserService;
import com.util.JedisAdapter;
import com.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FeedHandler implements EventHandler {
    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @Autowired
    FeedService feedService;

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    QuestionService questionService;

    private String buildFeedData(EventModel model) {
        Map<String, String> map = new HashMap<>();
        User actor = userService.getUserById(model.getActorId());
        if (actor == null) {
            return null;
        }

        map.put("userId", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getName());

        if (model.getEventType() == EventType.COMMENT ||
                (model.getEventType() == EventType.FOLLOW && model.getEntityType() == EntityType.Entity_Question)) {
            Question question = questionService.selectQuestionById(model.getEntityId());
            if (question == null) {
                return null;
            }

            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    @Override
    public void doHandler(EventModel model) {
        Feed feed = new Feed();
        feed.setCreatedDate(new Date());
        feed.setType(model.getEventType().getValue());
        feed.setUserId(model.getActorId());
        feed.setData(buildFeedData(model));
        if (feed.getData() == null) {
            return;
        }
        feedService.addFeed(feed);
        List<Integer> followers = followService.getFollowers(EntityType.Entity_User, model.getActorId(), 0, Integer.MAX_VALUE);

        //给所有的粉丝推送事件
        followers.add(0);
        for (Integer follower : followers) {
            String timeLineKey = RedisKeyUtil.getTimeLineKey(follower);
            jedisAdapter.lpush(timeLineKey, String.valueOf(feed.getId()));
        }
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.FOLLOW, EventType.COMMENT);
    }
}
