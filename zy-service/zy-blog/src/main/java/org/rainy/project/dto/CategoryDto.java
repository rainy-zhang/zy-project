package org.rainy.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoryDto {

    private Integer id;

    private String name;

    private Integer seq;

    private String description;

    private Long articleCount;

}
