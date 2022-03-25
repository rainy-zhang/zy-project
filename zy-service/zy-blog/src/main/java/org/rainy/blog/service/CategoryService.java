package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.dto.CategoryDto;
import org.rainy.blog.entity.Category;
import org.rainy.blog.repository.CategoryRepository;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.constant.CommonStatus;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.util.BeanValidator;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ArticleService articleService;

    // 处理循环依赖
    public CategoryService(CategoryRepository categoryRepository, @Lazy ArticleService articleService) {
        this.categoryRepository = categoryRepository;
        this.articleService = articleService;
    }

    public Category findById(Integer id) {
        Preconditions.checkNotNull(id, "分类ID不能为空");
        return categoryRepository.findById(id).orElseThrow(() -> new BeanNotFoundException("分类已不存在"));
    }

    public PageResult<CategoryDto> categories(PageQuery pageQuery) {
        BeanValidator.validate(pageQuery, ValidateGroups.SELECT.class);
        Page<Category> page = categoryRepository.findAll(pageQuery.convert());
        Page<CategoryDto> categoryPage = page.map(category -> CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .seq(category.getSeq())
                .description(category.getDescription())
                .articleCount(articleService.countByCategoryId(category.getId()))
                .build());
        return PageResult.of(categoryPage);
    }

    public Long count() {
        Specification<Category> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Category.COLUMN.STATUS), CommonStatus.VALID.getCode());
        return categoryRepository.count(specification);
    }
}
