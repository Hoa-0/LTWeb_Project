package com.alotra.web.controller;

import com.alotra.web.dto.NhanVienLoginRequest;
import com.alotra.web.entity.NhanVien;
import com.alotra.web.service.NhanVienService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/NhanVien")
@RequiredArgsConstructor
@Slf4j
public class NhanVienController {
    
    private final NhanVienService nhanVienService;
    
    /**
     * Hiển thị trang đăng nhập cho nhân viên
     */
    @GetMapping("/login")
    public String showLoginPage(Model model, HttpSession session) {
        // Kiểm tra nếu đã đăng nhập rồi thì chuyển hướng đến dashboard
        if (session.getAttribute("loggedInNhanVien") != null) {
            return "redirect:/NhanVien/home";
        }
        
        model.addAttribute("loginRequest", new NhanVienLoginRequest());
        return "nhanvien/login";
    }
    
    /**
     * Xử lý đăng nhập nhân viên
     */
    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("loginRequest") NhanVienLoginRequest loginRequest,
                               BindingResult bindingResult,
                               HttpSession session,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        
        log.info("Processing login for: {}", loginRequest.getUsernameOrEmail());
        
        // Kiểm tra validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginRequest", loginRequest);
            return "nhanvien/login";
        }
        
        try {
            // Xác thực thông tin đăng nhập
            Optional<NhanVien> authenticatedNhanVien = nhanVienService.authenticateNhanVien(
                loginRequest.getUsernameOrEmail(), 
                loginRequest.getPassword()
            );
            
            if (authenticatedNhanVien.isPresent()) {
                NhanVien nhanVien = authenticatedNhanVien.get();
                
                // Lưu thông tin nhân viên vào session
                session.setAttribute("loggedInNhanVien", nhanVien);
                session.setAttribute("nhanVienId", nhanVien.getMaNV());
                session.setAttribute("nhanVienName", nhanVien.getTenNV());
                session.setAttribute("nhanVienRole", nhanVien.getVaiTro());
                session.setAttribute("nhanVienUsername", nhanVien.getUsername());
                
                log.info("Login successful for employee: {} - {}", nhanVien.getUsername(), nhanVien.getTenNV());
                
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Đăng nhập thành công! Xin chào " + nhanVien.getTenNV());
                
                return "redirect:/NhanVien/home";
                
            } else {
                log.warn("Login failed for: {}", loginRequest.getUsernameOrEmail());
                model.addAttribute("errorMessage", "Tên đăng nhập/Email hoặc mật khẩu không chính xác");
                model.addAttribute("loginRequest", loginRequest);
                return "nhanvien/login";
            }
            
        } catch (Exception e) {
            log.error("Error during login process", e);
            model.addAttribute("errorMessage", "Có lỗi xảy ra trong quá trình đăng nhập. Vui lòng thử lại.");
            model.addAttribute("loginRequest", loginRequest);
            return "nhanvien/login";
        }
    }
    
    /**
     * Hiển thị dashboard cho nhân viên
     */
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        NhanVien loggedInNhanVien = (NhanVien) session.getAttribute("loggedInNhanVien");
        
        if (loggedInNhanVien == null) {
            return "redirect:/NhanVien/login";
        }
        
        model.addAttribute("nhanVien", loggedInNhanVien);
        model.addAttribute("pageTitle", "Dashboard - Quản lý Nhân viên");
        return "nhanvien/dashboard";
    }
    
    /**
     * Đăng xuất
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("nhanVienUsername");
        
        // Xóa tất cả thông tin trong session
        session.invalidate();
        
        log.info("Employee logged out: {}", username);
        redirectAttributes.addFlashAttribute("successMessage", "Đăng xuất thành công!");
        
        return "redirect:/NhanVien/login";
    }
    
    /**
     * API để kiểm tra trạng thái đăng nhập (cho AJAX calls)
     */
    @GetMapping("/check-session")
    @ResponseBody
    public boolean checkSession(HttpSession session) {
        return session.getAttribute("loggedInNhanVien") != null;
    }
}