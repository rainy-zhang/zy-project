package org.rainy.project.repository;

import org.rainy.project.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Integer> {

    @Query(nativeQuery = true, value = "SELECT tag_id FROM t_article_tag WHERE article_id = :articleId")
    List<Integer> findTagIdsByArticleId(@Param("articleId") Integer articleId);

    void deleteByArticleId(Integer articleId);

}
