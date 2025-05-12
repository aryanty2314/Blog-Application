package com.AryanTheJavaDev.blog.service.serviceImpl;

import com.AryanTheJavaDev.blog.entities.User;
import com.AryanTheJavaDev.blog.repository.UserRepository;
import com.AryanTheJavaDev.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }
}
