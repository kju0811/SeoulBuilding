package Project.SeoulBuilding.backend.jwt.jwtclass;

import Project.SeoulBuilding.backend.User.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtGenerator jwtGenerator;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromrequest(request);
        if (token != null) {
            boolean isAccesstoken = token.startsWith("access");
            if (jwtGenerator.validateToken(token, isAccesstoken)) {
                UsernamePasswordAuthenticationToken authentication = getAuthentication(token,isAccesstoken);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }
    }

    private String getTokenFromrequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token,boolean isAccessToken) {
        String email = jwtGenerator.getUsernameFromToken(token,isAccessToken);
        UserDetails userPrincipal = customUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }
}
