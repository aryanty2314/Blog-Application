package com.AryanTheJavaDev.blog.dto_s;


import com.AryanTheJavaDev.blog.Entities.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreatePostRequestDto
{
    @NotBlank(message = "Title is required ")
    @Size(min = 3 ,max = 200,message = "message must be in between {min} and {max} characters !!")
    private String title;

    @NotBlank(message = "Content is required ")
    @Size(min = 20 ,max = 50000,message = "Content must be in between {min} and {max} characters !!")
    private String content;

    @NotNull(message = " Category ID is required ")
    private UUID categoryId;

    @Builder.Default
    @Size(max = 10,message = "maximum {max} tags allowed")
    private Set<UUID> tagsId = new HashSet<>();

    @NotNull(message = "status is required")
    private PostStatus status;


}
