package com.AryanTheJavaDev.blog.Service.ServiceImpl;

import com.AryanTheJavaDev.blog.Entities.User;
import com.AryanTheJavaDev.blog.Repository.UserRepository;
import com.AryanTheJavaDev.blog.Service.UserService;
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
