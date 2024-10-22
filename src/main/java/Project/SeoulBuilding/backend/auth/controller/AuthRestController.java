package Project.SeoulBuilding.backend.auth.controller;

import Project.SeoulBuilding.backend.User.dto.UserRequestDTO;
import Project.SeoulBuilding.backend.auth.dto.AuthrequestDTO;
import Project.SeoulBuilding.backend.auth.dto.AuthresponseDTO;
import Project.SeoulBuilding.backend.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthRestController {
    private final AuthService authService;

    /**
     로그인 API
     **/
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody AuthrequestDTO authrequestDTO) {
        AuthresponseDTO authresponseDTO = this.authService.login(authrequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    /**
     회원가입 API
     **/
    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequestDTO userRequestDTO) {
        this.authService.signup(userRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    /**
     토큰갱신 API
     **/
    @GetMapping("/api/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("REFRESH_TOKEN") String refreshToken, boolean isAccesstoken) {
        String newAccesstoken = this.authService.refresh(refreshToken, isAccesstoken);
        return ResponseEntity.status(HttpStatus.OK).body(newAccesstoken);
    }
}
