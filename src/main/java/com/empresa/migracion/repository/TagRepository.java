package com.empresa.migracion.repository;

import com.empresa.migracion.model.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long>{
    Optional<Tag> findByName(String name);
}
