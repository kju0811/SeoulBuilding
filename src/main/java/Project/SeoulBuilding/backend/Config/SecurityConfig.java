package Project.SeoulBuilding.backend.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/login","/joinProc","/join","/api/building").permitAll()
                        .requestMatchers("/admin/**").hasRole("admin")
                        .requestMatchers("/user/**").hasAnyRole("admin","user")
                        .anyRequest().authenticated()
                );
        /*
        http
                .formLogin((auth)->auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login")
                        .permitAll()
                );
        http
                .sessionManagement((auth)-> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));
        http
                .sessionManagement((auth)->auth
                        .sessionFixation().changeSessionId());
        http
                .logout((logout) -> logout
                .logoutSuccessUrl("/login")
                 //http에서 사용자 세션 무효화->데이터 삭제
                .invalidateHttpSession(true))
                .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
*/
        return http.build();
    }
}
