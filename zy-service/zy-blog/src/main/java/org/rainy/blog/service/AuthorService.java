package org.rainy.blog.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.dto.Author;
import org.rainy.common.util.JsonMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
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

    public Author load() throws IOException {
        File authorFile = ResourceUtils.getFile("classpath:author.json");
        String authorStr = Files.lines(authorFile.toPath()).collect(Collectors.joining());
        this.author = JsonMapper.string2Object(authorStr, new TypeReference<>() {
        });
        log.info("加载作者信息完成，author：{}", author);
        return author;
    }

    public Author change(Author author) throws IOException {
        File authorFile = ResourceUtils.getFile("classpath:author.json");
        String authorStr = JsonMapper.object2String(author);
        Preconditions.checkNotNull(authorStr);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(authorFile, false))) {
            writer.write(authorStr);
        }
        this.author = author;
        log.info("变更作者信息完成，author：{}", this.author);
        return author;
    }

}
