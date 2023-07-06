package com.example.asm.sevice.impl;

import com.example.asm.entity.OrderDetail;
import com.example.asm.repository.OrderDetailDAO;
import com.example.asm.sevice.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Override
    public List<OrderDetail> getAll() {
        return orderDetailDAO.findAll();
    }

    @Override
    public List<OrderDetail> getAllByOrder(long iđ) {
        return orderDetailDAO.findOrderDetailsByOrder_Id(iđ);
    }

    @Override
    public void save(OrderDetail orderDetail) {
        OrderDetail orderDetail1 = orderDetailDAO.findOrderDetailByProduct_IdAndOrder_Id(orderDetail.getProduct().getId(), orderDetail.getOrder().getId());
        if (orderDetail1 != null) {
            if (orderDetail.getQuantity() > orderDetail1.getQuantity()) {
                orderDetail1.setQuantity(orderDetail1.getQuantity() + orderDetail.getQuantity());
                orderDetailDAO.save(orderDetail1);
            } else {
                orderDetail1.setQuantity(orderDetail1.getQuantity() + 1);
                orderDetailDAO.save(orderDetail1);
            }
        } else {
            orderDetailDAO.save(orderDetail);
        }
    }

    @Override
    public void update(int id, int quantity, Long id1) {
        OrderDetail orderDetail = orderDetailDAO.findOrderDetailByProduct_IdAndOrder_Id(id, id1);
        if (orderDetail != null) {
            orderDetail.setQuantity(quantity);

        }
        orderDetailDAO.save(orderDetail);
    }

    @Override
    public void delete(Long id) {
        orderDetailDAO.deleteById(id);
    }

    @Override
    public OrderDetail findOne(Long id) {
        return orderDetailDAO.getOne(id);
    }
}
