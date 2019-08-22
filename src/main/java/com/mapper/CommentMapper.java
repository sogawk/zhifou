package com.mapper;

import com.bean.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    int insertComment(Comment comment);

    int deleteComment(int commentId);

    int updateComment(Comment comment);

    List<Comment> selectAllComments(int entityId, int entityType);

    Comment getCommentById(int commentId);

    int getUserCommentCount(int userId);

}
