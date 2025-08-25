package com.green.greengram.config.security.oauth;

import com.green.greengram.config.enumcode.model.EnumUserRole;
import com.green.greengram.config.model.JwtUser;
import lombok.Getter;

import java.util.List;

@Getter
public class OAuth2JwtUser extends JwtUser {
    private final String nickName;
    private final String pic;

    public OAuth2JwtUser(String nickName, String pic, long signedUserId, List<EnumUserRole> roles) {
        super(signedUserId, roles);
        this.nickName = nickName;
        this.pic = pic;
    }
}
