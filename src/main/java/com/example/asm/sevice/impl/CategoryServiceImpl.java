package com.example.asm.sevice.impl;

import com.example.asm.entity.Category;
import com.example.asm.repository.CategoryDAO;
import com.example.asm.sevice.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public List<Category> getAll() {
        return categoryDAO.findAll();
    }

    @Override
    public void saveOrUpdate(Category category) {
        categoryDAO.save(category);
    }

    @Override
    public Category findOne(String id) {
        return categoryDAO.getOne(id);
    }
}
