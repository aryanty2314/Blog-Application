package com.AryanTheJavaDev.blog.dto_s;


import com.AryanTheJavaDev.blog.entities.PostStatus;
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

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class UpdatePostRequestDto
{
@NotBlank()
@Size(min = 5, max = 255)
private String title;

@NotNull
private UUID id;

@NotBlank
@Size(min = 10, max = 50000)
private String content;

@NotNull
private UUID categoryId;

@NotNull
@Builder.Default
private Set<UUID> tagIds = new HashSet<>();

@NotNull
private PostStatus status;
}
