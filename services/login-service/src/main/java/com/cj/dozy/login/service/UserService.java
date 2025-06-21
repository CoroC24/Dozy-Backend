package com.cj.dozy.login.service;

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

    /**
     * Registra un nuevo usuario.
     *
     * @return el usuario guardado (sin contraseña en texto plano)
     * @throws IllegalArgumentException si el usuario ya existe
     */
    public User registerUser(User user) {
        user = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role("USER")
                .build();
        return userRepository.save(user);
    }

    /**
     * Busca un usuario por su username.
     *
     * @param username nombre de usuario
     * @return Optional con el usuario
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
