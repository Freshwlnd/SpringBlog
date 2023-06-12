package com.raysmond.blog.microservice2.repositories;

import com.raysmond.blog.microservice2.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Raysmond<i @ raysmond.com>.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
