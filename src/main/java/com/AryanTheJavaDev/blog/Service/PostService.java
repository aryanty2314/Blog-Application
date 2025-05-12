package com.AryanTheJavaDev.blog.Service;

import com.AryanTheJavaDev.blog.Entities.Post;
import com.AryanTheJavaDev.blog.Entities.User;
import com.AryanTheJavaDev.blog.dto_s.CreatePostRequest;


import java.util.List;
import java.util.UUID;

public interface PostService
{
List<Post> getPosts(UUID CategoryId, UUID TagId);

List<Post> getDraftPosts(User user);

Post createPost(User user, CreatePostRequest createPostRequest);


}
