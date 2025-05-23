package com.AryanTheJavaDev.blog.service;

import com.AryanTheJavaDev.blog.dto_s.requests.UpdatePostRequest;
import com.AryanTheJavaDev.blog.entities.Post;
import com.AryanTheJavaDev.blog.entities.User;
import com.AryanTheJavaDev.blog.dto_s.requests.CreatePostRequest;


import java.util.List;
import java.util.UUID;

public interface PostService
{
List<Post> getPosts(UUID CategoryId, UUID TagId);

List<Post> getDraftPosts(User user);

Post createPost(User user, CreatePostRequest createPostRequest);

Post updatePost(UUID id, UpdatePostRequest updatePostRequest);

Post getPost(UUID id);

void deletePost(UUID id);
}
