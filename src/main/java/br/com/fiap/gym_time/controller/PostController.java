package br.com.fiap.gym_time.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.gym_time.models.Post;
import br.com.fiap.gym_time.models.PostFilter;
import br.com.fiap.gym_time.repository.PostRepository;
import br.com.fiap.gym_time.specification.PostSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {

    private Logger log = LoggerFactory.getLogger(getClass());
    // List of all posts
    @Autowired
    private PostRepository repository;

    // Get all posts
    @GetMapping()
    @Cacheable("posts")
    public Page<Post> getPosts(  PostFilter filter,
    @PageableDefault(size = 10, sort = "date", direction = Direction.DESC) Pageable pageable) {
        var specification = PostSpecification.withFilters(filter);
        return repository.findAll(specification, pageable);
    }
    
    // Create a new post
    @PostMapping()
    @CacheEvict(value = "posts", allEntries = true)
    @Operation(summary = "Create a new post", description = "Create a new post", responses = {
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "400"),
    })
    public ResponseEntity<Post> createPost(@RequestBody @Valid Post post) {
        log.info("Posting...");
        repository.save(post);
        return ResponseEntity.status(201).body(post);
    }
    
    // Get details of a post
    @GetMapping("/{id}")
    public Post getPostByID(@PathVariable Long id) {
        log.info("Searching post " + id);
        return getPost(id);
    }

    // Delete a post
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        log.info("Deleting post " + id);
        repository.delete(getPost(id));
    }    

    // Update a post
    @PutMapping("/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody @Valid Post post) {
        log.info("Updating post " + id);
        repository.delete(getPost(id));
        post.setId(id);
        repository.save(post);
    }

    private Post getPost(Long id) {
        return repository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
            );

    }
}
