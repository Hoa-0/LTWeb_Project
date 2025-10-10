package com.alotra.web.service;

import com.alotra.web.entity.Product;
import com.alotra.web.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllActiveProducts() {
        // Sau này sẽ thêm điều kiện isActive = true
        return productRepository.findAll();
    }
}