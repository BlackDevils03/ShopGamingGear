package com.example.asm.response;

import com.example.asm.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponse {
    private Integer id;
//    @NotBlank(message = "Vui lòng nhập họ và tên")
    private String name;
    private MultipartFile image;
//    @Min(value = 1, message = "Giá phải lớn hơn hoặc bằng 1")
//    @Max(value = 1000000000, message = "Giá phải nhỏ hơn hoặc bằng 1000000000")
//    @NotNull(message = "Vui lòng nhập giá")
    private Double price;
//    @NotNull(message = "Vui lòng chọn ngày")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private Boolean available;
    private Category categoryId;
}
