package com.AryanTheJavaDev.blog.Service;

import com.AryanTheJavaDev.blog.Entities.User;

import java.util.UUID;

public interface UserService
{
User getUserById(UUID id);
}
