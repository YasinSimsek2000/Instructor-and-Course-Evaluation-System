package com.ices4hu.demo.service;

import com.ices4hu.demo.entity.Question;
import com.ices4hu.demo.entity.Survey;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface SurveyService {



    Survey findById(Long id) throws Exception;

    Survey save(Survey survey) throws Exception;

    Survey update(Long id, Survey survey) throws Exception;

    Survey deleteById(Long id) throws Exception;

    List<Survey> getSurveysByInstructorId(Long id);

    List<Survey> findOpenSurveysByDepartment(Long departmentId, Date now);

    default List<Survey> getAllList() throws Exception {
        return getAllList(Sort.by(Sort.Direction.ASC, "id"));
    }

    List<Survey> getAllList(Sort sort) throws Exception;


}
