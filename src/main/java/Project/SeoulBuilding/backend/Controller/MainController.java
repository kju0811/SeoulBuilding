package Project.SeoulBuilding.backend.Controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

@RestController
public class MainController {

    @GetMapping("/")
    public String mainP(Model model) {

        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        //454b526c4c6d656d313331544a57526b api 인증키
        return "main";
    }
    @PostMapping("/api")
    public String Building (@RequestParam("FCLT_NM") String name, Model model ) {
        String result = "";

        try {
            String requestdata = name;
            URL url = new URL("http://openapi.seoul.go.kr:8088/454b526c4c6d656d313331544a57526b/json/tbEntranceItem/1/20/"
                    + requestdata);

        }
    }
}

