package com.example.task_management.config;

import com.example.task_management.security.JwtAuthenticaitonFilter;
import com.example.task_management.security.JwtAuthenticationEntryPoint;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
@AllArgsConstructor
public class SpringSecurityConfig {


    private JwtAuthenticaitonFilter authenticaitonFilter;
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeHttpRequests((authorize) -> {
            authorize.requestMatchers("/api/auth/**").permitAll();
            authorize.requestMatchers("/api/user/**").permitAll();

            //   authorize.requestMatchers("/api/auth/login").permitAll();
            authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll(); //thuong hay bi chan boi Spring security ==> nên thuong phai co dong nay
            authorize.anyRequest().authenticated();  //Tất cả các yêu cầu gửi đến ứng dụng (dù là URL nào) đều cần được xác thực.
        }).httpBasic(Customizer.withDefaults());    //HTTP Basic Authentication. Đây là cách xác thực đơn giản, trong đó tên đăng nhập và mật khẩu được gửi kèm theo trong phần header của mỗi yêu cầu HTTP.
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint));
        http.addFilterBefore(authenticaitonFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();  // tạo đối tượng SecurityFilterChain để thực thi cấu hình http ở trên
    }


}
