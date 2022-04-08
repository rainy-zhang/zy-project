package org.rainy.project.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.project.dto.Author;
import org.rainy.project.util.JsonMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthorService {

    private Author author = null;
    
    public Author author() throws IOException {
        if (author == null) {
            return load();
        }
        return this.author;
    }
    
    public Author load() throws IOException {
        // TODO：这里获取的File是target/class目录下的，不对
        File authorFile = ResourceUtils.getFile("classpath:author.json");
        String authorStr = Files.lines(authorFile.toPath()).collect(Collectors.joining());
        author = JsonMapper.string2Object(authorStr, new TypeReference<>() {
        });
        return this.author;
    }

    public Author change(Author author) throws IOException {
        File authorFile = ResourceUtils.getFile("classpath:author.json");
        String authorStr = JsonMapper.object2String(author);
        Preconditions.checkNotNull(authorStr);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(authorFile, true))) {
            writer.write(authorStr);
        }
        return load();
    }

}
