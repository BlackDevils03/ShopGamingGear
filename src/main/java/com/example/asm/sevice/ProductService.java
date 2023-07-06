package com.example.asm.sevice;

import com.example.asm.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public interface ProductService {
    Product save(Product product);
    void delete(Product product);
    List<Product> getAll();
    List<Product> getAll(Sort sort);
    Page<Product> getAll(Pageable pageable);
    Page<Product> getAllAvalible(Boolean avalible,Pageable pageable);
    File saveFile(MultipartFile file, String path);
    Product findOne(int id);

}
