package com.example.asm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "Accounts")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Fullname")
    private String fullname;

    @Column(name = "Email")
    private String email;

    @Column(name = "Photo")
    private String photo;

    @Column(name = "Active")
    private boolean active;

    @Column(name = "Admin")
    private boolean admin;

    @OneToMany(mappedBy = "account")
    private Set<Order> orders;
}

//package edu.poly.entity;
//
//        import jakarta.persistence.Column;
//        import jakarta.persistence.Entity;
//        import jakarta.persistence.Id;
//        import jakarta.persistence.OneToMany;
//        import jakarta.persistence.Table;
//        import jakarta.validation.constraints.Email;
//        import jakarta.validation.constraints.NotBlank;
//        import lombok.AllArgsConstructor;
//        import lombok.Builder;
//        import lombok.Getter;
//        import lombok.NoArgsConstructor;
//        import lombok.Setter;
//        import lombok.ToString;
//
//        import java.util.Set;
//
//@Entity
//@Table(name = "Accounts")
//@Getter
//@Setter
//@ToString
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class Account {
//    @NotBlank(message = "Vui lòng nhập username")
//    @Id
//    @Column(name = "Username")
//    private String username;
//
//    @NotBlank(message = "Vui lòng nhập password")
//    @Column(name = "Password")
//    private String password;
//
//    @NotBlank(message = "Vui lòng nhập họ và tên")
//    @Column(name = "Fullname")
//    private String fullname;
//
//    @NotBlank(message = "Vui lòng nhập địa chỉ email")
//    @Email(message = "Vui lòng nhập đúng định dạng email")
//    @Column(name = "Email")
//    private String email;
//
//    @NotBlank(message = "Vui lòng chọn ảnh")
//    @Column(name = "Photo")
//    private String photo;
//
//    @Column(name = "Active")
//    private boolean active;
//
//    @Column(name = "Admin")
//    private boolean admin;
//
//    @OneToMany(mappedBy = "account")
//    private Set<Order> orders;
//}