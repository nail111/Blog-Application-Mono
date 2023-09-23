package org.example.repository;

import org.example.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // No needed because of SimpleJpaRepository
public interface PostRepository extends JpaRepository<Post, Long> {

}