package com.ices4hu.demo.repository;

import com.ices4hu.demo.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    Optional<Term> removeTermById(Long id);


//    @Modifying
//    @Query("update term set " +
//            "startDate =:startDate," +
//            "endDate =:endDate" +
//            " where id =:id")
//    void updateTerm(
//            @Param("id") long id,
//            @Param("startDate") Date startDate,
//            @Param("endDate") Date endDate
//    );


}
