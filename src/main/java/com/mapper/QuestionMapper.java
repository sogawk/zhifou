package com.mapper;

import com.bean.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {
    int insertQuestion(Question question);

    List<Question> getLatestQuestion(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    Question selectQuestionById(int questionId);
}
