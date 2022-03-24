package org.rainy.blog.controller;

import org.rainy.blog.dto.Author;
import org.rainy.blog.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public Author author() throws IOException {
        return authorService.author();
    }
    
    @GetMapping(value = "/load")
    public void load() throws IOException {
        authorService.load();
    }

}
