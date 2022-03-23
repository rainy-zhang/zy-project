package org.rainy.blog.controller;

import org.rainy.blog.dto.Statistics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/statistics")
public class StatisticsController {

    @GetMapping
    public Statistics statistics() {
        return Statistics.builder()
                .articleCount(100L)
                .followCount(50L)
                .build();
    }

}
