package com.example.asm.controller;

import com.example.asm.entity.Account;
import com.example.asm.entity.Order;
import com.example.asm.sevice.AccountService;
import com.example.asm.sevice.OrderService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;


    @GetMapping
    public String login(Model model) {
        model.addAttribute("error", "");
        return "login";
    }

    @PostMapping
    public String login(Model model, @RequestParam("Username") String username, @RequestParam("Password") String password, HttpSession session) {
        session.setAttribute("name", username);
        if (session.getAttribute("order") != null) {
            session.removeAttribute("order");
        }
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            model.addAttribute("error", "Username or Password khong duoc de trong");
            return "login";
        } else {
            Account account = accountService.login(username, password);
            if (account != null) {
                List<Order> listDelete = orderService.findAll(0);
                orderService.deleteBystatus(listDelete);
                session.setAttribute("account", account);
                return "redirect:/home/view";
            } else {
                model.addAttribute("error", "User or Pass khong chinh xac");
                return "login";
            }
        }
    }

    @PostMapping("/update")
    public String updatePass(@RequestParam("pass1") String code, @RequestParam("Password") String password, HttpSession session) {
//        if ()
        String name = (String) session.getAttribute("name");
        String codeMail = (String) session.getAttribute("code");
        if (code.equals(codeMail)) {
            Account account = accountService.findOne(name);
            account.setPassword(password);
            accountService.saveorUpdate(account);
            return "login";
        } else {
            return "updatePass";
        }
    }

    @GetMapping("/update")
    public String ViewUpdate(@RequestParam("pass1") String code, @RequestParam("Password") String password, HttpSession session) {
        return "updatePass";
    }

    @PostMapping("/send")
    public String sendCode(@RequestParam("email") String email, HttpSession session) throws MessagingException {
//        if ()
        Double aDouble = Math.random();
        String noiDung = accountService.guiMail(email, "mã Xác nhận", String.valueOf(aDouble));
        session.setAttribute("code", noiDung);
        return "updatePass";
    }

    @GetMapping("/send")
    public String viewSend() {
        return "sendCode";
    }
}
