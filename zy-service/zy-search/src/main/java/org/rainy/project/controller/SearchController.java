package org.rainy.project.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    
    @PostMapping(value = "/search")
    public Object search() {
        return null;
    }

}
