package com.alotra.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller xử lý quản lý khuyến mãi cho nhân viên
 */
@Controller
@RequestMapping("/NhanVien/khuyenmai")
@Slf4j
public class KhuyenMaiController {

    @GetMapping("")
    public String listKhuyenMai(Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        // TODO: Implement promotion listing logic
        log.info("Danh sách khuyến mãi accessed by: {}", 
                session.getAttribute("nhanVienUsername"));

        model.addAttribute("pageTitle", "Quản lý khuyến mãi");
        return "nhanvien/khuyenmai/list";
    }

    @GetMapping("/them")
    public String themKhuyenMai(Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        // TODO: Implement add promotion logic
        model.addAttribute("pageTitle", "Thêm khuyến mãi");
        return "nhanvien/khuyenmai/form";
    }

    @GetMapping("/sua/{id}")
    public String suaKhuyenMai(Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        // TODO: Implement edit promotion logic
        model.addAttribute("pageTitle", "Sửa khuyến mãi");
        return "nhanvien/khuyenmai/form";
    }

    @GetMapping("/xem/{id}")
    public String xemKhuyenMai(Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        // TODO: Implement view promotion logic
        model.addAttribute("pageTitle", "Chi tiết khuyến mãi");
        return "nhanvien/khuyenmai/detail";
    }
}