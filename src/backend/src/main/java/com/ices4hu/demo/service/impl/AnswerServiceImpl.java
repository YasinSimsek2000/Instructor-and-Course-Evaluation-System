package com.ices4hu.demo.service.impl;


import com.ices4hu.demo.entity.Answer;
import com.ices4hu.demo.entity.Question;
import com.ices4hu.demo.repository.AnswerRepository;
import com.ices4hu.demo.repository.QuestionRepository;
import com.ices4hu.demo.service.AnswerService;
import com.ices4hu.demo.util.ICES4HUValidatorUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {

    final AnswerRepository answerRepository;
    final QuestionRepository questionRepository;


    @Override
    public Answer findById(Long id) throws Exception {
        return answerRepository.findById(id).orElseThrow();
    }

    @Override
    public Answer save(Answer answer) throws Exception {
        Optional<Answer> optionalAnswer = answerRepository.findById(answer.getId());

        if (optionalAnswer.isPresent()) {
            throw new Exception("Answer already exists with the id: " + answer.getId());
        }

        answerRepository.save(answer);

        return answer;
    }

    @Override
    public Answer deleteById(Long id) throws Exception {
        Answer answer = findById(id);
        answerRepository.deleteById(id);
        return answer;
    }

    @Override
    public Answer update(Long id, Answer answer) throws Exception {
        Optional<Answer> existingAnswerOptional = answerRepository.findById(id);

        if (existingAnswerOptional.isEmpty()) {
            throw new Exception("Answer not found with ID: " + id);
        }

        Answer existingAnswer = existingAnswerOptional.get();

        existingAnswer.setRating(answer.getRating());
        existingAnswer.setAnswer(answer.getAnswer());
        existingAnswer.setQuestionType(answer.getQuestionType());
        existingAnswer.setQuestion(answer.getQuestion());
        //existingAnswer.setOwner(answer.getOwner());
        existingAnswer.setUpdatedAt(ICES4HUValidatorUtil.now());

        answerRepository.save(existingAnswer);

        return existingAnswer;
    }

    @Override
    public List<Answer> getAllAnswersToQuestion(Long questionId) throws Exception {
        Optional<Question> questionOptional = questionRepository.getQuestionsById(questionId);

        if (questionOptional.isEmpty()) {
            throw new Exception("Question not found with ID: " + questionId);
        }

        Question question = questionOptional.get();

        return new ArrayList<>(question.getAnswers());
    }

    @Override
    public List<Answer> getAllList(Sort sort) throws Exception {
        return answerRepository.findAll().stream().toList();
    }

    @Override
    public Answer getById(Long answerId) throws Exception {
        return answerRepository.findById(answerId).orElseThrow();
    }
}
