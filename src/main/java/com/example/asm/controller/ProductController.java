package com.example.asm.controller;

import com.example.asm.entity.Account;
import com.example.asm.entity.Category;
import com.example.asm.entity.Product;
import com.example.asm.sevice.CategoryService;
import com.example.asm.sevice.ProductService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    ServletContext app;

//    @GetMapping("/add")
//    public String viewAd() {
//        return "loadFile";
//    }

    @GetMapping("/view-add")
    public String showAddProductForm(Model model, HttpSession session) {
        Account login = (Account) session.getAttribute("account");
        if (!login.isAdmin() || !login.isActive()) {
            session.setAttribute("login", "Fail");
            return "redirect:/home/view";
        } else {
            session.setAttribute("login", "done");
        }
        model.addAttribute("product", new Product());
        Sort sort = Sort.by(Sort.Direction.ASC, "price");
        Pageable pageable = PageRequest.of(0, 2, sort);
        Page<Product> productPage = productService.getAll(pageable);
        System.out.println(productPage);
        model.addAttribute("data", productPage);
        int totalPages = productPage.getTotalPages();
        List<Integer> listPage = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            listPage.add(i);
        }
        model.addAttribute("pageNumber", listPage);
        model.addAttribute("main", "Product");
        return "main";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") @DateTimeFormat(pattern = "yyyy-MM-dd") Product product
            , BindingResult bindingResult, Model model, @RequestParam("photo") MultipartFile image
            , HttpSession session) throws IOException {
        if (image.isEmpty()) {
            bindingResult.rejectValue("image", "image.notBlank", "Vui lòng chọn ảnh");
        }
        if (!bindingResult.hasErrors()) {
            System.out.println("Day là ở vị trí void add" + product);
            System.out.println(image.getOriginalFilename());
            product.setImage(image.getOriginalFilename());
            Product product1 = productService.save(product);
            if (product1 != null) {
                try {
                    System.out.println("Vào đến đây rồi");
                    File saveFile = new ClassPathResource("static/img").getFile();
                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + image.getOriginalFilename());
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                session.setAttribute("msg", "Image Upload Sucessfully");
                return "redirect:/products/view-add";
            }
        }
        System.out.println(bindingResult);
        Sort sort = Sort.by(Sort.Direction.ASC, "price");
        Pageable pageable = PageRequest.of(0, 2, sort);
        Page<Product> productPage = productService.getAll(pageable);
        model.addAttribute("data", productPage);
        int totalPages = productPage.getTotalPages();
        List<Integer> listPage = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            listPage.add(i);
        }
        model.addAttribute("pageNumber", listPage);
        model.addAttribute("main", "Product");
        return "main";
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.getAll();
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable("id") int id) throws IOException {
        model.addAttribute("product", productService.findOne(id));
        System.out.println(productService.findOne(id));
        Sort sort = Sort.by(Sort.Direction.ASC, "price");
        Pageable pageable = PageRequest.of(0, 2, sort);
        Page<Product> productPage = productService.getAll(pageable);
        model.addAttribute("data", productPage);
        int totalPages = productPage.getTotalPages();
        List<Integer> listPage = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            listPage.add(i);
        }
        model.addAttribute("pageNumber", listPage);
        model.addAttribute("main", "Product");
        return "main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) throws IOException {
        System.out.println(productService.findOne(id));
        Product product = productService.findOne(id);
        product.setAvailable(false);
        productService.save(product);
        return "redirect:/products/view-add";
    }

    @GetMapping("/getPage/{page}")
    public String totalPage(@PathVariable("page") int page, Model model) throws IOException {
        model.addAttribute("product", new Product());
        Sort sort = Sort.by(Sort.Direction.ASC, "price");
        Pageable pageable = PageRequest.of(page, 2, sort);
        Page<Product> productPage = productService.getAll(pageable);
        model.addAttribute("data", productPage);
        int totalPages = productPage.getTotalPages();
        List<Integer> listPage = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            listPage.add(i);
        }
        model.addAttribute("pageNumber", listPage);
        model.addAttribute("main", "Product");
        return "main";
    }
}
