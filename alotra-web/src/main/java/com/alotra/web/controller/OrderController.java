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
import java.util.List; // Thêm import này
import com.alotra.web.entity.Order; // Thêm import này

import java.security.Principal;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Hiển thị trang checkout
    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart"; // Nếu giỏ hàng trống, quay về trang giỏ hàng
        }
        
        model.addAttribute("cartItems", cart.values());
        double totalAmount = cart.values().stream()
                                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                                    .sum();
        model.addAttribute("totalAmount", totalAmount);

        return "checkout";
    }

    // Xử lý đặt hàng
    @PostMapping("/checkout")
    public String placeOrder(@RequestParam("shippingAddress") String shippingAddress,
                             HttpSession session,
                             Principal principal) { // Principal giúp lấy thông tin người dùng đã đăng nhập

        // Lấy giỏ hàng từ session
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");

        // Lấy email của người dùng đang đăng nhập
        String userEmail = principal.getName();

        // Gọi service để tạo đơn hàng
        orderService.createOrder(cart, userEmail, shippingAddress);

        // Xóa giỏ hàng sau khi đã đặt hàng thành công
        session.removeAttribute("cart");

        return "redirect:/order-success"; // Chuyển hướng đến trang thông báo thành công
    }

    @GetMapping("/order-success")
    public String showOrderSuccessPage() {
        return "order-success";
    }
    @GetMapping("/my-orders")
    public String showOrderHistory(Model model, Principal principal) {
        // Lấy email người dùng đang đăng nhập
        String userEmail = principal.getName();
        
        // Lấy danh sách đơn hàng từ service
        List<Order> orders = orderService.findOrdersByUserEmail(userEmail);
        
        // Đưa danh sách vào model để hiển thị ra view
        model.addAttribute("orders", orders);
        
        return "my-orders"; // Trả về file my-orders.html
    }
}