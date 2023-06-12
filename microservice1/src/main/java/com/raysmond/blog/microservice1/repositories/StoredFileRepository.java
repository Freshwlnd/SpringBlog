package com.raysmond.blog.microservice1.repositories;

import com.raysmond.blog.microservice1.models.StoredFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoredFileRepository extends JpaRepository<StoredFile, Long> {
    StoredFile findById(Long id);

    StoredFile findByName(String name);
}
