package com.example.asm.controller;

import com.example.asm.entity.Account;
import com.example.asm.entity.Order;
import com.example.asm.entity.OrderDetail;
import com.example.asm.entity.Product;
import com.example.asm.sevice.AccountService;
import com.example.asm.sevice.OrderDetailService;
import com.example.asm.sevice.OrderService;
import com.example.asm.sevice.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private Page<Product> productPage;
    private Sort sort;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderDetailService orderDetailService;


    @GetMapping("/view")
    public String home(Model model, HttpSession session) {
        if (session.getAttribute("login") == null) {
            session.setAttribute("login", "done");
        }
        if (session.getAttribute("login").equals("Fail")) {
            model.addAttribute("showAlert", true);
            session.setAttribute("login","done");
        } else {
            model.addAttribute("showAlert", false);
        }
        sort = Sort.by(Sort.Direction.DESC, "name");
        model.addAttribute("sort", "name");
        Pageable pageable = PageRequest.of(0, 6, sort);
        productPage = productService.getAllAvalible(true, pageable);
        System.out.println(productPage);
        model.addAttribute("product", productPage);
        int totalPages = productPage.getTotalPages();
        List<Integer> listPage = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            listPage.add(i);
        }
        model.addAttribute("pageNumber", listPage);
        model.addAttribute("main", "index");
        return "main";
    }

    @GetMapping("/viewSort")
    public String homeSort(Model model, @RequestParam("sort") String sortIndex) {
        System.out.println(sortIndex);
        if (sortIndex.equals("name")) {
            sort = Sort.by(Sort.Direction.DESC, "name");
            model.addAttribute("sort", "name");
            Pageable pageable = PageRequest.of(0, 6, sort);
            productPage = productService.getAllAvalible(true, pageable);
            System.out.println(productPage);
            model.addAttribute("product", productPage);
            int totalPages = productPage.getTotalPages();
            List<Integer> listPage = new ArrayList<>();
            for (int i = 0; i < totalPages; i++) {
                listPage.add(i);
            }
            model.addAttribute("pageNumber", listPage);
        }
        if (sortIndex.equals("price")) {
            sort = Sort.by(Sort.Direction.DESC, "price");
            model.addAttribute("sort", "price");
            Pageable pageable = PageRequest.of(0, 6, sort);
            productPage = productService.getAllAvalible(true, pageable);
            System.out.println(productPage);
            model.addAttribute("product", productPage);
            int totalPages = productPage.getTotalPages();
            List<Integer> listPage = new ArrayList<>();
            for (int i = 0; i < totalPages; i++) {
                listPage.add(i);
            }
            model.addAttribute("pageNumber", listPage);
        }
        model.addAttribute("main", "index");
        return "main";
    }

    @GetMapping("/getPage/{page}")
    public String totalPage(@PathVariable("page") int page, Model model) throws IOException {
        Pageable pageable = PageRequest.of(page, 6, sort);
        model.addAttribute("sort", sort.toString());
        productPage = productService.getAllAvalible(true, pageable);
        model.addAttribute("product", productPage);
        int totalPages = productPage.getTotalPages();
        List<Integer> listPage = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            listPage.add(i);
        }
        model.addAttribute("pageNumber", listPage);
        model.addAttribute("main", "index");
        return "main";
    }

    @GetMapping("/addCart/{id}")
    public String addCart(@ModelAttribute("product") Product product, @PathVariable("id") int id, HttpSession session) {
        Date date = new Date();
        Account account = (Account) session.getAttribute("account");
        if (session.getAttribute("order") == null) {
            Order order = new Order();
            order.setAccount(account);
            order.setCreateDate(date);
            order.setStatus(0);
            OrderDetail orderDetail1 = new OrderDetail();
            Order order1 = orderService.saveOrUpdate(order);
            session.setAttribute("order", order1);
            orderDetail1.setProduct(productService.findOne(id));
            orderDetail1.setQuantity(1);
            orderDetail1.setPrice(productService.findOne(id).getPrice());
            orderDetail1.setOrder(order1);
            orderDetailService.save(orderDetail1);
        } else {
            Order order = (Order) session.getAttribute("order");
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(productService.findOne(id));
            orderDetail.setQuantity(1);
            orderDetail.setPrice(productService.findOne(id).getPrice());
            orderDetailService.save(orderDetail);
        }

        return "redirect:/cart/view";
    }

    @PostMapping("/addCartDetail/{id}")
    public String addCartDetail(@ModelAttribute("product") Product product, @PathVariable("id") int id, HttpSession session, @RequestParam("qty") int quantity, Model model) {
        Date date = new Date();
        Account account = (Account) session.getAttribute("account");
        if (!account.isAdmin() && !account.isActive()) {
            model.addAttribute("showAlert", true);
            return "redirect:/home/view";
        }
        if (session.getAttribute("order") == null) {
            Order order = new Order();
            order.setAccount(account);
            order.setCreateDate(date);
            order.setStatus(0);
            OrderDetail orderDetail1 = new OrderDetail();
            Order order1 = orderService.saveOrUpdate(order);
            session.setAttribute("order", order1);
            orderDetail1.setProduct(productService.findOne(id));
            if (quantity == 0) {
                orderDetail1.setQuantity(1);
            } else {
                orderDetail1.setQuantity(quantity);
            }
            orderDetail1.setPrice(productService.findOne(id).getPrice());
            orderDetail1.setOrder(order1);
            orderDetailService.save(orderDetail1);
        } else {
            Order order = (Order) session.getAttribute("order");
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(productService.findOne(id));
            if (quantity == 0) {
                orderDetail.setQuantity(1);
            } else {
                orderDetail.setQuantity(quantity);
            }
            orderDetail.setPrice(productService.findOne(id).getPrice());
            orderDetailService.save(orderDetail);
        }

        return "redirect:/cart/view";
    }

    @GetMapping("/detail/{id}")
    public String detail(@ModelAttribute("product") Product product, @PathVariable("id") int id, HttpSession session, Model model) {
        model.addAttribute("product", productService.findOne(id));
        model.addAttribute("main", "detailProduct");
        return "main";
    }
}
