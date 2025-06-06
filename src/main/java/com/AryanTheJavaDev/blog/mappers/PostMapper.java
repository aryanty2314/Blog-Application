package com.AryanTheJavaDev.blog.mappers;


import com.AryanTheJavaDev.blog.dto_s.*;
import com.AryanTheJavaDev.blog.dto_s.requests.CreatePostRequest;
import com.AryanTheJavaDev.blog.dto_s.requests.UpdatePostRequest;
import com.AryanTheJavaDev.blog.entities.Post;
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


    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto updatePostRequestDto);

}
