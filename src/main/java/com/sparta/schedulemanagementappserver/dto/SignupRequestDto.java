package com.sparta.schedulemanagementappserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank(message = "사용자 이름은 필수값 입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    @NotBlank(message = "비밀번호는 필수값 입니다.")
    private String password;
    @NotBlank
    private String nickname;
    private boolean admin = false;
    private String adminToken = "";
}
