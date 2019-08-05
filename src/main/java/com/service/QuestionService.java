package com.service;

import com.bean.Question;

import java.util.List;

public interface QuestionService {

    int addQuestion(Question question);

    List<Question> selectQuestion(int userId, int offset, int limit);

    Question selectQuestionById(int questionId);
}
