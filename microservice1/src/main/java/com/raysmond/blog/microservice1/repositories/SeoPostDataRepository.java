package com.raysmond.blog.microservice1.repositories;

import com.raysmond.blog.common.models.SeoPostData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeoPostDataRepository extends JpaRepository<SeoPostData, Long> {
}
