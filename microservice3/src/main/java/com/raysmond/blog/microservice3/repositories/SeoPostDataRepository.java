package com.raysmond.blog.microservice3.repositories;

import com.raysmond.blog.microservice3.models.SeoPostData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeoPostDataRepository extends JpaRepository<SeoPostData, Long> {
}
