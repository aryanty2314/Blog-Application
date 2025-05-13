package com.AryanTheJavaDev.blog.service.serviceImpl;

import com.AryanTheJavaDev.blog.dto_s.requests.UpdatePostRequest;
import com.AryanTheJavaDev.blog.entities.*;
import com.AryanTheJavaDev.blog.repository.PostRepository;
import com.AryanTheJavaDev.blog.service.CategoryService;
import com.AryanTheJavaDev.blog.service.PostService;
import com.AryanTheJavaDev.blog.service.TagService;
import com.AryanTheJavaDev.blog.dto_s.requests.CreatePostRequest;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor


public class PostServiceImpl implements PostService
{
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;


    private static final int WORDS_PER_MINUTE = 200;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPosts(UUID CategoryId, UUID TagId) {
        if (CategoryId != null && TagId != null)
        {
             Category category = categoryService.getCategory(CategoryId);
             Tag tag = tagService.getTagById(TagId);
              return postRepository.findAllByStatusAndCategoryAndTagsContaining
                     (
                             PostStatus.PUBLISHED,
                             category,
                             tag
                     );
        }
        if (CategoryId != null)
        {
            Category category = categoryService.getCategory(CategoryId);
            return postRepository.findAllByStatusAndCategory(PostStatus.PUBLISHED, category);
        }
        if (TagId!= null)
        {
            Tag tag = tagService.getTagById(TagId);
            return postRepository.findAllByStatusAndTagsContaining(PostStatus.PUBLISHED, tag);
        }
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftPosts(User user)
    {
     return  postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
    }

    @Transactional
    @Override
    public Post createPost(User user, CreatePostRequest createPostRequest1)
    {
        Post post = new Post();
        post.setTitle(createPostRequest1.getTitle());
        post.setContent(createPostRequest1.getContent());
        post.setAuthor(user);
        post.setStatus(createPostRequest1.getStatus());
        post.setReadingTime(calculateReadingTime(createPostRequest1.getContent()));
        Category category = categoryService.getCategory(createPostRequest1.getCategoryId());
        post.setCategory(category);

         Set<UUID> TagIds  = createPostRequest1.getTagsId();
         List<Tag> tags = tagService.getTagByIds(TagIds);
         createPostRequest1.setTagsId(new HashSet<>(TagIds));

        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post existingPost =  postRepository.findById(id)
                .orElseThrow(()-> new EntityExistsException("Post does not Exist" +id));

        existingPost.setTitle(updatePostRequest.getTitle());

        String content = updatePostRequest.getContent();
        existingPost.setContent(content);

        existingPost.setStatus(updatePostRequest.getStatus());

        existingPost.setReadingTime(calculateReadingTime(content));

        UUID categoryId = updatePostRequest.getCategoryId();
        if (!existingPost.getCategory().getId().equals(categoryId)) {
            Category category = categoryService.getCategory(categoryId);
            existingPost.setCategory(category);
        }
        else
        {
            existingPost.setCategory(existingPost.getCategory());
        }

        Set<UUID> existingTagIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagIds();

        if (!existingTagIds.equals(updatePostRequestTagIds)) {
            List<Tag> tags = tagService.getTagByIds(updatePostRequestTagIds);
            existingPost.setTags(new HashSet<>(tags));
        }
        else
        {
            existingPost.setTags(existingPost.getTags());
        }
        return postRepository.save(existingPost);
        }

    @Override
    public Post getPost(UUID id) {

        return postRepository.findById(id)
                .orElseThrow(()-> new EntityExistsException("Post does not Exist" +id));

    }

    @Override
    public void deletePost(UUID id)
    {
        Post post = getPost(id);
        postRepository.delete(post);
    }

    private Integer calculateReadingTime(String Content)
    {
        if (Content == null || Content.isEmpty())
        {
            return 0;
        }
        int wordCount = Content.trim().split("\\s+").length;
        return   (int) Math.ceil((double) wordCount/WORDS_PER_MINUTE);
    }
}
