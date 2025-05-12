package com.AryanTheJavaDev.blog.mappers;


import com.AryanTheJavaDev.blog.entities.Post;
import com.AryanTheJavaDev.blog.dto_s.CreatePostRequest;
import com.AryanTheJavaDev.blog.dto_s.CreatePostRequestDto;
import com.AryanTheJavaDev.blog.dto_s.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper
{

    @Mapping(target = "author",source = "author")
    @Mapping(target = "category",source = "category")
    @Mapping(target = "tags",source = "tags")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto createPostRequestDto);

}
