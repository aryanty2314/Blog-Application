package com.AryanTheJavaDev.blog.controller;

import com.AryanTheJavaDev.blog.dto_s.*;
import com.AryanTheJavaDev.blog.dto_s.requests.CreatePostRequest;
import com.AryanTheJavaDev.blog.dto_s.requests.UpdatePostRequest;
import com.AryanTheJavaDev.blog.entities.Post;
import com.AryanTheJavaDev.blog.entities.User;
import com.AryanTheJavaDev.blog.mappers.PostMapper;
import com.AryanTheJavaDev.blog.service.PostService;
import com.AryanTheJavaDev.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(required = false) UUID categoryId,
                                                     @RequestParam(required = false) UUID tagId) {
        List<Post> posts = postService.getPosts(categoryId, tagId);
        List<PostDto> PostDtos = posts.stream().map(postMapper::toDto).toList();

        return ResponseEntity.ok(PostDtos);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id)
    {
        Post post = postService.getPost(id);
       PostDto postDto = postMapper.toDto(post);
       return ResponseEntity.ok(postDto);
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getAllDrafts(@RequestAttribute UUID userId)
    {
        User loggedInUser = userService.getUserById(userId);
        List<Post>  draftPosts = postService.getDraftPosts(loggedInUser);
        List<PostDto> PostDtos = draftPosts.stream().map(postMapper::toDto).toList();
          return ResponseEntity.ok(PostDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
     @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
    @RequestAttribute UUID userId
    )
    {
        User loggedInUser = userService.getUserById(userId);
        CreatePostRequest createPostRequest =   postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(loggedInUser, createPostRequest);
        PostDto createPostDto = postMapper.toDto(createdPost);

        return new ResponseEntity<>(createPostDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
           @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto
            )
    {
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatedPost = postService.updatePost(id,updatePostRequest);
        PostDto updatedPostDto = postMapper.toDto(updatedPost);
        return new ResponseEntity<>(updatedPostDto,HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id)
    {
         postService.deletePost(id);
         return ResponseEntity.noContent().build();
    }

}
