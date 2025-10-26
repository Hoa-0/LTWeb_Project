package com.alotra.web.controller;

import com.alotra.web.entity.User;
import com.alotra.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class VendorController {

    @Autowired
    private UserRepository userRepository;

    // Hiển thị form đăng ký shop
    @GetMapping("/vendor/register")
    public String showVendorRegistrationForm(Model model, Principal principal) {
        // Lấy thông tin user hiện tại và đưa vào model
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user"));
        model.addAttribute("user", user);
        return "vendor-register";
    }

    // Xử lý yêu cầu đăng ký shop
    @PostMapping("/vendor/register")
    public String processVendorRegistration(@ModelAttribute User userForm, Principal principal, RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user"));
        
        // Cập nhật thông tin shop và đổi vai trò
        currentUser.setShopName(userForm.getShopName());
        currentUser.setShopAddress(userForm.getShopAddress());
        currentUser.setRole("ROLE_VENDOR"); // Nâng cấp vai trò thành VENDOR
        // isShopActive vẫn là false, chờ Admin duyệt
        
        userRepository.save(currentUser);

        redirectAttributes.addFlashAttribute("successMessage", "Đăng ký bán hàng thành công! Vui lòng chờ quản trị viên phê duyệt.");
        return "redirect:/"; // Chuyển về trang chủ
    }
}