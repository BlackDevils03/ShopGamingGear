package com.example.asm.controller;//package edu.poly.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class OrderDetailController {
//    @GetMapping("/order-details/add")
//    public String showAddOrderDetailForm(Model model) {
//        model.addAttribute("orderDetail", new OrderDetail());
//        return "add-order-detail";
//    }
//
//    @PostMapping("/order-details")
//    public String addOrderDetail(@ModelAttribute OrderDetail orderDetail) {
//        // save orderDetail to database
//        return "redirect:/order-details";
//    }
//}
