package com.AryanTheJavaDev.blog.mappers;
import com.AryanTheJavaDev.blog.dto_s.CategoryDTO;
import com.AryanTheJavaDev.blog.dto_s.CreateCategoryRequest;
import com.AryanTheJavaDev.blog.entities.Category;
import com.AryanTheJavaDev.blog.entities.Post;
import com.AryanTheJavaDev.blog.entities.PostStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel= "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper
{
    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDTO todto(Category category);

    Category toEntity(CreateCategoryRequest createCategoryRequest);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts)
    {
        if (posts == null)
        {
            return 0;
        }
        return posts.stream()
                .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                .count();
    }
}
