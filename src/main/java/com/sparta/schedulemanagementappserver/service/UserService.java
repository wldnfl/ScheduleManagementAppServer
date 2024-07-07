package com.sparta.schedulemanagementappserver.service;

import com.sparta.schedulemanagementappserver.dto.SignupRequestDto;
import com.sparta.schedulemanagementappserver.entity.User;
import com.sparta.schedulemanagementappserver.entity.UserRole;
import com.sparta.schedulemanagementappserver.exception.DataConflictException;
import com.sparta.schedulemanagementappserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// 사용자 등록 로직 처리
// 비밀번호 암호화, 사용자 역할 설정, 중복 검사 수행
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret.key}")
    private String ADMIN_TOKEN;

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String password = requestDto.getPassword();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new DataConflictException("중복된 username 입니다.");
        }

        if (!isValidUsername(username) || !isValidPassword(password)) {
            throw new IllegalArgumentException("username 또는 password가 유효하지 않습니다.");
        }

        UserRole role = UserRole.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.ADMIN;
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, nickname, role);
        userRepository.save(user);
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 4 && username.length() <= 10 && username.matches("^[a-z0-9]*$");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.length() <= 15 && password.matches("^[a-zA-Z0-9]*$");
    }
}
