package com.alotra.web.service;

import com.alotra.web.entity.Product;
import com.alotra.web.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collections;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllActiveProducts() {
        try {
            // Sau này sẽ thêm điều kiện isActive = true
            return productRepository.findAll();
        } catch (Exception e) {
            // Nếu có lỗi database, trả về danh sách rỗng
            System.err.println("Error fetching products: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}