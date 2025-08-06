package com.green.greengram.entity;

import com.green.greengram.config.enumcode.model.EnumUserRole;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class UserRole {
    @EmbeddedId
    private UserRoleIds userRoleIds;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
}
