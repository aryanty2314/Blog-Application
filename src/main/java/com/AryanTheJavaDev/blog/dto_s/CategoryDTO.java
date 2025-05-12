package com.AryanTheJavaDev.blog.dto_s;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CategoryDTO
{
private UUID id;
private String name;
private long postCount;
}
