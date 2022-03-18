package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.entity.Comment;
import org.rainy.blog.param.CommentParam;
import org.rainy.blog.repository.BlogRepository;
import org.rainy.blog.repository.CommentRepository;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.util.BeanValidator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    public CommentService(CommentRepository commentRepository, BlogRepository blogRepository) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
    }

    public PageResult<Comment> pageResult(PageQuery pageQuery, Integer blogId) {
        Preconditions.checkNotNull(blogId, "博客id不能为空");
        Pageable pageable = pageQuery.convert();
        Example<Comment> example = Example.of(
                Comment.builder()
                        .blogId(blogId)
                        .build()
        );
        Page<Comment> page = commentRepository.findAll(example, pageable);
        return PageResult.of(page);
    }

    public void save(CommentParam commentParam) {
        BeanValidator.validate(commentParam);
        Comment comment = commentParam.convert();
        blogRepository.findById(comment.getBlogId()).orElseThrow(() -> new BeanNotFoundException("博客不存在"));
        commentRepository.save(comment);
    }

}
