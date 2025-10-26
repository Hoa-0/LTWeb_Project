package com.alotra.web.controller;

import com.alotra.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String showHomePage(Model model) {
        // Lấy danh sách sản phẩm từ service
        var products = productService.getAllActiveProducts();
        // Đưa danh sách đó vào model với tên là "products"
        model.addAttribute("products", products);
        // Trả về file 'index.html' trong thư mục templates
        return "index";
    }
}