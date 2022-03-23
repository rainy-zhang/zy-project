package org.rainy.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.dto.CategoryDto;
import org.rainy.blog.service.CategoryService;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyu
 * @description:
 * @date: in 2022/3/23 9:20 PM
 */
@Slf4j
@RequestMapping(value = "/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/categories")
    public PageResult<CategoryDto> categories(@RequestBody PageQuery pageQuery) {
        return categoryService.categories(pageQuery);
    }

}
