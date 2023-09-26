package com.wellsbabo.springbootblog.service;

import com.wellsbabo.springbootblog.domain.User;
import com.wellsbabo.springbootblog.dto.AddUserRequest;
import com.wellsbabo.springbootblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public Long save(AddUserRequest dto){
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))  //패스워드 암호화 후 저장
                .build()).getId();
    }
}
