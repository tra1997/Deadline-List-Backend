package com.example.todo_management.security;

import com.example.todo_management.entity.User;
import com.example.todo_management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email: " + usernameOrEmail));

        // Map user roles to GrantedAuthority objects
       // GrantedAuthority  đại diện cho những quyền mà người dùng được cấp
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        // Return a UserDetails object with username, password, and authorities
        return new org.springframework.security.core.userdetails.User(  //là một lớp trong Spring Security đại diện cho người dùng trong hệ thống bảo mật
                //Trả về một đối tượng UserDetails
                usernameOrEmail,
                user.getPassword(),
                authorities
        );
    }


}