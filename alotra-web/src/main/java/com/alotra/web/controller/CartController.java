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

@Controller("cartControllerMain")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    // Helper: Lấy giỏ hàng từ session (hạn chế phạm vi @SuppressWarnings)
    @SuppressWarnings("unchecked")
    private Map<Long, CartItem> getCartFromSession(HttpSession session) {
        Object attr = session.getAttribute("cart");
        if (attr instanceof Map<?, ?>) {
            try {
                return (Map<Long, CartItem>) attr;
            } catch (ClassCastException ex) {
                // Nếu kiểu không khớp, tạo mới để tránh ClassCastException
            }
        }
        Map<Long, CartItem> newCart = new HashMap<>();
        session.setAttribute("cart", newCart);
        return newCart;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session) {

        Map<Long, CartItem> cart = getCartFromSession(session);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại: " + productId));

        cart.compute(productId, (id, existing) -> {
            if (existing == null) {
                return new CartItem(productId, product.getName(), product.getPrice(), quantity);
            } else {
                existing.setQuantity(existing.getQuantity() + quantity);
                return existing;
            }
        });

        session.setAttribute("cart", cart);
        return "redirect:/";
    }

    @GetMapping("/cart-guest")
    public String showCart(HttpSession session, Model model) {
        Map<Long, CartItem> cart = getCartFromSession(session);

        model.addAttribute("cartItems", cart.values());

        double totalAmount = cart.values().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("totalAmount", totalAmount);

        return "cart";
    }
}