package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.entity.Comment;
import org.rainy.blog.param.CommentParam;
import org.rainy.blog.repository.CommentRepository;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.util.BeanValidator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Slf4j
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public PageResult<Comment> pageResult(PageQuery pageQuery, Integer articleId) {
        Preconditions.checkNotNull(articleId, "文章id不能为空");
        Pageable pageable = pageQuery.convert();
        Example<Comment> example = Example.of(
                Comment.builder()
                        .articleId(articleId)
                        .build()
        );
        Page<Comment> page = commentRepository.findAll(example, pageable);
        return PageResult.of(page);
    }

    public long countByArticleId(Integer articleId) {
        Preconditions.checkNotNull(articleId, "文章id不能为空");
        Specification<Comment> specification = (root, query,criteriaBuilder) -> criteriaBuilder.equal(root.get(Comment.COLUMN.ARTICLE_ID), articleId);
        return commentRepository.count(specification);
    }

    public void save(CommentParam commentParam) {
        BeanValidator.validate(commentParam);
        Comment comment = commentParam.convert();
        commentRepository.save(comment);
    }

}
