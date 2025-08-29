package com.green.greengram.application.user.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserSignInReq {

    @NotNull(message = "아이디는 필수로 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9_]{4,50}$")
    private String uid;

    @NotNull(message = "비밀번호는 필수로 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+\\-={}\\[\\]:;\"'<>?,./]{8,20}$",
            message = "비밀번호는 영문자와 숫자를 포함하고, 특수문자 포함 8~20자 이내여야 합니다."
    )
    private String upw;
}
