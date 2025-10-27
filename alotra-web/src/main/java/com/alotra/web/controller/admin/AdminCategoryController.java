package com.alotra.web.controller.admin;

import com.alotra.web.dto.admin.CategoryAdminViewDTO;
import com.alotra.web.entity.DanhMucSanPham;
import com.alotra.web.service.admin.AdminCategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(AdminCategoryController.class);

    @Autowired
    private AdminCategoryService adminCategoryService;

    // Hiển thị danh sách
    @GetMapping
    public String listCategories(Model model) {
        List<CategoryAdminViewDTO> categories = adminCategoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin/category-list";
    }

    // Hiển thị form thêm mới
    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("danhMuc", new DanhMucSanPham());
        model.addAttribute("pageTitle", "Thêm Danh mục mới");
        return "admin/category-form";
    }

    // Hiển thị form sửa
    @GetMapping("/{maDM}/edit")
    public String showEditCategoryForm(@PathVariable Integer maDM, Model model, RedirectAttributes redirectAttributes) {
        Optional<DanhMucSanPham> categoryOpt = adminCategoryService.getCategoryById(maDM);
        if (categoryOpt.isPresent()) {
            model.addAttribute("danhMuc", categoryOpt.get());
            model.addAttribute("pageTitle", "Sửa Danh mục");
            return "admin/category-form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy danh mục với Mã: " + maDM);
            return "redirect:/admin/categories";
        }
    }

    // Xử lý submit form thêm/sửa
    @PostMapping("/save")
    public String saveCategory(@Valid @ModelAttribute("danhMuc") DanhMucSanPham danhMuc,
                               BindingResult bindingResult, // Bắt lỗi validation nếu có @Valid trong Entity
                               RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", danhMuc.getMaDM() == null ? "Thêm Danh mục mới" : "Sửa Danh mục");
            return "admin/category-form";
        }

        try {
            boolean isNew = danhMuc.getMaDM() == null;
            adminCategoryService.saveCategory(danhMuc);
            redirectAttributes.addFlashAttribute("successMessage", (isNew ? "Thêm" : "Cập nhật") + " danh mục '" + danhMuc.getTenDM() + "' thành công.");
            return "redirect:/admin/categories";
        } catch (Exception e) { // Bắt lỗi (vd: trùng tên)
            logger.error("Error saving category: {}", e.getMessage());
            model.addAttribute("errorMessage", "Lỗi khi lưu danh mục: " + e.getMessage());
            model.addAttribute("pageTitle", danhMuc.getMaDM() == null ? "Thêm Danh mục mới" : "Sửa Danh mục");
            return "admin/category-form";
        }
    }

    // Xử lý xóa mềm
    @PostMapping("/{maDM}/delete")
    public String deleteCategory(@PathVariable Integer maDM, RedirectAttributes redirectAttributes) {
        try {
            adminCategoryService.softDeleteCategory(maDM);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa tạm thời danh mục (Mã: " + maDM + ").");
        } catch (EntityNotFoundException | IllegalStateException e) { // Bắt cả lỗi không xóa được
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            logger.error("Error soft deleting category: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi xóa danh mục.");
        }
        return "redirect:/admin/categories";
    }

    // Xử lý khôi phục
    @PostMapping("/{maDM}/restore")
    public String restoreCategory(@PathVariable Integer maDM, RedirectAttributes redirectAttributes) {
        try {
            DanhMucSanPham restoredCategory = adminCategoryService.restoreCategory(maDM);
            redirectAttributes.addFlashAttribute("successMessage", "Đã khôi phục danh mục '" + restoredCategory.getTenDM() + "'.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            logger.error("Error restoring category: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi khôi phục danh mục.");
        }
        return "redirect:/admin/categories";
    }

    // Xử lý xóa vĩnh viễn
    /**
     * Xóa vĩnh viễn (hard delete) một danh mục.
     * Cần đảm bảo `AdminCategoryService` có phương thức `deleteCategoryPermanently(Integer maDM)`.
     * Xử lý lỗi nếu danh mục không tồn tại hoặc có ràng buộc khóa ngoại (ví dụ: đang có sản phẩm thuộc danh mục này).
     * @param maDM Mã danh mục cần xóa.
     * @param redirectAttributes Dùng để truyền thông báo thành công/thất bại về trang danh sách.
     * @return Chuyển hướng về trang danh sách.
     */
    @PostMapping("/{maDM}/delete-permanently")
    public String deleteCategoryPermanently(@PathVariable Integer maDM, RedirectAttributes redirectAttributes) {
        try {
            adminCategoryService.deleteCategoryPermanently(maDM);
            redirectAttributes.addFlashAttribute("successMessage", "Đã **xóa vĩnh viễn** danh mục (Mã: " + maDM + ").");
        } catch (EntityNotFoundException | IllegalStateException e) {
            // Bắt lỗi không tìm thấy hoặc trạng thái không hợp lệ (nếu Service có kiểm tra)
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) { // Bắt lỗi ràng buộc khóa ngoại (ConstraintViolationException) hoặc lỗi khác
           logger.error("Error permanently deleting category: {}", e.getMessage());
           redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa vĩnh viễn danh mục: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }
}