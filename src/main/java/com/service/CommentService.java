package com.service;

import com.bean.Comment;

import java.util.List;

public interface CommentService {
    int addComment(Comment comment);

    List<Comment> getCommentsByEntity(int entityType, int entityId);
}
