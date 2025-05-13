package com.AryanTheJavaDev.blog.dto_s.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorResponse
{
private String message;
private int status;
private List<FieldError> details;


@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public static class FieldError
{
    private String field;
    private String message;
}
}
