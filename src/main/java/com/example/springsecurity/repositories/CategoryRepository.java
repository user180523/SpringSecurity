package com.example.springsecurity.repositories;

import com.example.springsecurity.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
   Category findByName(String name);
}
