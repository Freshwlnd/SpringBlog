package com.raysmond.blog.microservice0.repositories;

import com.raysmond.blog.microservice0.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Raysmond<i @ raysmond.com>.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
