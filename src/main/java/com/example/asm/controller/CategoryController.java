package com.example.asm.controller;

import com.example.asm.entity.Category;
import com.example.asm.sevice.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/view-add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.saveOrUpdate(category);
        return "redirect:/categories/view-add";
    }

}
