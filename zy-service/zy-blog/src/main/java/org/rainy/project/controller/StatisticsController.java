package org.rainy.project.controller;

import org.rainy.project.dto.StatisticsDto;
import org.rainy.project.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public StatisticsDto statistics() {
        return statisticsService.statistics();
    }

}
