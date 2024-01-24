package com.ices4hu.demo.repository;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.ResponseSheet;
import com.ices4hu.demo.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ResponseSheetRepository extends JpaRepository<ResponseSheet, Long> {

    Optional<ResponseSheet> findBySurveyAndResponder(Survey survey, AppUser responder);

    Optional<ResponseSheet> findByResponder(AppUser responder);

    @Modifying
    @Query("update response_sheet set " +
            "survey =:survey," +
            "responder =:responder," +
            "updatedAt =:updatedAt" +
            " where id =:id")
    void updateResponseSheet(
            @Param("id") long id,
            @Param("survey") Survey survey,
            @Param("responder") AppUser responder,
            @Param("updatedAt") Date updatedAt
    );

}
