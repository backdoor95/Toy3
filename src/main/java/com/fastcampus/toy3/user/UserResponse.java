package com.fastcampus.toy3.user;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {
    @Setter
    @Getter
    public static class JoinDTO {
        private Long id;
        private String username;
        private String nickName;

        public JoinDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.nickName = user.getNickName();
        }
    }
}
