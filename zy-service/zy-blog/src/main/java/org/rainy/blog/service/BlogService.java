package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.entity.Blog;
import org.rainy.blog.param.BlogParam;
import org.rainy.blog.repository.BlogRepository;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.util.BeanValidator;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlogService {
    
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }
    
    public PageResult<Blog> pageResult(PageQuery pageQuery) {
        BeanValidator.validate(pageQuery, ValidateGroups.SELECT.class);
        Page<Blog> blogPage = blogRepository.findAll(pageQuery.convert());
        return PageResult.of(blogPage);
    }
    
    public Blog findById(Integer id) {
        Preconditions.checkNotNull(id, "博客id不能为空");
        return blogRepository.findById(id).orElseThrow(() -> new BeanNotFoundException("博客不存在"));
    }
    
    public void save(BlogParam blogParam) {
        BeanValidator.validate(blogParam, ValidateGroups.INSERT.class);
        Blog blog = blogParam.convert();
        blogRepository.save(blog);
        log.info("保存博客成功：{}", blog);
    }
    
    public void delete(Integer id) {
        Preconditions.checkNotNull(id);
        blogRepository.deleteById(id);
        log.info("删除博客成功：{}", id);
    }
    
    public void update(BlogParam blogParam) {
        BeanValidator.validate(blogParam, ValidateGroups.UPDATE.class);
        Blog blog = blogParam.convert();
        blogRepository.save(blog);
    }
    
}
