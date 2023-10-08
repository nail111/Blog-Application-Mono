package org.example.repository;

import org.example.entity.Category;
import org.example.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // No needed because of SimpleJpaRepository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCategory(Category category);
}