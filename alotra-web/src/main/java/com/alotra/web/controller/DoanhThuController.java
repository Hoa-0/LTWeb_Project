package com.alotra.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller xử lý báo cáo doanh thu cho nhân viên
 */
@Controller
@RequestMapping("/NhanVien/doanhthu")
@Slf4j
public class DoanhThuController {

    @GetMapping("")
    public String baoCaoDoanhThu(Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        // TODO: Implement revenue report logic
        log.info("Báo cáo doanh thu accessed by: {}", 
                session.getAttribute("nhanVienUsername"));

        model.addAttribute("pageTitle", "Báo cáo doanh thu");
        return "nhanvien/doanhthu/report";
    }

    @GetMapping("/theo-ngay")
    public String doanhThuTheoNgay(Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        // TODO: Implement daily revenue logic
        model.addAttribute("pageTitle", "Doanh thu theo ngày");
        return "nhanvien/doanhthu/daily";
    }

    @GetMapping("/theo-thang")
    public String doanhThuTheoThang(Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        // TODO: Implement monthly revenue logic
        model.addAttribute("pageTitle", "Doanh thu theo tháng");
        return "nhanvien/doanhthu/monthly";
    }

    @GetMapping("/theo-san-pham")
    public String doanhThuTheoSanPham(Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        // TODO: Implement product revenue logic
        model.addAttribute("pageTitle", "Doanh thu theo sản phẩm");
        return "nhanvien/doanhthu/product";
    }
}