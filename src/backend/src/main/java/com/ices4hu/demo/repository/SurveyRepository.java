package com.ices4hu.demo.repository;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Optional<Survey> getSurveyById(Long id);

    Optional<Survey> getSurveyByCreator(AppUser appUser);
   List<Survey> getAllByCreator(AppUser appUser);



    @Query("SELECT s FROM survey s WHERE :now >= s.startDate AND :now <= s.endDate AND :departmentId = s.departmentId")
    List<Survey> findOpenSurveysByDepartment(Long departmentId, Date now);


    @Modifying
    @Query("update survey set " +
            "name =:name," +
            "updatedAt =:updatedAt," +
            "startDate =:startDate," +
            "endDate =:endDate" +
            " where id =:id")
    void updateSurvey(
            @Param("id") long id,
            @Param("name") String name,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("updatedAt") Date updatedAt
    );
}
