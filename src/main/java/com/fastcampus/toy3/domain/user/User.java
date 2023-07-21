package com.fastcampus.toy3.domain.user;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@ToString
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

    @Column
    private String roles;

    @ColumnDefault("true")
    private Boolean status; // true, false -> 만약에 관리자가 role를 변경할때 false로 설정이되고 등업조건을 만족하더라도 자동등업을 막는 역할, 기본값은 true

//    @CreationTimestamp // 우리가 직접 안넣어도 flush 할때 넣어준다.
//    private Timestamp createdAt;
//    @CreationTimestamp // 우리가 직접 안넣어도 flush 할때 넣어준다.
//    private Timestamp updatedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void prePersist() {
        this.roles = "새싹회원";
    }

    @PrePersist //persist가 동작하기 직전에 동작 -> flush 되기 직전에 동작 , db에 insert 되면 동작
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate // 더치 체킹되서 update되기 직전?
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }



    @Builder
    public User(Long id, String username, String password, String email, String nickname, String roles, Boolean status, LocalDateTime createdAt, LocalDateTime updatedAt) {
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