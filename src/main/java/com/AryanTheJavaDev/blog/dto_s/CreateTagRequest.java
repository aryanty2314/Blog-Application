package com.AryanTheJavaDev.blog.dto_s;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CreateTagRequest
{

    @NotBlank(message = "At least there must be a Tag")
    @Size(max = 10,message = "Maximum (max) Tags Allowed")
    private Set<
            @Size(min = 2, max = 30, message = "Tag name must be in between (min) and (max) Characters ")
            @Pattern(regexp = "^[\\w\\s-]+$", message = "Tag name can only contain letters, number, spaces and hyphens")
                    String> names;

}
