package Project.SeoulBuilding.backend.jwt;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BusinessException extends RuntimeException {
   public static final String INVALID_EXPIRED_JWT = "토큰이 만료되었습니다";
   public static final String INVALID_JWT = "유효하지 않은 토큰입니다";
   public static final String MEMBER_NOT_FOUND = "사용자를 찾을수 없습니다";
   public static final String EXIST_USER = "중복된 유저입니다";
   public static final String INVALID_JWT_SINGATURE = "유효하지 않은 JWT서명";
   public static final String UNSUPPORTED_JWT_TOKEN = "지원하지 않는 JWT토큰";
   public static final String CLAIMS_EMPTY = "JWT 클레임 문자열이 빔";
   public BusinessException(String message) {
      super(message);
   }
}
