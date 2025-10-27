package com.alotra.web.controller.admin;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminAuthController {

    @GetMapping("/login")
    public String showLoginForm(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout) {

        // Nếu đã đăng nhập thì chuyển thẳng vào dashboard
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
             // Kiểm tra xem có phải ROLE_ADMIN không trước khi redirect
             boolean isAdmin = authentication.getAuthorities().stream()
                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
             if (isAdmin) {
                 return "redirect:/admin/dashboard";
             }
             // Nếu đăng nhập nhưng không phải admin thì có thể xử lý khác, ví dụ logout hoặc báo lỗi
             // Hiện tại tạm cho về login lại
        }


        if (error != null) {
            model.addAttribute("errorMessage", "Thông tin đăng nhập không chính xác hoặc bạn không có quyền truy cập.");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "Bạn đã đăng xuất thành công.");
        }

        // Truyền DTO rỗng để Thymeleaf binding (nếu dùng th:object)
        // model.addAttribute("loginRequest", new LoginRequestDTO()); // Cần tạo LoginRequestDTO nếu muốn dùng

        return "admin/login"; // Trả về tên file view trong templates/admin/
    }

    // Không cần phương thức POST /admin/login vì Spring Security tự xử lý
}