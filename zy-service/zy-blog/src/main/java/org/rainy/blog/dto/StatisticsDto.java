package org.rainy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatisticsDto {

    private Long articleCount;

    private Long followCount;

    private Long tagCount;

    private Long categoryCount;

}
