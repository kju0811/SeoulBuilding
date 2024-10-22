package Project.SeoulBuilding.backend.auth.service;

import Project.SeoulBuilding.backend.User.domain.User;
import Project.SeoulBuilding.backend.User.domain.UserPrincipal;
import Project.SeoulBuilding.backend.User.dto.UserRequestDTO;
import Project.SeoulBuilding.backend.User.repository.UserRepository;
import Project.SeoulBuilding.backend.auth.dto.AuthrequestDTO;
import Project.SeoulBuilding.backend.auth.dto.AuthresponseDTO;
import Project.SeoulBuilding.backend.auth.entity.Auth;
import Project.SeoulBuilding.backend.auth.repository.AuthRepository;
import Project.SeoulBuilding.backend.jwt.jwtclass.JwtGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    /**
    로그인
     **/
    @Transactional
    public AuthresponseDTO login(AuthrequestDTO authrequestDTO) {
        // 유저 아이디 비번 체크
        User user = this.userRepository.findByEmail(authrequestDTO.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException(authrequestDTO.getEmail() + "유저를 찾을수 없습니다."));
        if (!passwordEncoder.matches(authrequestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException(authrequestDTO.getEmail() + "비밀번호가 일치하지 않습니다");
        }
        // 토큰 생성
        String accesstoken = this.jwtGenerator.generateAccessToken(
                new UsernamePasswordAuthenticationToken(new UserPrincipal(user), user.getPassword()));
        String refreshtoken = this.jwtGenerator.generateRefreshToken(
                new UsernamePasswordAuthenticationToken(new UserPrincipal(user), user.getPassword()));
        //해당 인증 엔티티가 존재하는지 확인한 후, 토큰을 업데이트
        if(this.authRepository.existsByUser(user)) {
            user.getAuth().updateAccessToken(accesstoken);
            user.getAuth().updateRefreshToken(refreshtoken);
        }

        //인증 엔티티가 존재하지 않으면, 인증 엔티티와 토큰을 저장
        Auth auth = this.authRepository.save(Auth.builder()
                .user(user)
                .tokentype("Bearer ")
                .accesstoken(accesstoken)
                .refreshtoken(refreshtoken)
                .build());
        return new AuthresponseDTO(auth);
    }
    /**
      회원가입
     **/
    @Transactional
    public void signup(UserRequestDTO userRequestDTO) {
        // 유저 엔티티에 저장
        userRequestDTO.setRole(User.Role.USER);
        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        this.userRepository.save(userRequestDTO.toEntity());
    }

    /**
      토큰 갱신
     **/
    @Transactional
    public String refresh(String refreshtoken, boolean isAccessToken) {
        //리프레시 토큰의 유효 기간을 확인하고, 액세스 토큰을 업데이트한 후 반환
        if (this.jwtGenerator.validateToken(refreshtoken,isAccessToken)) {
            Auth auth = this.authRepository.findByRefreshToken(refreshtoken).orElseThrow(()->
                    new IllegalArgumentException("해당 REFRESH_TOKEN을 찾을 수 없습니다 : " + refreshtoken));

            String newAccessToken = this.jwtGenerator.generateAccessToken(
                    new UsernamePasswordAuthenticationToken(
                            new UserPrincipal(auth.getUser()),auth.getUser().getPassword()));
            auth.updateAccessToken(newAccessToken);
            return newAccessToken;
        }
        return null;
    }

}
