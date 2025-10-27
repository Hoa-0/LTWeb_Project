package com.alotra.web.controller.admin;

import com.alotra.web.dto.admin.PromotionAdminViewDTO;
import com.alotra.web.dto.admin.PromotionFormDTO;
import com.alotra.web.service.admin.AdminPromotionService;
import jakarta.validation.Valid; // <-- Import @Valid
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // <-- Import BindingResult
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/promotions")
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
public class AdminPromotionController {

    @Autowired
    private AdminPromotionService promotionService;

    // === HIỂN THỊ DANH SÁCH (LIST) ===
    @GetMapping
    public String listPromotions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        int currentPage = Math.max(page - 1, 0);
        Pageable pageable = PageRequest.of(currentPage, size, Sort.by("maKM").descending());
        Page<PromotionAdminViewDTO> promotionPage = promotionService.findAllPaginated(pageable);

        model.addAttribute("promotionPage", promotionPage);

        int totalPages = promotionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "admin/promotion-list"; // Trả về promotion-list.html
    }

    // === HIỂN THỊ FORM THÊM MỚI ===
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("promotionDTO", new PromotionFormDTO());
        model.addAttribute("formTitle", "Thêm mới Khuyến mãi");
        return "admin/promotion-form"; // Trả về promotion-form.html
    }

    // === XỬ LÝ SUBMIT FORM THÊM MỚI ===
    @PostMapping("/add")
    public String processAddForm(
            @Valid @ModelAttribute("promotionDTO") PromotionFormDTO dto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Thêm mới Khuyến mãi");
            return "admin/promotion-form"; // Lỗi: Trả về form
        }

        try {
            promotionService.save(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm mới khuyến mãi thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/promotions"; // Thành công: Về trang list
    }

    // === HIỂN THỊ FORM SỬA ===
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model) {
        try {
            PromotionFormDTO dto = promotionService.findDtoById(id);
            model.addAttribute("promotionDTO", dto);
            model.addAttribute("formTitle", "Cập nhật Khuyến mãi (ID: " + id + ")");
            return "admin/promotion-form"; // Trả về promotion-form.html
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không tìm thấy khuyến mãi.");
            return "redirect:/admin/promotions";
        }
    }

    // === XỬ LÝ SUBMIT FORM SỬA ===
    @PostMapping("/{id}/edit")
    public String processEditForm(
            @PathVariable Integer id,
            @Valid @ModelAttribute("promotionDTO") PromotionFormDTO dto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        dto.setMaKM(id); // Đảm bảo ID được set cho DTO

        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Cập nhật Khuyến mãi (ID: " + id + ")");
            return "admin/promotion-form"; // Lỗi: Trả về form
        }

        try {
            promotionService.save(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật khuyến mãi thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/promotions"; // Thành công: Về trang list
    }

    // === CÁC HÀNH ĐỘNG XÓA (Đã có) ===

    @PostMapping("/{id}/toggle-status")
    public String togglePromotionStatus(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            promotionService.toggleStatus(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đổi trạng thái thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/promotions";
    }

    @PostMapping("/{id}/delete")
    public String softDeletePromotion(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            promotionService.softDelete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa tạm thời thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/promotions";
    }

    @PostMapping("/{id}/restore")
    public String restorePromotion(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            promotionService.restore(id);
            redirectAttributes.addFlashAttribute("successMessage", "Khôi phục thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/promotions";
    }

    @PostMapping("/{id}/delete-permanently")
    public String deletePromotionPermanently(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            promotionService.deletePermanently(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa vĩnh viễn!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/promotions";
    }
}