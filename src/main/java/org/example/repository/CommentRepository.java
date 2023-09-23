package org.example.repository;

import org.example.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // No needed because of SimpleJpaRepository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}