package com.alotra.web.controller;

import com.alotra.web.dto.SanPhamDTO;
import com.alotra.web.entity.SanPham;
import com.alotra.web.repository.DanhMucSanPhamRepository;
import com.alotra.web.repository.ToppingRepository;
import com.alotra.web.repository.KhuyenMaiSanPhamRepository;
import com.alotra.web.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/NhanVien")
@RequiredArgsConstructor
@Slf4j
public class NhanVienHomeController {

    private final SanPhamService sanPhamService;
    private final DanhMucSanPhamRepository danhMucSanPhamRepository;
    private final ToppingRepository toppingRepository;
    private final KhuyenMaiSanPhamRepository khuyenMaiSanPhamRepository;

    /**
     * Trang home dành cho quầy (khách xem và chọn đồ uống). Không yêu cầu đăng nhập.
     */
    @GetMapping({"", "/", "/home"})
    public String showNhanVienHome(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "24") int size,
                                   @RequestParam(required = false) Integer maDM,
                                   Model model) {
        try {
            Pageable pageable = PageRequest.of(page, Math.min(Math.max(size, 6), 48));

            // Lấy danh sách sản phẩm đang bán (có thể lọc theo danh mục)
            Page<SanPham> spPage = (maDM != null)
                    ? sanPhamService.getActiveSanPhamByCategory(maDM, pageable)
                    : sanPhamService.getActiveSanPham(pageable);

            // Convert sang DTO để thuận tiện hiển thị size/giá
            List<SanPhamDTO> sanPhamDTOs = spPage.getContent()
                    .stream()
                    .map(sanPhamService::convertToDTO)
                    .collect(Collectors.toList());

            // Tính phần trăm khuyến mãi đang áp dụng cho từng sản phẩm (nếu có)
            java.time.LocalDate today = java.time.LocalDate.now();
            java.util.Map<Integer, Integer> discountPercents = new java.util.HashMap<>();
            for (SanPhamDTO sp : sanPhamDTOs) {
                try {
                    Integer p = khuyenMaiSanPhamRepository.findActiveDiscountPercentForProduct(sp.getMaSP(), today);
                    if (p != null && p > 0) {
                        discountPercents.put(sp.getMaSP(), p);
                    }
                } catch (Exception ex) {
                    // Không chặn trang nếu lỗi tính khuyến mãi; ghi log mức debug
                    log.debug("Cannot get discount percent for product {}: {}", sp.getMaSP(), ex.getMessage());
                }
            }

            // Danh mục để filter (UI, chưa thêm logic filter backend ở đây)
            var danhMucs = danhMucSanPhamRepository.findAllActive();
            var toppings = toppingRepository.findAllActive();

            model.addAttribute("pageTitle", "Menu tại quầy");
            model.addAttribute("products", sanPhamDTOs);
            model.addAttribute("categories", danhMucs);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", spPage.getTotalPages());
            model.addAttribute("selectedMaDM", maDM);
            model.addAttribute("toppings", toppings);
            model.addAttribute("discountPercents", discountPercents);

            return "nhanvien/home";
        } catch (Exception e) {
            log.error("Error rendering NhanVien home", e);
            model.addAttribute("errorMessage", "Không thể tải danh sách sản phẩm. Vui lòng thử lại.");
            return "nhanvien/home";
        }
    }
}
