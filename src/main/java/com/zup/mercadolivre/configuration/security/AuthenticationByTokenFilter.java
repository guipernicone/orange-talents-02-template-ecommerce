package com.zup.mercadolivre.configuration.security;

import com.zup.mercadolivre.configuration.security.service.TokenService;
import com.zup.mercadolivre.entity.user.User;
import com.zup.mercadolivre.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {
    private TokenService tokenService;

    private UserRepository userRepository;

    public AuthenticationByTokenFilter(TokenService tokenService, UserRepository userRepository) {
        super();
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = this.getToken(request);
        boolean validToken = tokenService.isTokenValid(token);

        if (validToken) {
            this.authenticateUser(token);
        }

        filterChain.doFilter(request, response);

    }

    /**
     * Authenticate a user by the given token
     *
     * @param token
     */
    private void authenticateUser(String token) {
        String userId = tokenService.getUserId(token);
        Optional<User> optionalUser = userRepository.findById(Long.parseLong(userId));

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        // Return only the token without the header type Bearer
        return token.substring(7, token.length());
    }
}
