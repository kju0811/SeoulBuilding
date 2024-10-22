package Project.SeoulBuilding.backend.jwt.jwtclass;

import Project.SeoulBuilding.backend.User.domain.UserPrincipal;
import Project.SeoulBuilding.backend.jwt.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

//Header와 Claims에 들어가야할 정보를 받아서, JWT를 build해서 반환하는 코드
@Slf4j
@Component
public class JwtGenerator {
    @Value("${ACCESS_SECRET_KEY}")
    private String ACCESS_SECRET_KEY;
    @Value("${REFRESH_SECRET_KEY}")
    private String REFRESH_SECRET_KEY;
    @Value("${access-expiration}")
    private String ACCESS_EXPIRATION;
    @Value("${refresh-expiration}")
    private String REFRESH_EXPIRATION;

    public String generateAccessToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date expirationDate = new Date(new Date().getTime() + ACCESS_EXPIRATION);
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("user-id", userPrincipal.getId())
                .claim("type","access")
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, ACCESS_SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date expirationDate = new Date(new Date().getTime() + REFRESH_EXPIRATION);
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("user-id",userPrincipal.getId())
                .claim("type","refresh")
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, REFRESH_SECRET_KEY)
                .compact();
    }

    public Long getUserIdFromToken(String token,boolean isAccesstoken) {
        String secretKey = isAccesstoken ? ACCESS_SECRET_KEY : REFRESH_SECRET_KEY;

        return Jwts.parser()
                .setSigningKey(secretKey) // 서명 검증에 사용할 시크릿 키 설정
                .parseClaimsJws(token)       // 전달된 JWT 토큰을 파싱하여 Claims 객체를 얻음
                .getBody()                   // JWT의 페이로드(Claims)를 가져옴
                .get("user-id", Long.class); // 페이로드에서 "user-id"라는 클레임 값을 Long 타입으로 가져옴
    }

    public  String getUsernameFromToken(String token,boolean isAccesstoken) {
        String secretKey = isAccesstoken ? ACCESS_SECRET_KEY : REFRESH_SECRET_KEY;

        return Jwts.parser()
                .setSigningKey(secretKey) // 서명 검증에 사용할 시크릿 키 설정
                .parseClaimsJws(token)       // 전달된 JWT 토큰을 파싱하여 Claims 객체를 얻음
                .getBody()                   // JWT의 페이로드(Claims)를 가져옴
                .get("username", String.class);
    }

    public Date getExpirationDateFromToken(String token,boolean isAccesstoken) {
        String secretKey = isAccesstoken ? ACCESS_SECRET_KEY : REFRESH_SECRET_KEY;

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public Boolean validateToken(String token,boolean isAccesstoken) {
        String secretkey = isAccesstoken ? ACCESS_SECRET_KEY : REFRESH_SECRET_KEY;

        try {
            Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error(BusinessException.INVALID_JWT_SINGATURE);
        } catch (MalformedJwtException ex) {
            log.error(BusinessException.INVALID_JWT);
        } catch (ExpiredJwtException ex) {
            log.error(BusinessException.INVALID_EXPIRED_JWT);
        } catch (UnsupportedJwtException ex) {
            log.error(BusinessException.UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException ex) {
            log.error(BusinessException.CLAIMS_EMPTY);
        }
        return false;
    }

    public Key[] getSigningkey() {
        String[] encodedKeys = encodeToBase64();  // 인코딩된 키 배열을 받아옴

        Key accessKey = Keys.hmacShaKeyFor(encodedKeys[0].getBytes(StandardCharsets.UTF_8));  // ACCESS_SECRET_KEY로 HMAC 키 생성
        Key refreshKey = Keys.hmacShaKeyFor(encodedKeys[1].getBytes(StandardCharsets.UTF_8));  // REFRESH_SECRET_KEY로 HMAC 키 생성

        return new Key[]{accessKey, refreshKey};  // 두 개의 Key를 배열로 반환
    }

    private String[] encodeToBase64() {
        String encodedAccessKey = Base64.getEncoder().encodeToString(ACCESS_SECRET_KEY.getBytes());
        String encodedRefreshKey = Base64.getEncoder().encodeToString(REFRESH_SECRET_KEY.getBytes());

        return new String[]{encodedAccessKey, encodedRefreshKey};  // 인코딩된 두 개의 키를 배열로 반환
    }

}

