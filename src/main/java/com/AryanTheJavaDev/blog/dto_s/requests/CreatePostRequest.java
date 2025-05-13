package com.AryanTheJavaDev.blog.dto_s.requests;


import com.AryanTheJavaDev.blog.entities.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreatePostRequest
{

    private String title;

    private String content;

    private UUID categoryId;

    @Builder.Default
    private Set<UUID> tagsId = new HashSet<>();

    private PostStatus status;

}
