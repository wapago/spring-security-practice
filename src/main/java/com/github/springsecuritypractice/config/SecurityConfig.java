package com.github.springsecuritypractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/joinProc", "/join").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );

        // 사이트 위/변조 방지설정 : POST 요청시 csrf 토큰 보내주어야 로그인 진행됨. 개발단계에서는 비활성화.
//        http.
//                csrf((auth) -> auth.disable());

        // form방식 로그인 설정
//        http.
//                formLogin((auth) -> auth.loginPage("/login")
//                        .loginProcessingUrl("/loginProc")
//                        .permitAll()
//                );
        http.
                httpBasic(Customizer.withDefaults());

        http.
                sessionManagement((auth) -> auth
                        .maximumSessions(1) // 하나의 아이디에 대한 다중 로그인 허용 개수
                        .maxSessionsPreventsLogin(true));   // true: 초과시 새로운 로그인 차단

        http.
                sessionManagement((session) -> session
                        .sessionFixation((sessionFixation) -> sessionFixation
                                .newSession()
                        )
                );


        return http.build();
    }

    // 시큐리티 로그인 환경이 필요하지만 소수의 회원 정보만 가지며 데이터베이스라는 자원을 투자하기 힘든 경우
    // 회원가입 없는 InMemory방식
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.builder()
                .username("user1")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("user2")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
