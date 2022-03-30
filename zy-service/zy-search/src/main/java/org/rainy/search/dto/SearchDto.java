package org.rainy.search.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SearchDto {
    
    private String keyword;
    
    private List<Integer> tagIds;
    
    private Integer categoryId;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
}
