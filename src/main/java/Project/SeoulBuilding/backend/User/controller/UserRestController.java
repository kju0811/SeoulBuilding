package Project.SeoulBuilding.backend.User.controller;

import Project.SeoulBuilding.backend.User.dto.UserRequestDTO;
import Project.SeoulBuilding.backend.User.dto.UserResponseDTO;
import Project.SeoulBuilding.backend.User.service.UserService;
import Project.SeoulBuilding.backend.jwt.jwtclass.JwtGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;
    private final JwtGenerator jwtGenerator;

    //회원정보조회 API
    @GetMapping ("/find")
    public ResponseEntity<?> findUser(@RequestHeader("Authorization") String accesstoken) {
        try {
            if(accesstoken != null && accesstoken.startsWith("Bearer ")) {
                String token = accesstoken.substring(7);
                boolean isAccesstoken = token.startsWith("access");
                Long id = jwtGenerator.getUserIdFromToken(token,isAccesstoken);
                UserResponseDTO userResponseDTO = userService.findByUsername(id.toString());
                return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 인증 헤더");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    //회원정보수정 API
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String accesstoken, @RequestBody UserRequestDTO userRequestDTO) {
        try{
            if(accesstoken != null && accesstoken.startsWith("Bearer ")) {
                String token = accesstoken.substring(7);
                boolean isAccesstoken = token.startsWith("access");
                Long id = jwtGenerator.getUserIdFromToken(token,isAccesstoken);
                userService.update(String.valueOf(id), userRequestDTO);

                return ResponseEntity.status(HttpStatus.OK).body("유저 정보를 업데이트하였습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유저 정보 업데이트에 실패하였습니다");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    //회원정보 삭제 API
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String accesstoken) {
        try {
            if (accesstoken != null && accesstoken.startsWith("Bearer ")) {
                String token = accesstoken.substring(7);
                boolean isAccesstoken = token.startsWith("access");
                Long id = jwtGenerator.getUserIdFromToken(token, isAccesstoken);
                userService.delete(String.valueOf(id));

                return ResponseEntity.status(HttpStatus.OK).body("유저가 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유저 삭제에 실패하였습니다");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
