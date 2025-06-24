package com.cj.dozy.login.service;

import com.cj.dozy.login.dto.LoginRequest;
import com.cj.dozy.login.dto.RegisterRequest;
import com.cj.dozy.login.dto.UserResponse;
import com.cj.dozy.login.model.User;
import com.cj.dozy.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()) ||
                userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email or Username already registered");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
    }

    public UserResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository
                .findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail());

        if (userOpt.isEmpty()){
            throw new IllegalArgumentException("User not found");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect Password");
        }

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
