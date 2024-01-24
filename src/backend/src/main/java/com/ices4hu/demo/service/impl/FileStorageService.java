package com.ices4hu.demo.service.impl;

import java.io.IOException;
import java.util.stream.Stream;

import com.ices4hu.demo.entity.FileDB;
import com.ices4hu.demo.repository.FileDBRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
@AllArgsConstructor
public class FileStorageService {


    private FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(FileDB);
    }

    public void removeByUUID(String UUID){
        if(UUID == null) return;
        fileDBRepository.deleteById(UUID);
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
