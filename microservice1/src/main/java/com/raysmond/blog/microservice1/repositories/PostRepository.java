package com.raysmond.blog.microservice1.repositories;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.support.PostStatus;
import com.raysmond.blog.common.models.support.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import com.raysmond.blog.common.models.PageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPermalinkAndPostStatusAndDeleted(String permalink, PostStatus postStatus, Boolean deleted);

    Post findByIdAndPostStatusAndDeleted(Long postId, PostStatus postStatus, Boolean deleted);

     Page<Post> findAllByPostTypeAndDeleted(PostType postType, Pageable pageRequest, Boolean deleted);
//    Page<Post> findAllByPostTypeAndDeleted(PostType postType, PageRequest pageRequest, Boolean deleted);

     Page<Post> findAllByPostTypeAndPostStatusAndDeleted(PostType postType, PostStatus postStatus, Pageable pageRequest, Boolean deleted);
//    Page<Post> findAllByPostTypeAndPostStatusAndDeleted(PostType postType, PostStatus postStatus, PageRequest pageRequest, Boolean deleted);

    List<Post> findAllByPostTypeAndPostStatusAndDeleted(PostType postType, PostStatus postStatus, Boolean deleted);

     Page<Post> findAllByDeleted(Pageable pageRequest, Boolean deleted);
//    Page<Post> findAllByDeleted(PageRequest pageRequest, Boolean deleted);

    @Query("SELECT p FROM Post p INNER JOIN p.tags t WHERE t.name = :tag AND p.deleted = false")
     Page<Post> findByTag(@Param("tag") String tag, Pageable pageable);
//    Page<Post> findByTag(@Param("tag") String tag, @Param("pageable") PageRequest pageable);

    @Query("SELECT t.name, count(p) as tag_count from Post p " +
            "INNER JOIN p.tags t " +
            "WHERE p.postStatus = :status " +
            "AND p.deleted = false " +
            "GROUP BY t.id " +
            "ORDER BY tag_count DESC")
    List<Object[]> countPostsByTags(@Param("status") PostStatus status);
}

