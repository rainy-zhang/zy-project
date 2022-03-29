package org.rainy.blog.dto;

import lombok.Data;
import lombok.Getter;
import org.rainy.blog.constant.ArticleStatus;
import org.rainy.blog.entity.Article;
import org.rainy.blog.entity.Category;
import org.rainy.blog.entity.Tag;
import org.rainy.common.util.CommonUtils;

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

    private ArticleDto(Builder builder) {
        this.id = builder.article.getId();
        this.title = builder.article.getTitle();
        this.summary = builder.article.getSummary();
        this.userId = builder.article.getUserId();
        this.reading = builder.article.getReading();
        this.likes = builder.article.getLikes();
        this.comments = builder.article.getComments();
        this.ago = CommonUtils.ago(builder.article.getCreateTime());
        this.category = builder.category;
        this.tags = builder.tags;
        this.status = builder.status;
    }

    @Getter
    public static class Builder {

        private Article article;

        private Category category;

        private List<Tag> tags;

        private String status;

        public ArticleDto build() {
            return new ArticleDto(this);
        }

        public Builder article(Article article) {
            this.article = article;
            return this;
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
