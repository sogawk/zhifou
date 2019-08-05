package com.service.impl;

import com.bean.Question;
import com.mapper.QuestionMapper;
import com.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public int addQuestion(Question question) {
        return questionMapper.insertQuestion(question);
    }

    @Override
    public List<Question> selectQuestion(int userId, int offset, int limit) {
        return questionMapper.getLatestQuestion(userId, offset, limit);
    }

    @Override
    public Question selectQuestionById(int questionId) {
        return questionMapper.selectQuestionById(questionId);
    }
}
