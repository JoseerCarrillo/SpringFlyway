package com.empresa.migracion.repository;

import com.empresa.migracion.model.Category;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository 
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
