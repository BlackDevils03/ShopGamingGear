package com.example.asm.controller;

import com.example.asm.entity.Account;
import com.example.asm.entity.Order;
import com.example.asm.entity.OrderDetail;
import com.example.asm.sevice.AccountService;
import com.example.asm.sevice.OrderDetailService;
import com.example.asm.sevice.OrderService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.NumberFormat;
import java.util.Locale;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/orders/add")
    public String showAddOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "add-order";
    }

    @PostMapping("/orders")
    public String addOrder(@ModelAttribute Order order) {
        // save order to database
        return "redirect:/orders";
    }

    @GetMapping("/history")
    public String history(Model model, HttpSession session) {
        Account login = (Account) session.getAttribute("account");
        if (!login.isAdmin() || !login.isActive()) {
            session.setAttribute("login", "Fail");
            return "redirect:/home/view";
        } else {
            session.setAttribute("login", "done");
        }
        model.addAttribute("orders", orderService.findAll(1));
////        List<Map<String, Object>> chartData = new ArrayList<>();
////        for (OrderDetail orderDetail : orderDetailService.getAll()) {
////            int productId = orderDetail.getProduct().getId();
////            int quantity = orderDetail.getQuantity();
////            Map<String, Object> data = new HashMap<>();
////            data.put("label", productId);
////            data.put("value", quantity);
////            chartData.add(data);
////        }
//        List<Order> chartData = orderService.findAll(1);
//        model.addAttribute("chartData", chartData);
//        model.addAttribute("data", orderDetailService.getAll());
        model.addAttribute("main", "history");
        return "main";
    }

    @PostMapping("/sendMail")
    public String sendMail(@RequestParam("gmail") String gmail, Model model) throws MessagingException {
        Double aDouble = 0.0;
        StringBuilder sb = new StringBuilder();
        for (OrderDetail orderDetail : orderDetailService.getAll()) {
            aDouble += orderDetail.getPrice() * orderDetail.getQuantity();
            Locale locale = new Locale("vi", "VN");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
            String formattedCurrencys = currencyFormatter.format(orderDetail.getQuantity() * orderDetail.getPrice());
            sb.append("\n");
            sb.append(orderDetail.getProduct().getName())
                    .append("\n")
                    .append(" - Số lượng: ")
                    .append(orderDetail.getQuantity())
                    .append("\n")
                    .append(", Thành tiền: ")
                    .append(formattedCurrencys)
                    .append("\n");
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String formattedCurrency = currencyFormatter.format(aDouble);
        model.addAttribute("orders", orderService.findAll(1));
        accountService.guiMail(gmail, "Tỏng hợp doanh thu", String.valueOf(sb + "\n" + "Tổng doanh thu:" + formattedCurrency));
        model.addAttribute("main", "history");
        return "main";
    }
}
