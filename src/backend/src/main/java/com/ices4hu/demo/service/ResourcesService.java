package com.ices4hu.demo.service;

import com.ices4hu.demo.entity.Resources;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResourcesService {

    Resources saveResources(Resources resources);
    List<Resources> getResourcesByDepartmentId(Long id);
    Resources removeResourcesById(Long id);
}
