package com.example.asm.repository;

import com.example.asm.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findOrderDetailsByOrder_Id(Long id);


    OrderDetail findOrderDetailByProduct_IdAndOrder_Id(int id,Long id1);
}
