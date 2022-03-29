package org.rainy.blog.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ArchiveDto {

    private Integer id;

    private String name;

    private String description;

    private List<Integer> articleIds;

    private Integer articleCount;

}
