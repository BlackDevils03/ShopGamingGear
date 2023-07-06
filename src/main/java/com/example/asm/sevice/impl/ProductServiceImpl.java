package com.example.asm.sevice.impl;

import com.example.asm.entity.Product;
import com.example.asm.repository.ProductDAO;
import com.example.asm.sevice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDAO;

    @Override
    public Product save(Product product) {
        return productDAO.saveAndFlush(product);
    }

    @Override
    public void delete(Product product) {
        productDAO.delete(product);
    }

    @Override
    public List<Product> getAll() {
        return productDAO.findAll();
    }

    @Override
    public List<Product> getAll(Sort sort) {
        return productDAO.findAll(sort);
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return productDAO.findAll(pageable);
    }

    @Override
    public Page<Product> getAllAvalible(Boolean avalible, Pageable pageable) {
        return productDAO.findAllByAvailable(avalible, pageable);
    }

    @Override
    public File saveFile(MultipartFile file, String path) {
        if (!file.isEmpty()) {
            try {
                Path directoryPath = Paths.get(path);
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }
                String fileName = file.getOriginalFilename();
                String filePath = path + File.separator + fileName;
                File savedFile = new File(filePath);
                Files.copy(file.getInputStream(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return savedFile;
            } catch (IOException e) {
                throw new RuntimeException("Error saving file");
            }
        }
        return null;
    }

    @Override
    public Product findOne(int id) {
        return productDAO.getOne(id);
    }
}
