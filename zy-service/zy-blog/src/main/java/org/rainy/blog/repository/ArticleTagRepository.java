package org.rainy.blog.repository;

import org.rainy.blog.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Integer> {
    
    @Query(nativeQuery = true, value = "SELECT tagId FROM t_article_tag WHERE article_id = :articleId")
    List<Integer> findTagIdsByArticleId(Integer articleId);
    
    void deleteByArticleId(Integer articleId);
    
}
