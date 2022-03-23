package org.rainy.blog.repository;

import org.rainy.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
    Category findByArticleId(Integer articleId);
    
}
