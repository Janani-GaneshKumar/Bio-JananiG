package com.janani.contentrecommendation.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/*
The main Work of this JwtAuthenticationFilter is when a user sends a request (JWT Token), It checks if the
 token is valid, If it is valid it allows the user inside or it  shows unauthorized*/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    //This is the Main method of the Filter
    //HttpServletRequest request - Incoming request
    //HttpServletResponse - Outgoing response
    //FilterChain filterChain - Lets you pass request to next filter /controller
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");//token bearer
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(token)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, jwtUtil.getAuthorities(token));
                /*
                  Creates a Spring Security authentication object.
                  Parameters:
                  username → the user’s identity (from token).
                  null → no password here, because we’re authenticating via token.
                  jwtUtil.getAuthorities(token) → the user’s role(s), e.g. ROLE_USER or ROLE_ADMIN.
                 */
                        //Attach request-specific info to the authentication object.
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        //stores the authentication object
                        SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
