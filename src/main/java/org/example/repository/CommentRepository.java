package org.example.repository;

import org.example.entity.Comment;
import org.example.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // No needed because of SimpleJpaRepository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    @Query(nativeQuery = true, value = "SELECT * FROM comments WHERE post_id=?1")
    List<Comment> findAllCommentsByPostId(Long postId);
}