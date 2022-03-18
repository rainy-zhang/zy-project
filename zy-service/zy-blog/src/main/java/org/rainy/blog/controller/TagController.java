package org.rainy.blog.controller;

import org.rainy.blog.entity.Tag;
import org.rainy.blog.param.TagParam;
import org.rainy.blog.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/tag")
@RestController
public class TagController {
    
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
    
    @GetMapping(value = "/name")
    public List<Tag> tags(@PathVariable("name") String name) {
        return tagService.tags(name);
    }
    
    @PostMapping(value = "/save")
    public void save(@RequestBody TagParam tagParam) {
        tagService.save(tagParam);
    }
    
    @PutMapping(value = "/update")
    public void update(@RequestBody TagParam tagParam) {
        tagService.update(tagParam);
    }
    
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id) {
        tagService.delete(id);
    }
    
}
