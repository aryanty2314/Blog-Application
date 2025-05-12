package com.AryanTheJavaDev.blog.dto_s;

import com.AryanTheJavaDev.blog.entities.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto
{

private UUID id;
private String title;
private String content;
private AuthorDto author;
private Integer readingTime;
private CategoryDTO category;
private Set<TagDto> tags;
private LocalDateTime createdAt;
private LocalDateTime updatedAt;
private PostStatus postStatus;

}
