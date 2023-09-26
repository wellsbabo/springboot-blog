package com.wellsbabo.springbootblog.config;

import com.wellsbabo.springbootblog.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
class WebSecurityConfig {

    private final UserDetailService userService;

    // 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http

                .authorizeHttpRequests( // 인증, 인가 설정
                        authorize -> authorize
                                .requestMatchers("/login","/signup","/user").permitAll()
                                .anyRequest().authenticated()
                )

                .formLogin( //폼 기반 로그인 설정
                    form -> form
                            .loginPage("/login")
                            .defaultSuccessUrl("/articles")
                )

                .logout(    //로그아웃 설정
                        logout -> logout
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                )

                .csrf().disable()   //csrf 비활성화 -> REST API 방식에는 CSRF 공격을 받을 가능성이 존재하지 않음
                .build();
    }

    //인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDeatilService) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDeatilService)    //사용자 정보 service 설정
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    //패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
