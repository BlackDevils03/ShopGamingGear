package com.example.asm.controller;


import com.example.asm.entity.Order;
import com.example.asm.entity.OrderDetail;
import com.example.asm.sevice.OrderDetailService;
import com.example.asm.sevice.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;


    @RequestMapping("/view")
    public String viewCart(Model model, HttpSession session) {
        List<OrderDetail> list = orderDetailService.getAll();
        for (OrderDetail orderDetail : list) {
            if (orderDetail.getOrder() == null) {
                orderDetailService.delete(orderDetail.getId());
            }
        }
        Order order = (Order) session.getAttribute("order");
        model.addAttribute("cart", orderDetailService.getAllByOrder(order.getId()));
        model.addAttribute("main", "cart");
        List<OrderDetail> orderDetails = orderDetailService.getAllByOrder(order.getId());
        Double total = 0.0;
        for (OrderDetail orderDetail : orderDetails) {
            total += orderDetail.getPrice() * orderDetail.getQuantity();
        }
        model.addAttribute("total", total);
        return "main";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        orderDetailService.delete(id);
        return "redirect:/cart/view";
    }

    @PostMapping("/pay")
    public String pay(HttpSession session, Model model, @RequestParam("address") String diaChi) {
        System.out.println(diaChi);
        Order order = (Order) session.getAttribute("order");
        order.setStatus(1);
        order.setAddress(diaChi);
        orderService.saveOrUpdate(order);
        session.removeAttribute("order");
        return "redirect:/home/view";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") int id, @RequestParam("idOrder") Long id1, @RequestParam("qty") int quantity, HttpSession session) {
        orderDetailService.update(id, quantity, id1);
        return "redirect:/cart/view";
    }
}

