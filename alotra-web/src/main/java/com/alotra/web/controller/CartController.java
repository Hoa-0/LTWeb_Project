package com.alotra.web.controller;

import com.alotra.web.dto.CartItem;
import com.alotra.web.entity.Product;
import com.alotra.web.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity, HttpSession session) {
        // Lấy giỏ hàng từ session. Nếu chưa có, tạo mới.
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại: " + productId));

        // Nếu sản phẩm đã có trong giỏ, cập nhật số lượng
        if (cart.containsKey(productId)) {
            CartItem item = cart.get(productId);
            item.setQuantity(item.getQuantity() + quantity);
        } else { // Nếu chưa có, thêm mới
            CartItem item = new CartItem(productId, product.getName(), product.getPrice(), quantity);
            cart.put(productId, item);
        }

        // Lưu lại giỏ hàng vào session
        session.setAttribute("cart", cart);

        // Chuyển hướng về trang chủ hoặc trang sản phẩm
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        
        model.addAttribute("cartItems", cart.values());
        
        // Tính tổng tiền
        double totalAmount = cart.values().stream()
                                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                                    .sum();
        model.addAttribute("totalAmount", totalAmount);

        return "cart"; // Trả về file cart.html
    }
}