package aqlaam.version2.jwt.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static aqlaam.version2.jwt.util.JwtTokenUtil.*;

@Component
public class JWTHelper {

    Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public String generateAccessToken(String email, List<String> roles) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_EXPIRE))
                .withIssuer(ISSUER)
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    public String generateRefreshToken(String email) {
        return JWT
                .create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_EXPIRE))
                .withIssuer(ISSUER)
                .sign(algorithm);
    }

    public String extractTokenFromHeaderIfExists(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    public Map<String, String> getTokensMap(String jwtAccessToken, String jwtRefreshToken) {
        Map<String, String> idTokens = new HashMap<>();
        idTokens.put("accessToken", jwtAccessToken);
        idTokens.put("refreshToken", jwtRefreshToken);

        return idTokens;
    }

}
