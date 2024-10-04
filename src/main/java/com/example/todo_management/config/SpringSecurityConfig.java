package com.example.todo_management.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
@AllArgsConstructor
public class SpringSecurityConfig {


//    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
public  static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeHttpRequests((authorize) -> {
            authorize.requestMatchers("/api/auth/**").permitAll();
           authorize.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll();
            authorize.anyRequest().authenticated();  //Tất cả các yêu cầu gửi đến ứng dụng (dù là URL nào) đều cần được xác thực.
        }).httpBasic(Customizer.withDefaults());    //HTTP Basic Authentication. Đây là cách xác thực đơn giản, trong đó tên đăng nhập và mật khẩu được gửi kèm theo trong phần header của mỗi yêu cầu HTTP.

        return http.build();  // tạo đối tượng SecurityFilterChain để thực thi cấu hình http ở trên
    }




//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails tea = User.builder()
//                .username("tea")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(tea, admin);
//
//    }

}
