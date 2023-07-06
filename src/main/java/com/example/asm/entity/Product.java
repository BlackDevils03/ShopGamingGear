package com.example.asm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Products")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Image")
    private String image;

    @Column(name = "Price")
    private double price;

    @Column(name = "CreateDate")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDate;


    @Column(name = "Available")
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "CategoryId")
    private Category category;

}

//
//package edu.poly.entity;
//
//        import jakarta.persistence.Column;
//        import jakarta.persistence.Entity;
//        import jakarta.persistence.GeneratedValue;
//        import jakarta.persistence.GenerationType;
//        import jakarta.persistence.Id;
//        import jakarta.persistence.JoinColumn;
//        import jakarta.persistence.ManyToOne;
//        import jakarta.persistence.Table;
//        import jakarta.persistence.Temporal;
//        import jakarta.persistence.TemporalType;
//        import jakarta.validation.constraints.Max;
//        import jakarta.validation.constraints.Min;
//        import jakarta.validation.constraints.NotBlank;
//        import jakarta.validation.constraints.NotNull;
//        import lombok.AllArgsConstructor;
//        import lombok.Builder;
//        import lombok.Getter;
//        import lombok.NoArgsConstructor;
//        import lombok.Setter;
//        import lombok.ToString;
//        import org.springframework.format.annotation.DateTimeFormat;
//
//        import java.io.Serializable;
//        import java.util.Date;
//
//@Entity
//@Table(name = "Products")
//@Getter
//@Setter
//@ToString
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class Product implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "Id")
//    private int id;
//
//
//    @Column(name = "Name")
//    @NotBlank(message = "Vui lòng nhập họ và tên")
//    private String name;
//
//    @Column(name = "Image")
//    private String image;
//
//
//    @Column(name = "Price")
//    @Min(value = 1, message = "Giá phải lớn hơn hoặc bằng 1")
//    @Max(value = 1000000000, message = "Giá phải nhỏ hơn hoặc bằng 1000000000")
//    @NotNull(message = "Vui lòng nhập giá")
//    private double price;
//
//
//    @Column(name = "CreateDate")
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @NotNull(message = "Vui lòng chọn ngày")
//    private Date createDate;
//
//
//    @Column(name = "Available")
//    private boolean available;
//
//    @ManyToOne
//    @JoinColumn(name = "CategoryId")
//    private Category category;
//
//}
