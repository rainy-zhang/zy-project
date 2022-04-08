package org.rainy.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Author {
    
    private String name;
    
    private String description;

    private String city;

    private String email;

    private String wechat;

    @JsonProperty(value = "QQ")
    private String QQ;
    
    private String github;

    /**
     * 经历
     */
    private List<Experience> experiences;
    
    private String status;
    
    private String dream;

    /**
     * 技能
     */
    private List<String> skills;

    @Data
    private static class Experience {
        private String companyName;
        private String start;
        private String end;
        private String jobDescription;
    }
    
}
