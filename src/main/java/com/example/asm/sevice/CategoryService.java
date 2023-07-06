package com.example.asm.sevice;

import com.example.asm.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getAll();
    void saveOrUpdate(Category category);

    Category findOne(String id);
}
