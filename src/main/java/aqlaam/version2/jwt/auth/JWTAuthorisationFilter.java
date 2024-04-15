package aqlaam.version2.jwt.auth;

import aqlaam.version2.jwt.helper.JWTHelper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static aqlaam.version2.jwt.util.JwtTokenUtil.AUTH_HEADER;
import static aqlaam.version2.jwt.util.JwtTokenUtil.SECRET;

@AllArgsConstructor
public class JWTAuthorisationFilter extends OncePerRequestFilter {

    private JWTHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtHelper.extractTokenFromHeaderIfExists(request.getHeader(AUTH_HEADER));
        if (accessToken != null) {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);
            String email = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
