package Project.SeoulBuilding.backend.sevice;

import Project.SeoulBuilding.backend.Entity.UserEntity;
import Project.SeoulBuilding.backend.dto.CustomUserDetails;
import Project.SeoulBuilding.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final  UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       UserEntity userData = userRepository.findByUsername(username);

       if (userData != null) {

           return new CustomUserDetails(userData);
       }

        return null;
    }
}
