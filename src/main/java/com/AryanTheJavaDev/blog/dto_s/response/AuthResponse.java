package com.AryanTheJavaDev.blog.dto_s.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse
{
private String token;
private long expiresIn;
}
