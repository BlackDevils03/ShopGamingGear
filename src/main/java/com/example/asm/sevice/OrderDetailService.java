package com.example.asm.sevice;

import com.example.asm.entity.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {
    List<OrderDetail> getAll();

    List<OrderDetail> getAllByOrder(long iđ);

    void save(OrderDetail orderDetail);

    void delete(Long id);

    OrderDetail findOne(Long id);

    void update(int id, int quantity,Long id1);
}
