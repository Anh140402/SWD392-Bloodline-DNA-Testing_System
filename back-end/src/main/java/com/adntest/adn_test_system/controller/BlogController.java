package com.adntest.adn_test_system.controller;

import com.adntest.adn_test_system.dto.request.BlogRequest;
import com.adntest.adn_test_system.dto.response.BlogResponse;
import com.adntest.adn_test_system.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
@Tag(name = "Blog", description = "Blog management APIs")
public class BlogController {
    private final BlogService blogService;

    @GetMapping
    @Operation(summary = "Get all blogs")
    public ResponseEntity<List<BlogResponse>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get blog by ID")
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable String id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @PostMapping
    @Operation(summary = "Create new blog (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<BlogResponse> createBlog(@Valid @RequestBody BlogRequest request, @RequestParam(required = false) String userId) {
        return ResponseEntity.ok(blogService.createBlog(request, userId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update blog (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<BlogResponse> updateBlog(@PathVariable String id, @Valid @RequestBody BlogRequest request) {
        return ResponseEntity.ok(blogService.updateBlog(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete blog (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> deleteBlog(@PathVariable String id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/author/{authorId}")
    @Operation(summary = "Get blogs by author")
    public ResponseEntity<List<BlogResponse>> getBlogsByAuthor(@PathVariable String authorId) {
        return ResponseEntity.ok(blogService.getBlogsByAuthor(authorId));
    }

    @GetMapping("/my-blogs")
    @Operation(summary = "Get current user's blogs (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<List<BlogResponse>> getMyBlogs(@RequestParam String userId) {
        return ResponseEntity.ok(blogService.getBlogsByAuthor(userId));
    }

    
}

