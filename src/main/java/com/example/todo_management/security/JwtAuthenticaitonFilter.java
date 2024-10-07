package com.example.todo_management.security;

import com.example.todo_management.entity.User;
import jakarta.persistence.Column;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticaitonFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticaitonFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }


    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader( "Authorization");

        if(StringUtils.hasText(bearerToken)  && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }

        return null;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Get JWT token from HTTP request
        String token = getTokenFromRequest(request);

        // Validate Token
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            // get username from token
            String username = jwtTokenProvider.getUsername(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);  // su dung CustomUserDetailsService cung dc

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,  // la phan password nhung vi da dang nhap thanh cong roi nen ko can dien cung duoc
                    userDetails.getAuthorities()    //để cho phép ứng dụng kiểm tra quyền truy cập của người dùng, duy trì trạng thái xác thực và cung cấp thông tin quyền cho các phần khác của ứng dụng
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));   // Bổ sung thông tin ngữ cảnh: web./ Tăng cường khả năng kiểm tra và phân tích:

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);  //bảo rằng sau khi bộ lọc hiện tại đã thực hiện các tác vụ của mình, yêu cầu sẽ được chuyển tiếp đến bộ lọc tiếp theo, cho phép ứng dụng thực hiện đầy đủ các kiểm tra bảo mật và xử lý yêu cầu theo cách mong muốn.
    }


}
