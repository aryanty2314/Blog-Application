package com.AryanTheJavaDev.blog.Controller;

import com.AryanTheJavaDev.blog.Entities.Post;
import com.AryanTheJavaDev.blog.Entities.User;
import com.AryanTheJavaDev.blog.Mappers.PostMapper;
import com.AryanTheJavaDev.blog.Service.PostService;
import com.AryanTheJavaDev.blog.Service.UserService;
import com.AryanTheJavaDev.blog.dto_s.CreatePostRequest;
import com.AryanTheJavaDev.blog.dto_s.CreatePostRequestDto;
import com.AryanTheJavaDev.blog.dto_s.PostDto;
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

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getAllDrafts(@RequestAttribute UUID id)
    {
        User loggedInUser = userService.getUserById(id);
        List<Post>  draftPosts = postService.getDraftPosts(loggedInUser);
        List<PostDto> PostDtos = draftPosts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(PostDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
    @RequestBody CreatePostRequestDto createPostRequestDto,
    @RequestAttribute UUID Userid
    )
    {
        User loggedInUser = userService.getUserById(Userid);
        CreatePostRequest createPostRequest =   postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(loggedInUser, createPostRequest);
        PostDto createPostDto = postMapper.toDto(createdPost);

        return new ResponseEntity<>(createPostDto, HttpStatus.CREATED);
    }

}
