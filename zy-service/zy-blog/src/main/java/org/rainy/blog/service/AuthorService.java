package org.rainy.blog.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.dto.Author;
import org.rainy.common.util.JsonMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthorService {

    private Author author = null;
    
    public Author author() throws IOException {
        if (author == null) {
            load();
        }
        return this.author;
    }
    
    public void load() throws IOException {
        File authorFile = ResourceUtils.getFile("classpath:author.json");
        String authorStr = Files.lines(authorFile.toPath()).collect(Collectors.joining());
        author = JsonMapper.string2Object(authorStr, new TypeReference<>() {
        });
    }

}
