package com.raysmond.blog.repositories;

import com.raysmond.blog.models.SeoPostData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeoPostDataRepository extends JpaRepository<SeoPostData, Long> {
}
