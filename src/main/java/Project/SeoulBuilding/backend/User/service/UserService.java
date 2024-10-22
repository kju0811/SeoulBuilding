package Project.SeoulBuilding.backend.User.service;

import Project.SeoulBuilding.backend.User.domain.User;
import Project.SeoulBuilding.backend.User.dto.UserRequestDTO;
import Project.SeoulBuilding.backend.User.dto.UserResponseDTO;
import Project.SeoulBuilding.backend.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // USER 조회
    @Transactional(readOnly = true)
    public UserResponseDTO findByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException(email + "에 해당하는 유저를 찾을 수 없습니다."));
        return new UserResponseDTO(user);
    }

    // USER 수정
    @Transactional
    public void update(String email, UserRequestDTO requestDTO) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException(email + "에 해당하는 유저를 찾을 수 없습니다."));
        user.update(requestDTO);
    }

    // USER 삭제
    @Transactional
    public void delete(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException(email + "에 해당하는 유저를 찾을 수 없습니다."));
        this.userRepository.delete(user);
    }

}
