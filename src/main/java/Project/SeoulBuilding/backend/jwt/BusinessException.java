package Project.SeoulBuilding.backend.jwt;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BusinessException extends RuntimeException {
   public static final String INVALID_EXPIRED_JWT = "토큰이 만료되었습니다";
   public static final String INVALID_JWT = "유효하지 않은 토근입니다";
   public static final String MEMBER_NOT_FOUND = "사용자를 찾을수 없습니다";
   public BusinessException(String message) {
      super(message);
   }

}
