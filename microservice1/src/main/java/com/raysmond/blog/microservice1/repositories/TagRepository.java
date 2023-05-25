package com.raysmond.blog.microservice1.repositories;

import com.raysmond.blog.common.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Raysmond<i@raysmond.com>.
 */
public interface TagRepository extends JpaRepository<Tag, Long>{
    Tag findByName(String name);
}
