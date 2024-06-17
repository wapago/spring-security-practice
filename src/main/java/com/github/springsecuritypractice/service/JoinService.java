package com.github.springsecuritypractice.service;

import com.github.springsecuritypractice.dto.JoinDTO;
import com.github.springsecuritypractice.repository.users.UserEntity;
import com.github.springsecuritypractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {


        //db에 이미 동일한 username을 가진 회원이 존재하는지?
        boolean isExists = userRepository.existsByUsername(joinDTO.getUsername());
        System.out.println("isExists = " + isExists);

        UserEntity data = new UserEntity();

        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setRole("ROLE_USER");

        userRepository.save(data);
    }
}
