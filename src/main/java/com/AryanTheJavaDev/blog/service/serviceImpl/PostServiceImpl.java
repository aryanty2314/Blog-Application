package com.AryanTheJavaDev.blog.service.serviceImpl;

import com.AryanTheJavaDev.blog.entities.*;
import com.AryanTheJavaDev.blog.repository.PostRepository;
import com.AryanTheJavaDev.blog.service.CategoryService;
import com.AryanTheJavaDev.blog.service.PostService;
import com.AryanTheJavaDev.blog.service.TagService;
import com.AryanTheJavaDev.blog.dto_s.CreatePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
