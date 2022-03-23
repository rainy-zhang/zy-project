package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.entity.Category;
import org.rainy.blog.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    public Category findByArticleId(Integer articleId) {
        Preconditions.checkNotNull(articleId, "文章ID不能为空");
        return categoryRepository.findByArticleId(articleId);
    }
    
}
