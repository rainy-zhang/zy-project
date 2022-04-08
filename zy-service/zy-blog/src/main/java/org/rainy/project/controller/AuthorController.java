package org.rainy.project.controller;

import org.rainy.project.dto.Author;
import org.rainy.project.service.AuthorService;
import org.springframework.web.bind.annotation.*;

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
    public Author load() throws IOException {
        return authorService.load();
    }
    
    @PutMapping(value = "/change")
    public Author change(@RequestBody Author author) throws IOException {
        return authorService.change(author);
    }

}
