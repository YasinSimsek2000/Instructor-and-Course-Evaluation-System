package com.ices4hu.demo.service;

import com.ices4hu.demo.entity.Answer;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {
    Answer findById(Long id) throws Exception;

    Answer save(Answer answer) throws Exception;

    Answer deleteById(Long id) throws Exception;

    Answer update(Long id, Answer answer) throws Exception;

    List<Answer> getAllAnswersToQuestion(Long questionId) throws Exception;

    default List<Answer> getAllList() throws Exception {
        return getAllList(Sort.by(Sort.Direction.ASC, "id"));
    }

    List<Answer> getAllList(Sort sort) throws Exception;

    Answer getById(Long answerId) throws Exception;

}
