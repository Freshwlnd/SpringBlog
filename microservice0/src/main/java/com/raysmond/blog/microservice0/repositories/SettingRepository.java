package com.raysmond.blog.microservice0.repositories;

import com.raysmond.blog.microservice0.models.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Raysmond<i @ raysmond.com>
 */
@Repository
@Transactional
public interface SettingRepository extends JpaRepository<Setting, Long> {
    Setting findByKey(String key);
}
