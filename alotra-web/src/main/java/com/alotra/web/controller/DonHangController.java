package com.alotra.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller xử lý quản lý đơn hàng cho nhân viên
 */
@Controller
@RequestMapping("/NhanVien/donhang")
@Slf4j
public class DonHangController {

    @GetMapping("")
    public String listDonHang(Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        // TODO: Implement order listing logic
        log.info("Danh sách đơn hàng accessed by: {}", 
                session.getAttribute("nhanVienUsername"));

        model.addAttribute("pageTitle", "Quản lý đơn hàng");
        return "nhanvien/donhang/list";
    }

    @GetMapping("/chi-tiet/{id}")
    public String chiTietDonHang(Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        // TODO: Implement order detail logic
        model.addAttribute("pageTitle", "Chi tiết đơn hàng");
        return "nhanvien/donhang/detail";
    }

    @GetMapping("/cap-nhat-trang-thai/{id}")
    public String capNhatTrangThai(Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        // TODO: Implement status update logic
        return "redirect:/NhanVien/donhang";
    }
}