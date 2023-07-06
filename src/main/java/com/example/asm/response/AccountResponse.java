package com.example.asm.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountResponse {

        private String username;
        private String password;
        private String fullname;
        private String email;
        private MultipartFile photo;
        private Boolean active;
        private Boolean admin;
}