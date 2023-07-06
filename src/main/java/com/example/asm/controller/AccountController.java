package com.example.asm.controller;

import com.example.asm.entity.Account;
import com.example.asm.sevice.AccountService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    private ServletContext app;

    @GetMapping("/view-add")
    public String showAddaccountResponse(Model model, HttpSession session) {
        Account login = (Account) session.getAttribute("account");
        if (!login.isAdmin() || !login.isActive()) {
            session.setAttribute("login", "Fail");
            return "redirect:/home/view";
        } else {
            session.setAttribute("login", "done");
        }
        model.addAttribute("account", new Account());
        Sort sort = Sort.by(Sort.Direction.ASC, "fullname");
        Pageable pageable = PageRequest.of(0, 4, sort);
        Page<Account> productPage = accountService.getAll(pageable);
        System.out.println(productPage);
        model.addAttribute("accounts", productPage);
        int totalPages = productPage.getTotalPages();
        List<Integer> listPage = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            listPage.add(i);
        }
        model.addAttribute("pageNumber", listPage);
        model.addAttribute("main", "accountView");
        return "main";
    }

    @GetMapping("/getPage/{page}")
    public String totalPage(@PathVariable("page") int page, Model model) throws IOException {
        model.addAttribute("account", new Account());
        Sort sort = Sort.by(Sort.Direction.ASC, "fullname");
        Pageable pageable = PageRequest.of(page, 2, sort);
        Page<Account> productPage = accountService.getAll(pageable);
        model.addAttribute("accounts", productPage);
        int totalPages = productPage.getTotalPages();
        List<Integer> listPage = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            listPage.add(i);
        }
        model.addAttribute("pageNumber", listPage);
        model.addAttribute("main", "accountView");
        return "main";
    }

    @PostMapping("/add")
    public String handleAddAccount(@Valid @ModelAttribute("account") Account account, BindingResult bindingResult, @RequestParam("image") MultipartFile image, HttpSession session, Model model) {
        System.out.println(image.getOriginalFilename());
        if (image.isEmpty()) {
            bindingResult.rejectValue("photo", "photo.notBlank", "Vui lòng chọn ảnh");
        }
        if (!bindingResult.hasErrors()) {
            System.out.println(image.getOriginalFilename());
            account.setPhoto(image.getOriginalFilename());
            Account account1 = accountService.saveorUpdate(account);
            if (account1 != null) {
                try {
                    File saveFile = new ClassPathResource("static/AccountImg").getFile();
                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + image.getOriginalFilename());
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                session.setAttribute("msg", "Image Upload Sucessfully");
            }
            return "redirect:/accounts/view-add";
        }
        Sort sort = Sort.by(Sort.Direction.ASC, "fullname");
        Pageable pageable = PageRequest.of(0, 2, sort);
        Page<Account> productPage = accountService.getAll(pageable);
        model.addAttribute("accounts", productPage);
        int totalPages = productPage.getTotalPages();
        List<Integer> listPage = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            listPage.add(i);
        }
        model.addAttribute("pageNumber", listPage);
        model.addAttribute("main", "accountView");
        return "main";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") String usernameUpdate, Model model, HttpSession session) {
        
        System.out.println("test1");
        Sort sort = Sort.by(Sort.Direction.ASC, "fullname");
        Pageable pageable = PageRequest.of(0, 2, sort);
        Page<Account> productPage = accountService.getAll(pageable);
        for (Account accounts : productPage) {
            if (accounts.getUsername().equals(usernameUpdate)) {
                model.addAttribute("account", accounts);
            }
        }
        System.out.println(productPage);
        model.addAttribute("accounts", productPage);
        int totalPages = productPage.getTotalPages();
        List<Integer> listPage = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            listPage.add(i);
        }
        model.addAttribute("pageNumber", listPage);
        model.addAttribute("main", "accountView");
        return "main";
    }
}


