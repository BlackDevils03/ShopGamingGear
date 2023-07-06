package com.example.asm.repository;


import com.example.asm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order,Long> {
    Order findOrderById(Long id);
    List<Order> findAllByStatus(int status);
}
