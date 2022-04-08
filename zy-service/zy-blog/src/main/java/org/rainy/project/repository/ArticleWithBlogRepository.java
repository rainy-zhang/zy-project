package org.rainy.project.repository;

import org.rainy.project.entity.ArticleWithBlobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleWithBlogRepository extends JpaRepository<ArticleWithBlobs, Integer> {
}
