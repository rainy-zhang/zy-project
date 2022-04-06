package org.rainy.blog.controller;

import org.rainy.blog.entity.Tag;
import org.rainy.blog.param.TagParam;
import org.rainy.blog.service.TagService;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
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
