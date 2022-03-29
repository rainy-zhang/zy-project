package org.rainy.blog.dto;

import lombok.Data;

import java.util.List;

@Data
public class Author {

    private String name;

    private String description;

    private String city;

    private String email;

    private String wechat;

    private String github;

    /**
     * 经历
     */
    private List<String> experiences;

    private String status;

    private String dream;

    /**
     * 技能
     */
    private List<String> skills;

}
