package com.ices4hu.demo.service.impl;

import com.ices4hu.demo.entity.Resources;
import com.ices4hu.demo.repository.NewsRepository;
import com.ices4hu.demo.service.ResourcesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
@Component
public class ResourcesServiceImpl implements ResourcesService {

    NewsRepository newsRepository;

    @Override
    public Resources saveResources(Resources resources) {
        return newsRepository.save(resources);
    }

    @Override
    public List<Resources> getResourcesByDepartmentId(Long id) {
        return newsRepository.findAllByDepartmentId(id);
    }

    @Override
    public Resources removeResourcesById(Long id) {
        return newsRepository.removeNewsById(id);
    }
}
