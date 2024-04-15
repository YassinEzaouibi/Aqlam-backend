package aqlaam.version2.jwt.util;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    public static final int JWT_ACCESS_TOKEN_EXPIRE = 10 * 60 * 1000;

    public static final int JWT_REFRESH_TOKEN_EXPIRE = 120 * 60 * 1000;


    public static final String ISSUER = "AqlamApplication";

    public static final String SECRET = "Secret";


    public static final String BEARER_PREFIX = "Bearer ";


    public static final String AUTH_HEADER = "Authorization";

}
