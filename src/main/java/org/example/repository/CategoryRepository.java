package org.example.repository;

import org.example.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository // No needed because of SimpleJpaRepository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}