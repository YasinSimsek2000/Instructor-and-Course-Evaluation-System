package com.ices4hu.demo.repository;

import com.ices4hu.demo.entity.Question;
import com.ices4hu.demo.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> getQuestionsById(Long id);

    @Modifying
    @Query("update question set " +
            "description =:description," +
            "updatedAt =:updatedAt" +
            " where id =:id")
    void updateQuestion(
            @Param("id") long id,
            @Param("description")String description,
            @Param("updatedAt") Date updatedAt
    );
}
