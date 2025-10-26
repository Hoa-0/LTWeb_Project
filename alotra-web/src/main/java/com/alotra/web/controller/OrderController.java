package com.alotra.web.controller;

import com.alotra.web.dto.CartItem;
import com.alotra.web.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.alotra.web.entity.Order;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @SuppressWarnings("unchecked")
    private Map<Long, CartItem> getCartFromSession(HttpSession session) {
        Object attr = session.getAttribute("cart");
        if (attr instanceof Map<?, ?>) {
            try {
                return (Map<Long, CartItem>) attr;
            } catch (ClassCastException ex) {
                // fall through
            }
        }
        Map<Long, CartItem> newCart = new HashMap<>();
        session.setAttribute("cart", newCart);
        return newCart;
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model) {
        Map<Long, CartItem> cart = getCartFromSession(session);
        if (cart.isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("cartItems", cart.values());
        double totalAmount = cart.values().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("totalAmount", totalAmount);

        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@RequestParam("shippingAddress") String shippingAddress,
                             HttpSession session,
                             Principal principal) {

        Map<Long, CartItem> cart = getCartFromSession(session);
        String userEmail = principal.getName();

        orderService.createOrder(cart, userEmail, shippingAddress);

        session.removeAttribute("cart");

        return "redirect:/order-success";
    }

    @GetMapping("/order-success")
    public String showOrderSuccessPage() {
        return "order-success";
    }

    @GetMapping("/my-orders")
    public String showOrderHistory(Model model, Principal principal) {
        String userEmail = principal.getName();
        List<Order> orders = orderService.findOrdersByUserEmail(userEmail);
        model.addAttribute("orders", orders);
        return "my-orders";
    }
}