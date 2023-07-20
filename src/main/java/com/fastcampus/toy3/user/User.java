package com.fastcampus.toy3.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@NoArgsConstructor// 이 친구가 없으면 select 조회했을때, 영속성 컨텍스트에 User 엔티티가 안만들어진다.
@Getter
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

    @Column(name = "nickname", unique = true, nullable = false, length = 20)
    private String nickName;

    @Column(columnDefinition = "VARCHAR(255) default '새싹회원'")
    private String roles; // USER, ADMIN -> 권한이 2개가 될수있다.

    @ColumnDefault("true")
    private Boolean status; // true, false -> 만약에 관리자가 role를 변경할때 false로 설정이되고 등업조건을 만족하더라도 자동등업을 막는 역할, 기본값은 true

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist //persist가 동작하기 직전에 동작 -> flush 되기 직전에 동작 , db에 insert 되면 동작
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate // 더치 체킹되서 update되기 직전?
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public User(Long id, String username, String password, String email, String nickName, String roles, Boolean status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.roles = roles;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}