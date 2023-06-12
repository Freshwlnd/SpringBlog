package com.raysmond.blog.microservice2.repositories;

import com.raysmond.blog.microservice2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Raysmond<i @ raysmond.com>
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findAllByRoleOrderById(String role);
}
