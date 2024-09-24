package Project.SeoulBuilding.backend.login.sevice;

import Project.SeoulBuilding.backend.login.Entity.UserEntity;
import Project.SeoulBuilding.backend.login.dto.JoinDto;
import Project.SeoulBuilding.backend.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public JoinService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder= bCryptPasswordEncoder;
    }


    public void joinProcess(JoinDto joinDto) {


        boolean isUser = userRepository.existsByUsername(joinDto.getUsername());
        if(isUser){
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(joinDto.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}
