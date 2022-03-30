package org.rainy.search.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首先根据keyword去匹配文章、标签、分类
 * 对keyword进行分词，再次去匹配文章
 */
@RestController
public class SearchController {
    
    @PostMapping(value = "/search")
    public Object search() {
        return null;
    }

}
