package com.adntest.adn_test_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adntest.adn_test_system.entity.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, String> {
    Page<Blog> findByAuthorUserId(String authorId, Pageable pageable);
    
    @Query("SELECT b FROM Blog b WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    Page<Blog> findByTitleContainingOrContentContaining(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT b FROM Blog b ORDER BY b.createdAt DESC")
    Page<Blog> findRecentBlogs(Pageable pageable);
}

