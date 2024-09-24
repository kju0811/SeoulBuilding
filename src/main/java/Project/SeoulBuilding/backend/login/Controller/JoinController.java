package Project.SeoulBuilding.backend.login.Controller;

import Project.SeoulBuilding.backend.login.dto.JoinDto;
import Project.SeoulBuilding.backend.login.sevice.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/join")
    public String join() {

        return "Join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDto joinDto) {

        System.out.println(joinDto.getUsername());

        joinService.joinProcess(joinDto);

        return "redirect:/login";
    }
}