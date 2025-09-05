package lab.springboot.springbboothttps.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";
    private static final Logger log = LoggerFactory.getLogger(JwtTokenVerifierFilter.class);
    private static final List<String> PUBLIC_PATHS = List.of("/auth/signup", "/signup");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {

        String path = request.getRequestURI();
        String header = request.getHeader(HEADER_STRING);

        try {
            log.info("[ Filter: started ] - Incoming request: method={}, path={}", request.getMethod(), path);

            /** whitelisted path */
            if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
                filterChain.doFilter(request, response);
                return;
            }

            /** token check  */
            if (header == null || !header.startsWith(TOKEN_PREFIX)) {
                log.warn("Unauthorized request: missing or invalid Authorization header [{} {}]",
                        request.getMethod(), path);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
                return;
            }

            /** validate JWT */
            String token = header.replace("Bearer ", "");
            String username = JWTUtils.getAuthentication(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.warn("Unauthorized request {} : {} - reason: {}", request.getMethod(), path, e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT");
        } finally {
            log.info("[ Filter: completed ] - Incoming request: method={}, path={}", request.getMethod(), path);
        }
    }
}
