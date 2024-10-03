package Project.SeoulBuilding.backend.jwt.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenStatus {
    AUTHENTICATED, //인증이 성공적
    EXPIRED, //인증 토큰 또는 세션이 만료
    INVALID //인증이 유효하지 않거나 잘못
}
