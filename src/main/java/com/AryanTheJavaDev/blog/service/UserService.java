package com.AryanTheJavaDev.blog.service;

import com.AryanTheJavaDev.blog.entities.User;

import java.util.UUID;

public interface UserService
{
User getUserById(UUID id);
}
