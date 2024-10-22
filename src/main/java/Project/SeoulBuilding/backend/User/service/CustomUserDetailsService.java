package Project.SeoulBuilding.backend.User.service;

import Project.SeoulBuilding.backend.User.domain.User;
import Project.SeoulBuilding.backend.User.domain.UserPrincipal;
import Project.SeoulBuilding.backend.User.repository.UserRepository;
import Project.SeoulBuilding.backend.jwt.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static Project.SeoulBuilding.backend.jwt.BusinessException.EXIST_USER;
import static Project.SeoulBuilding.backend.jwt.BusinessException.MEMBER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(EXIST_USER);
        }
        return new UserPrincipal(user);
    }
}
