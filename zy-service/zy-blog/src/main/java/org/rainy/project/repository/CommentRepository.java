package org.rainy.project.repository;

import org.rainy.project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {

    List<Comment> findByArticleId(Integer articleId);

}
