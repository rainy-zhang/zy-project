package org.rainy.blog.dto;

import lombok.Data;
import lombok.Getter;
import org.rainy.blog.constant.ArticleStatus;
import org.rainy.blog.entity.Article;
import org.rainy.blog.entity.Category;
import org.rainy.blog.entity.Tag;
import org.rainy.common.util.CommonUtils;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDto {

    private Integer id;

    private String title;

    private String summary;

    private Integer userId;

    private Long reading;

    private Long likes;

    private String ago;

    private Category category;

    private List<Tag> tags;

    private Long comments;

    private String status;

    private String content;

    private String htmlContent;

    private ArticleDto(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.summary = builder.summary;
        this.content = builder.content;
        this.htmlContent = builder.htmlContent;
        this.userId = builder.userId;
        this.reading = builder.reading;
        this.likes = builder.likes;
        this.comments = builder.comments;
        this.ago = CommonUtils.ago(builder.createTime);
        this.category = builder.category;
        this.tags = builder.tags;
        this.status = builder.status;
    }

    @Getter
    public static class Builder {

        private Integer id;

        private String title;

        private String summary;

        private Integer userId;

        private Long reading;

        private Long likes;

        private LocalDateTime createTime;

        private Category category;

        private List<Tag> tags;

        private Long comments;

        private String status;

        private String content;

        private String htmlContent;
        
        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder htmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
            return this;
        }

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder reading(Long reading) {
            this.reading = reading;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder likes(Long likes) {
            this.likes = likes;
            return this;
        }

        public Builder comments(Long comments) {
            this.comments = comments;
            return this;
        }
        
        public ArticleDto build() {
            return new ArticleDto(this);
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder tags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Builder status(Integer status) {
            this.status = ArticleStatus.map.get(status);
            return this;
        }
    }

}
