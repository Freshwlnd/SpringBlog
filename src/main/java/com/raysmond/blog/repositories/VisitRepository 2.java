package com.raysmond.blog.repositories;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.User;
import com.raysmond.blog.models.Visit;
import com.raysmond.blog.models.SeoRobotAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("SELECT COUNT(DISTINCT v.clientIp) " +
            "FROM Visit AS v " +
            //"LEFT JOIN v.robotsAgents AS ra " +
            //"ON v.userAgent LIKE concat('%', ra.userAgent, '%') "+
            "WHERE v.post = :post AND v.isAdmin = FALSE " //+
            //"AND ra.id IS NULL "
    )
    Long getUniquePostVisitsCount(@Param("post") Post post);

    @Query("SELECT v.clientIp, NULLIF(v.userAgent, '') " +
            "FROM Visit AS v " +
            "WHERE v.post = :post AND v.isAdmin = FALSE " +
            "GROUP BY v.clientIp, NULLIF(v.userAgent, '') "
    )
    List<Object> getVisitsByPostAndIsAdminIsFalse(@Param("post") Post post);

}
