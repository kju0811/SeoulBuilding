package Project.SeoulBuilding.backend.jwt.jwtclass;


import Project.SeoulBuilding.backend.User.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Header와 Claims에 들어가야할 정보를 받아서, JWT를 build해서 반환하는 코드
@Component
public class JwtGenerator {

    public String generateAccessToken(final Key ACCESS_SECRET, final long ACCESS_EXPIRATION, User user) {
        Long now = System.currentTimeMillis();

        return Jwts.builder()
                //jwt 헤더 설정
                .setHeader(createHeader())
                //jwt 클레임 설정
                .setClaims(createClaims(user))
                //jwt 주제 설정
                .setSubject(user.getEmail())
                //jwt 만료시간 설정
                .setExpiration(new Date(now + ACCESS_EXPIRATION))
                //jwt 서명하는 방법 설정
                .signWith(ACCESS_SECRET, SignatureAlgorithm.HS256)
                //jwt를 문자열로 압축하여 최종 생성
                .compact();
    }

    public String generateRefreshToken(final Key REFRESH_SECRET, final long REFRESH_EXPIRATION, User user) {
        Long now = System.currentTimeMillis();

        return Jwts.builder()
                .setHeader(createHeader())
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(new Date(now + REFRESH_EXPIRATION))
                .signWith(REFRESH_SECRET, SignatureAlgorithm.HS256)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");       // JWT의 유형을 명시
        header.put("alg", "HS512");     // 사용할 서명 알고리즘을 명시
        return header;                  // 생성된 헤더 반환
    }

    private Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        //JWT 클레임에 사용자 ID를 추가,인증된 사용자의 고유 식별 정보를 포함
        claims.put("Identifier", user.getId());
        //사용자의 접근 권한을 서버에서 확인
        claims.put("Role", user.getRole());
        return claims;
    }
}

