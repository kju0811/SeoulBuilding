package Project.SeoulBuilding.backend.jwt.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JwtRule {
    //jwt 발급시 사용되는 헤더 값, 서버가 클라이언트에게 쿠키설정
    JWT_ISSUE_HEADER("Set-cookie"),
    //클라이언트로부터 jwt를 얻을 때 사용되는 헤더 값, 서버에 요청을 보낼 때 클라이언트가 저장한 쿠키를 포함
    JWT_RESOLVE_HEADER("Cookie"),
    //접근(Access) 토큰에 사용되는 접두어,특정 토큰이 접근 토큰임을 나타내는 용도
    ACCESS_PREFIX("access"),
    //갱신(Refresh) 토큰에 사용되는 접두어," "는 토큰이 리프레시 토큰임
    REFRESH_PREFIX("refresh");

    private final String value;
}

