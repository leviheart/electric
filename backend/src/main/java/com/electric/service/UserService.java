package com.electric.service;

import com.electric.dto.UserInfo;
import com.electric.model.Role;
import com.electric.model.User;
import com.electric.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserInfo> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserInfo)
                .collect(Collectors.toList());
    }

    public UserInfo getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        return toUserInfo(user);
    }

    @Transactional
    public UserInfo updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        if (request.username != null && !request.username.isEmpty()) {
            if (!request.username.equals(user.getUsername()) && userRepository.existsByUsername(request.username)) {
                throw new RuntimeException("用户名已存在");
            }
            user.setUsername(request.username);
        }
        
        return toUserInfo(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("用户不存在");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public void toggleEnable(Long id, boolean enabled) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    private UserInfo toUserInfo(User user) {
        String role = user.getRoles().stream()
                .findFirst()
                .map(Role::getName)
                .orElse("USER");
        
        return new UserInfo(
                user.getId(),
                user.getUsername(),
                role,
                user.getEnabled() != null ? user.getEnabled() : true
        );
    }

    public static class UpdateUserRequest {
        public String username;
        public String role;
    }
}
