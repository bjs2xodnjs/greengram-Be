package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // 상속받은 클래스에 userAt 필드 포함됨
@EqualsAndHashCode // 생성 시점 자동 기록
public class User extends UpdatedAt{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long userId;

        @Column(nullable = false, length = 50)
        private String uid;

        @Column(nullable = false, length = 100)
        private String upw;

        @Column(length = 30)
        private String nickName; //nick_name

        @Column(length = 100)
        private String pic;
}

