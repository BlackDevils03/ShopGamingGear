package com.example.asm.sevice.impl;

import com.example.asm.entity.Order;
import com.example.asm.repository.OrderDAO;
import com.example.asm.sevice.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDAO orderDAO;

    @Override
    public void delete(Long id) {
        orderDAO.deleteById(id);
    }

    @Override
    public List<Order> getAll() {
        return orderDAO.findAll();
    }

    @Override
    public void deleteBystatus(List<Order> status) {
        orderDAO.deleteAll(status);
    }

    @Override
    public List<Order> findAll(int status) {
        return orderDAO.findAllByStatus(status);
    }

    @Override
    public Order findOne(Long id) {
        return orderDAO.findOrderById(id);
    }

    @Override
    public Order saveOrUpdate(Order order) {
        return orderDAO.save(order);
    }
}
