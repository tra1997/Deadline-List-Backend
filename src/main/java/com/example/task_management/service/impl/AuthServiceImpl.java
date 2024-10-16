package com.example.task_management.service.impl;

import com.example.task_management.dto.JwtAuthResponse;
import com.example.task_management.dto.LoginDto;
import com.example.task_management.dto.RegisterDto;
import com.example.task_management.entity.Role;
import com.example.task_management.entity.User;
import com.example.task_management.exception.TaskAPIException;
import com.example.task_management.repository.RoleRepository;
import com.example.task_management.repository.UserRepository;
import com.example.task_management.security.JwtTokenProvider;
import com.example.task_management.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        if (registerDto.getPassword() == null || registerDto.getPassword().isEmpty()) {
            throw new TaskAPIException(HttpStatus.BAD_REQUEST, "Password cannot be empty");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");

        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return "User Registered Successfully!";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {

        // Authentication User
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );
        // Set Authentication into SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());
        String role = null;
        if (userOptional.isPresent()) {
            User loggedInUser = userOptional.get();
            Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();

            if (optionalRole.isPresent()) {
                Role userRole = optionalRole.get();
                role = userRole.getName();

            }
        }

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);

        return jwtAuthResponse;
    }
}
