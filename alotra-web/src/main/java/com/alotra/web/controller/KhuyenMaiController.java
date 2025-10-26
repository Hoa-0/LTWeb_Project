package com.alotra.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import com.alotra.web.entity.SuKienKhuyenMai;
import com.alotra.web.entity.KhuyenMaiSanPham;
import com.alotra.web.service.KhuyenMaiService;
import com.alotra.web.repository.SanPhamRepository;
import com.alotra.web.entity.SanPham;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller xử lý quản lý khuyến mãi cho nhân viên
 */
@Controller
@RequestMapping("/NhanVien/khuyenmai")
@Slf4j
public class KhuyenMaiController {
    @Autowired
    private KhuyenMaiService khuyenMaiService;
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @GetMapping("")
    public String listKhuyenMai(
            Model model,
            HttpSession session,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<SuKienKhuyenMai> pageData = khuyenMaiService.listKhuyenMai(pageable);
        long total = khuyenMaiService.countAll();
        long active = khuyenMaiService.countActive();
        long expired = khuyenMaiService.countExpired();
        long expiringSoon = khuyenMaiService.countExpiringSoon(7);

        log.info("Danh sách khuyến mãi accessed by: {}", session.getAttribute("nhanVienUsername"));

        model.addAttribute("pageTitle", "Quản lý khuyến mãi");
        model.addAttribute("promotions", pageData.getContent());
        model.addAttribute("pageData", pageData);
        model.addAttribute("total", total);
        model.addAttribute("active", active);
        model.addAttribute("expired", expired);
        model.addAttribute("expiringSoon", expiringSoon);
        return "nhanvien/khuyenmai/list";
    }

    @GetMapping("/them")
    public String themKhuyenMai(Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        model.addAttribute("pageTitle", "Thêm khuyến mãi");
        model.addAttribute("suKien", new SuKienKhuyenMai());
        // Danh sách sản phẩm để chọn áp dụng
        List<SanPham> allProducts = sanPhamRepository.findAllActive();
        model.addAttribute("allProducts", allProducts);
        model.addAttribute("assignedMap", new HashMap<Integer, Integer>());
        return "nhanvien/khuyenmai/form";
    }

    @GetMapping("/sua/{id}")
    public String suaKhuyenMai(@PathVariable("id") Integer id, Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

    SuKienKhuyenMai suKien = khuyenMaiService.getById(id)
                .orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));
    model.addAttribute("suKien", suKien);
    // Danh sách sản phẩm và cấu hình hiện tại
    List<SanPham> allProducts = sanPhamRepository.findAllActive();
    model.addAttribute("allProducts", allProducts);
    List<KhuyenMaiSanPham> ds = khuyenMaiService.getProductsByEvent(id);
    Map<Integer, Integer> assignedMap = new HashMap<>();
    for (KhuyenMaiSanPham k : ds) assignedMap.put(k.getMaSP(), k.getPhanTramGiam());
    model.addAttribute("assignedMap", assignedMap);
        model.addAttribute("pageTitle", "Sửa khuyến mãi");
        return "nhanvien/khuyenmai/form";
    }

    @GetMapping("/xem/{id}")
    public String xemKhuyenMai(@PathVariable("id") Integer id, Model model, HttpSession session) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        SuKienKhuyenMai suKien = khuyenMaiService.getById(id)
                .orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));
        List<KhuyenMaiSanPham> dsSanPham = khuyenMaiService.getProductsByEvent(id);
        model.addAttribute("suKien", suKien);
        model.addAttribute("dsSanPham", dsSanPham);
        model.addAttribute("pageTitle", "Chi tiết khuyến mãi");
        return "nhanvien/khuyenmai/detail";
    }

    @PostMapping("/xoa/{id}")
    public String xoaKhuyenMai(@PathVariable("id") Integer id, HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }
        khuyenMaiService.softDelete(id);
        return "redirect:/NhanVien/khuyenmai";
    }

    @PostMapping("/luu")
    public String luuKhuyenMai(@ModelAttribute("suKien") SuKienKhuyenMai suKien,
                               @RequestParam(value = "prodIds", required = false) List<Integer> prodIds,
                               @RequestParam(value = "prodPercents", required = false) List<Integer> prodPercents,
                               HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }
        SuKienKhuyenMai saved = khuyenMaiService.save(suKien);
        // Nếu có chọn sản phẩm thì lưu cấu hình
        if (prodIds != null && prodPercents != null && prodIds.size() == prodPercents.size()) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < prodIds.size(); i++) {
                map.put(prodIds.get(i), prodPercents.get(i));
            }
            khuyenMaiService.replaceProductsForEvent(saved.getMaKM(), map);
        }
        return "redirect:/NhanVien/khuyenmai/xem/" + saved.getMaKM();
    }

    @PostMapping("/capnhat/{id}")
    public String capNhatKhuyenMai(@PathVariable("id") Integer id,
                                   @ModelAttribute("suKien") SuKienKhuyenMai suKien,
                                   @RequestParam(value = "prodIds", required = false) List<Integer> prodIds,
                                   @RequestParam(value = "prodPercents", required = false) List<Integer> prodPercents,
                                   HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }
        khuyenMaiService.update(id, suKien);
        if (prodIds != null && prodPercents != null && prodIds.size() == prodPercents.size()) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < prodIds.size(); i++) {
                map.put(prodIds.get(i), prodPercents.get(i));
            }
            khuyenMaiService.replaceProductsForEvent(id, map);
        }
        return "redirect:/NhanVien/khuyenmai/xem/" + id;
    }
}