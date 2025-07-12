package com.adntest.adn_test_system.service;

import com.adntest.adn_test_system.dto.request.BlogRequest;
import com.adntest.adn_test_system.dto.response.BlogResponse;
import com.adntest.adn_test_system.entity.Account;
import com.adntest.adn_test_system.entity.Blog;
import com.adntest.adn_test_system.exception.AuthException;
import com.adntest.adn_test_system.repository.AccountRepository;
import com.adntest.adn_test_system.repository.BlogRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogService {
    private final BlogRepository blogRepository;
    private final AccountRepository accountRepository;

    public Page<BlogResponse> getAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public BlogResponse getBlogById(String id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new AuthException("Blog not found"));
        return mapToResponse(blog);
    }

    public BlogResponse createBlog(BlogRequest request) {
        String userId = request.getAuthorId();
        Account author = accountRepository.findById(userId)
                .orElseThrow(() -> new AuthException("Author not found"));

        Blog newBlog = Blog.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(author)
                .createdAt(LocalDateTime.now())
                .build();

        Blog savedBlog = blogRepository.save(newBlog);
        return mapToResponse(savedBlog);
    }

    public BlogResponse updateBlog(String id, BlogRequest request) {
        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new AuthException("Blog not found"));

        existingBlog.setTitle(request.getTitle());
        existingBlog.setContent(request.getContent());

        Blog updatedBlog = blogRepository.save(existingBlog);
        return mapToResponse(updatedBlog);
    }

    public void deleteBlog(String id) {
        if (!blogRepository.existsById(id)) {
            throw new AuthException("Blog not found");
        }
        blogRepository.deleteById(id);
    }

    public Page<BlogResponse> getBlogsByAuthor(String authorId, Pageable pageable) {
        return blogRepository.findByAuthorUserId(authorId, pageable)
                .map(this::mapToResponse);
    }

    public Page<BlogResponse> getMyBlogs(Pageable pageable) {
        String userId = getCurrentUserId();
        return blogRepository.findByAuthorUserId(userId, pageable)
                .map(this::mapToResponse);
    }

    public Page<BlogResponse> getRecentBlogs(Pageable pageable) {
        return blogRepository.findRecentBlogs(pageable)
                .map(this::mapToResponse);
    }

    public Page<BlogResponse> searchBlogs(String keyword, Pageable pageable) {
        return blogRepository.findByTitleContainingOrContentContaining(keyword, pageable)
                .map(this::mapToResponse);
    }

    public boolean canUserModifyBlog(String blogId, String userId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new AuthException("Blog not found"));
        return blog.getAuthor().getUserId().equals(userId);
    }

    private String getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        throw new AuthException("User not authenticated");
    }

    private BlogResponse mapToResponse(Blog blog) {
        return BlogResponse.builder()
                .blogId(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .authorId(blog.getAuthor() != null ? blog.getAuthor().getUserId() : null)
                .authorName(blog.getAuthor() != null ? blog.getAuthor().getUsername() : null)
                .createdAt(blog.getCreatedAt())
                .build();
    }
}