package org.rainy.blog.dto;

import lombok.Data;
import lombok.Getter;
import org.rainy.blog.entity.Article;
import org.rainy.blog.entity.Category;
import org.rainy.blog.entity.Comment;
import org.rainy.blog.entity.Tag;
import org.rainy.common.util.CommonUtils;

import java.util.List;

@Data
public class ArticleDto {

    private Integer id;

    private String title;

    private String summary;

    private Integer userId;

    private Long reads;

    private Long likes;

    private String ago;

    private Category category;

    private List<Tag> tagList;

    private List<Comment> commentList;

    private Long comments;

    private ArticleDto(Builder builder) {
        this.id = builder.article.getId();
        this.title = builder.article.getTitle();
        this.summary = builder.article.getSummary();
        this.userId = builder.article.getUserId();
        this.reads = builder.article.getReads();
        this.likes = builder.article.getLikes();
        this.comments = builder.article.getComments();
        this.ago = CommonUtils.ago(builder.article.getCreateTime());
        this.category = builder.category;
        this.tagList = builder.tagList;
        this.commentList = builder.commentList;
    }

    @Getter
    public static class Builder {

        private Article article;

        private Category category;

        private List<Tag> tagList;

        private List<Comment> commentList;

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

        public Builder tagList(List<Tag> tagList) {
            this.tagList = tagList;
            return this;
        }

        public Builder commentList(List<Comment> commentList) {
            this.commentList = commentList;
            return this;
        }

    }

}
