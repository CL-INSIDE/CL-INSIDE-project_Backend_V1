package com.example.sns.global.security.jwt;

import com.example.sns.global.exception.ExpiredAccessTokenException;
import com.example.sns.global.exception.ExpiredRefreshTokenException;
import com.example.sns.global.exception.IncorrectTokenException;
import com.example.sns.global.exception.InvalidTokenException;
import com.example.sns.global.security.auth.CustomUserDetailService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private String SECRET_KEY = "U05TcHJvamVjdA==";

    private String HEADER = "Authorization";

    private String PREFIX = "Bearer";

    private long ACCESS_TOKEN_VALID_TIME = 5 * 60 * 1000L;

    private long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 100L;

    private final CustomUserDetailService customUserDetailService;

    protected String setSecretKey(){
        return Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String createJwtAccessToken(String name){
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setSubject(name)
                .claim("type", "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String createJwtRefreshToken(String username){
        return Jwts.builder()
                .setHeaderParam("typ", "jwt")
                .setSubject(username)
                .claim("type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    //study
    public String resolveToken(HttpServletRequest request){
        String token = request.getHeader(HEADER);

        if(StringUtils.hasText(token) && token.startsWith(PREFIX)){
            return token.substring(7);
        }
        return null;
    }

    public Claims getUsername(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            throw IncorrectTokenException.EXCEPTION;
        }catch (ExpiredJwtException e) {
            throw ExpiredAccessTokenException.EXCEPTION;
        }catch (Exception e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = customUserDetailService.loadUserByUsername(getUsername(token).getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            String type = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("type", String.class);
            return type.equals("access");
        } catch (Exception e){
            throw InvalidTokenException.EXCEPTION;
        }
    }

    public boolean isRefreshToken(String refreshToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(refreshToken)
                    .getBody();

            return claims.get("type").equals("refresh") && !claims.getExpiration().before(new Date());
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            throw IncorrectTokenException.EXCEPTION;
        } catch (ExpiredJwtException e){
            throw ExpiredRefreshTokenException.EXCEPTION;
        } catch (Exception e){
            throw InvalidTokenException.EXCEPTION;
        }
    }


}
