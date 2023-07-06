package com.example.asm.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailResponse {
    private Long id;
    private Long orderId;
    private Integer productId;
    private Double price;
    private Integer quantity;
}
