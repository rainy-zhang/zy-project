package org.rainy.blog.repository;

import org.rainy.blog.entity.ArchiveArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveArticleRepository extends JpaRepository<ArchiveArticle, Integer> {
    
    @Query(value = "SELECT article_id FROM t_archive_article WHERE archive_id = :archiveId", nativeQuery = true)
    List<Integer> findArticleIdsByArchiveId(@Param("archiveId") Integer archiveId);
    
}
