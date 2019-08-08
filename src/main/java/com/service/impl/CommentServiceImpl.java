package com.service.impl;

import com.bean.Comment;
import com.mapper.CommentMapper;
import com.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public int addComment(Comment comment) {
        return commentMapper.insertComment(comment) >0 ?comment.getId():0;
    }

    @Override
    public List<Comment> getCommentsByEntity(int entityType, int entityId) {
        return commentMapper.selectAllComments(entityId, entityType);
    }

    @Override
    public Comment getCommentById(int commentId) {
        return commentMapper.getCommentById(commentId);
    }
}
