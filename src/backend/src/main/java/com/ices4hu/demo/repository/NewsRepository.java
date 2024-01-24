package com.ices4hu.demo.repository;

import com.ices4hu.demo.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<Resources, Long> {
    List<Resources> findAllByDepartmentId(Long id);

    Resources removeNewsById(Long id);
}
