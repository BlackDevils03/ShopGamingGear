package com.example.asm.sevice;

import com.example.asm.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    void delete(Long id);

    List<Order> getAll();

    void deleteBystatus(List<Order> status);

    List<Order> findAll(int status);
    Order findOne(Long id);

    Order saveOrUpdate(Order order);
}
