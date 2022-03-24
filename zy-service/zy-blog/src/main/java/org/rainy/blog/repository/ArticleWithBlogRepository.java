package org.rainy.blog.repository;

import org.rainy.blog.entity.ArticleWithBlobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleWithBlogRepository extends JpaRepository<ArticleWithBlobs, Integer> {
}
