package com.rti.puppygram.security;

import com.rti.puppygram.model.User;
import com.rti.puppygram.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class CustomBasicAuthenticatorFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BASIC = "Basic";

    private final UserRepository userRepository;

    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(isBasicAuthentication(request)){
            String[] credentials = decodeBase64(request.getHeader(AUTHORIZATION));
            String username = credentials[0];
            String password = credentials[1];

            User user = userRepository.findByUsernameFetchRoles(username).orElse(null);

            if(user == null){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("User not found");
                return;
            }

            boolean passwordMatches = checkPassword(password, user.getPassword());

            if(!passwordMatches){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid password");
                return;
            }

            setAuthentication(user);

            filterChain.doFilter(request, response);

        } else{
            filterChain.doFilter(request, response);
            return;
        }
    }

    private void setAuthentication(User user) {
        Authentication authentication = createAuthenticationToken(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication createAuthenticationToken(User user) {
        UserPrincipal principal = UserPrincipal.create(user);
        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }

    private boolean checkPassword(String userPassword, String loginPassword) {
        return encoder().matches(userPassword, loginPassword);
    }

    private String[] decodeBase64(String base64Credentials) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials.substring(6));
        String decodedString = new String(decodedBytes);
        return decodedString.split(":", 2);
    }

    private boolean isBasicAuthentication(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        return header != null && header.startsWith(BASIC);

    }
}
