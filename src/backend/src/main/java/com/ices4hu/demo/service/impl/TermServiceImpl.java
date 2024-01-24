package com.ices4hu.demo.service.impl;

import com.ices4hu.demo.entity.Term;
import com.ices4hu.demo.repository.TermRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class TermServiceImpl {

    final TermRepository termRepository;

    // Returns the term with the latest term start date
    public Term getCurrentTerm(){
        return termRepository.findAll().stream()
                .reduce(
                        new Term(new Date(0L), new Date(1L)),
         (t1, t2) -> t1.getStartDate().before(t2.getStartDate()) ? t2 : t1);
    }

    public boolean hasTerm(){
        return termRepository.findAll().size() > 0;
    }

    public void deleteById(Long id){
        termRepository.removeTermById(id).orElseThrow();
    }

    public Term addTerm(Term term){
        return termRepository.save(term);
    }


    public void updateTerm(Term term){
        Term currentTerm = getCurrentTerm();

        if (currentTerm == null) {
            addTerm(term);
            return;
        }

        currentTerm.setName(term.getName());
        currentTerm.setStartDate(term.getStartDate());
        currentTerm.setEndDate(term.getEndDate());
        currentTerm.setTermType(term.getTermType());

        termRepository.save(currentTerm);
    }


    public Term deleteTermById(Long id){
        return termRepository.removeTermById(id).orElseThrow();
    }

}
