package com.example.springmovieapi.security.jwt;

import com.example.springmovieapi.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A filter that intercepts incoming requests and processes the JWT token from the Authorization header.
 * If the token is valid, it creates an authentication token for the user and sets it in the SecurityContextHolder,
 * which means that the user is now authenticated and authorized to access any protected resources in the application.
 */
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

    /**
     * Constant string used to identify bearer tokens in the Authorization header
     */
    public static final String BEARER = "Bearer";

    /**
     * An instance of the JwtTokenUtil class used to validate and extract information from JWT tokens
     */
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * An instance of the UserDetailsService class used to load user details from the database
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtTokenUtil.validateJwtToken(jwt)) {

                // extracts username
                String username = jwtTokenUtil.getUserNameFromJwtToken(jwt);

                // loads user details from DB by username
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // creates an authentication token that contains user's details, their authorities and a null password
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null,userDetails.getAuthorities());

                // sets the details of the authentication token to be the details of the current request
                // WebAuthenticationDetailsSource extracts the relevant details (such as the user's IP) from the request.
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // sets the authentication token to be the current authentication in the security context
                // this means that the user is now considered to be authenticated
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {0}", e);
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER))
            return headerAuth.substring(BEARER.length());

        return null;
    }
}
