package com.fastcampus.toy3.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import java.sql.Timestamp;

@DynamicInsert
@NoArgsConstructor// 이 친구가 없으면 select 조회했을때, 영속성 컨텍스트에 User 엔티티가 안만들어진다.
@Getter
@Setter
@Table(name = "user_tb")
@Entity
public class User { // extends 시간설정 (상속)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto-increase
    private Long id;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 120) // 패스워드 인코딩(BCrypt)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(unique = true, nullable = false, length = 20)
    private String nickname;

    @Column(columnDefinition = "VARCHAR(255) default '새싹회원'")
    private String roles; // USER, ADMIN -> 권한이 2개가 될수있다.

    @ColumnDefault("true")
    private Boolean status; // true, false -> 만약에 관리자가 role를 변경할때 false로 설정이되고 등업조건을 만족하더라도 자동등업을 막는 역할, 기본값은 true

    @CreationTimestamp // 우리가 직접 안넣어도 flush 할때 넣어준다.
    private Timestamp createdAt;
    @CreationTimestamp // 우리가 직접 안넣어도 flush 할때 넣어준다.
    private Timestamp updatedAt;


    @Builder
    public User(Long id, String username, String password, String email, String nickname, String roles, Boolean status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.roles = roles;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}