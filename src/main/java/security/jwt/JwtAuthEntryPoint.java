package security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class implements the AuthenticationEntryPoint interface, which is used in Spring Security to handle authentication exceptions
 * when a client tries to access a protected resource without proper authentication.
 * The purpose of this class is to handle unauthorized requests.
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    /**
     * Sends an HTTP error response with the status code 401 Unauthorized and a message indicating that the client is not authorized to access the requested resource.
     * @param httpServletRequest an object representing the HTTP request that caused the authentication exception
     * @param httpServletResponse an object representing the HTTP response that will be sent back to the client
     * @param e the AuthenticationException that was thrown
     * @throws IOException  if an input or output exception occurs
     * @throws ServletException if there is an error in the servlet container or if the request cannot be handled for some reason
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        log.error("Unauthorized error: {}", e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
