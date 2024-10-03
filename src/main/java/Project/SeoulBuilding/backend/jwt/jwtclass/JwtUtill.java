package Project.SeoulBuilding.backend.jwt.jwtclass;

import Project.SeoulBuilding.backend.jwt.BusinessException;
import Project.SeoulBuilding.backend.jwt.constant.JwtRule;
import Project.SeoulBuilding.backend.jwt.constant.TokenStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

import static Project.SeoulBuilding.backend.jwt.BusinessException.INVALID_EXPIRED_JWT;
import static Project.SeoulBuilding.backend.jwt.BusinessException.INVALID_JWT;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtUtill {

    //해당 토큰의 유효 기간이 지나지 않았고 & 유효한지 여부를 파악
    public TokenStatus getTokenStatus(String token, Key secretkey) {
        try {
            Jwts.parserBuilder()
                    //JWT의 서명을 검증하기 위한 키를 설정.이 키는 JWT를 생성할 때 사용한 키와 일치
                    .setSigningKey(secretkey)
                    //JWT 파서 인스턴스를 생성
                    .build()
                    //전달된 JWT 토큰을 파싱하여 클레임을 반환
                    .parseClaimsJws(token);
            return TokenStatus.AUTHENTICATED;
            //유효기간 만료 혹은 잘못된 인자 전달로 인한 예외처리
        }catch (ExpiredJwtException | IllegalArgumentException e) {
            log.error(INVALID_EXPIRED_JWT);
            return TokenStatus.EXPIRED;
        }catch (JwtException e) {
            throw new BusinessException(INVALID_JWT);
        }
    }
    public String resolveToken(Cookie[] cookies, JwtRule tokenPrefix) {
        //쿠키 배열을 스트림으로 변환
        return Arrays.stream(cookies)
                //쿠키의 이름이 tokenPrefix 값과 같은지를 확인하여, 해당하는 쿠키만 필터링
                .filter(cookie -> cookie.getName().equals(tokenPrefix.getValue()))
                //첫번째 쿠키를 찾음
                .findFirst()
                //찾은 쿠키로부터 값을 가져옴
                .map(Cookie::getValue)
                //일치하는 쿠키 없음 빈문자열 반환
                .orElse("");
    }

    public Key getSigningkey(String secretkey) {
        String encodedKey = encodeToBase64(secretkey);
        return Keys.hmacShaKeyFor(encodedKey.getBytes(StandardCharsets.UTF_8));
    }

    private String encodeToBase64(String secretkey) {
        return Base64.getEncoder().encodeToString(secretkey.getBytes());
    }
    
    public Cookie resetToken(JwtRule tokenPrefix) {
        Cookie cookie = new Cookie(tokenPrefix.getValue(), null); // 지정된 이름의 쿠키를 'null'로 설정
        cookie.setMaxAge(0); // 쿠키의 유효기간을 0으로 설정하여 즉시 만료시킴 (즉, 쿠키 삭제)
        cookie.setPath("/"); // 쿠키의 경로를 지정. '/'로 설정하면 해당 애플리케이션 전체에서 쿠키 삭제
        return cookie; // 삭제될 쿠키 객체를 반환
    }
}
