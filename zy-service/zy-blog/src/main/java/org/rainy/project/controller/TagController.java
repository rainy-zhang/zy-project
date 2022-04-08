package org.rainy.project.controller;

import org.rainy.project.entity.Tag;
import org.rainy.project.param.TagParam;
import org.rainy.project.service.TagService;
import org.rainy.project.beans.PageQuery;
import org.rainy.project.beans.PageResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/tag")
@RestController
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping(value = "/tags")
    public PageResult<Tag> tags(@RequestBody PageQuery pageQuery) {
        return tagService.tags(pageQuery);
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
