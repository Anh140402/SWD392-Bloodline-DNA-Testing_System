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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
@Tag(name = "Blog", description = "Blog management APIs")
public class BlogController {
    private final BlogService blogService;

    @GetMapping
    @Operation(summary = "Get all blogs")
    public ResponseEntity<Page<BlogResponse>> getAllBlogs(Pageable pageable) {
        return ResponseEntity.ok(blogService.getAllBlogs(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get blog by ID")
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable String id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @PostMapping
   // @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Create new blog (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<BlogResponse> createBlog(@Valid @RequestBody BlogRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(blogService.createBlog(request, userDetails));
    }

    @PutMapping("/{id}")
   // @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Update blog (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<BlogResponse> updateBlog(@PathVariable String id, @Valid @RequestBody BlogRequest request) {
        return ResponseEntity.ok(blogService.updateBlog(id, request));
    }

    @DeleteMapping("/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete blog (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void> deleteBlog(@PathVariable String id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/author/{authorId}")
    @Operation(summary = "Get blogs by author")
    public ResponseEntity<Page<BlogResponse>> getBlogsByAuthor(@PathVariable String authorId, Pageable pageable) {
        return ResponseEntity.ok(blogService.getBlogsByAuthor(authorId, pageable));
    }

    @GetMapping("/my-blogs")
  //  @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @Operation(summary = "Get current user's blogs (Admin/Staff only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Page<BlogResponse>> getMyBlogs(@AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
        return ResponseEntity.ok(blogService.getBlogsByAuthor(userDetails.getUsername(), pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Search blogs by title or content")
    public ResponseEntity<Page<BlogResponse>> searchBlogs(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(blogService.searchBlogs(keyword, pageable));
    }

    @GetMapping("/recent")
    @Operation(summary = "Get recent blogs")
    public ResponseEntity<Page<BlogResponse>> getRecentBlogs(Pageable pageable) {
        return ResponseEntity.ok(blogService.getRecentBlogs(pageable));
    }
}

