package com.example.asm.repository;

import com.example.asm.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    Page<Product> findAllByAvailable(Boolean Boolean, Pageable pageable);
}
