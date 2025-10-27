package com.alotra.web.controller.admin;

import com.alotra.web.dto.admin.ProductAdminViewDTO;
import com.alotra.web.entity.DanhMucSanPham;
import com.alotra.web.entity.SanPham;
import com.alotra.web.repository.DanhMucSanPhamRepository; // Inject repo danh mục
import com.alotra.web.service.admin.AdminProductService;
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

import java.util.stream.Collectors; 
import java.util.List;
import java.util.Optional;

// ===== THÊM CÁC IMPORT PHÂN TRANG BỊ THIẾU HUY/Checkpoint7=====
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
// ==============================================


@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private static final Logger logger = LoggerFactory.getLogger(AdminProductController.class);

    @Autowired
    private AdminProductService adminProductService;

    @Autowired
    private DanhMucSanPhamRepository danhMucSanPhamRepository; // Inject để lấy danh mục

 // Hiển thị danh sách sản phẩm (ĐÃ THÊM PHÂN TRANG)
    @GetMapping
    public String listProducts(
            Model model,
            // Thêm RequestParam để nhận keyword từ URL (ví dụ: ?keyword=abc)
            @RequestParam(required = false) String keyword, 
            @PageableDefault(size = 10, sort = "maSP", direction = Sort.Direction.DESC) Pageable pageable) { 
        
        logger.info(">>> GET /admin/products - Keyword: {}, Page: {}, Size: {}", keyword, pageable.getPageNumber(), pageable.getPageSize());
        
        // Truyền keyword xuống Service
        Page<ProductAdminViewDTO> productsPage = adminProductService.getAllProducts(keyword, pageable);
        
        model.addAttribute("productsPage", productsPage); 
        model.addAttribute("products", productsPage.getContent()); 
        
        // Giữ lại keyword để hiển thị trong ô search và link phân trang
        model.addAttribute("keyword", keyword); 
        
        model.addAttribute("pageNumbers", getPageNumbers(productsPage.getTotalPages(), pageable.getPageNumber()));
        
        return "admin/product-list";
    }
    
    // Hiển thị form thêm mới sản phẩm
    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("sanPham", new SanPham()); // Tạo object rỗng cho form binding
        model.addAttribute("categories", getActiveCategories()); // Lấy danh mục active
        model.addAttribute("pageTitle", "Thêm Sản phẩm mới");
        return "admin/product-form";
    }

    // Hiển thị form sửa sản phẩm
    @GetMapping("/{maSP}/edit")
    public String showEditProductForm(@PathVariable Integer maSP, Model model, RedirectAttributes redirectAttributes) {
        Optional<SanPham> sanPhamOpt = adminProductService.getProductById(maSP);
        if (sanPhamOpt.isPresent()) {
            model.addAttribute("sanPham", sanPhamOpt.get());
            model.addAttribute("categories", getActiveCategories());
            model.addAttribute("pageTitle", "Sửa Sản phẩm");
            return "admin/product-form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm với Mã: " + maSP);
            return "redirect:/admin/products";
        }
    }

    // Xử lý submit form thêm/sửa sản phẩm
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("sanPham") SanPham sanPham,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, Model model) {

        // Nếu có lỗi validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", getActiveCategories());
            model.addAttribute("pageTitle", sanPham.getMaSP() == null ? "Thêm Sản phẩm mới" : "Sửa Sản phẩm");
            // Không cần add lại sanPham vì @ModelAttribute đã làm
            return "admin/product-form";
        }

        try {
            boolean isNew = sanPham.getMaSP() == null;
            
            // THÊM LOGIC KIỂM TRA ẢNH BỊ THIẾU KHI THÊM MỚI
            if (isNew && (sanPham.getUrlAnh() == null || sanPham.getUrlAnh().isEmpty())) {
                 model.addAttribute("categories", getActiveCategories());
                 model.addAttribute("pageTitle", "Thêm Sản phẩm mới");
                 model.addAttribute("errorMessage", "Sản phẩm mới phải có URL Ảnh.");
                 return "admin/product-form";
            }
            
            adminProductService.saveProduct(sanPham);
            redirectAttributes.addFlashAttribute("successMessage", (isNew ? "Thêm" : "Cập nhật") + " sản phẩm '" + sanPham.getTenSP() + "' thành công.");
            return "redirect:/admin/products";
        } catch (Exception e) { // Bắt lỗi chung (ví dụ trùng tên...)
            logger.error("Error saving product: {}", e.getMessage());
            model.addAttribute("errorMessage", "Lỗi khi lưu sản phẩm: " + e.getMessage());
            model.addAttribute("categories", getActiveCategories());
            model.addAttribute("pageTitle", sanPham.getMaSP() == null ? "Thêm Sản phẩm mới" : "Sửa Sản phẩm");
            return "admin/product-form"; // Quay lại form
        }
    }

    // Xử lý bật/tắt trạng thái
    @PostMapping("/{maSP}/toggle-status")
    public String toggleStatus(@PathVariable Integer maSP, RedirectAttributes redirectAttributes) {
        try {
            SanPham updatedSanPham = adminProductService.toggleProductStatus(maSP);
            String statusText = updatedSanPham.getTrangThai() == 1 ? "kích hoạt" : "vô hiệu hóa";
            redirectAttributes.addFlashAttribute("successMessage", "Đã " + statusText + " sản phẩm '" + updatedSanPham.getTenSP() + "'.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            logger.error("Error toggling product status: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi cập nhật trạng thái sản phẩm.");
        }
        return "redirect:/admin/products";
    }

     // Xử lý xóa mềm
     @PostMapping("/{maSP}/delete")
     public String deleteProduct(@PathVariable Integer maSP, RedirectAttributes redirectAttributes) {
         try {
             adminProductService.softDeleteProduct(maSP);
             redirectAttributes.addFlashAttribute("successMessage", "Đã xóa tạm thời sản phẩm (Mã: " + maSP + ").");
         } catch (EntityNotFoundException e) {
             redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
         } catch (Exception e) {
            logger.error("Error soft deleting product: {}", e.getMessage());
             redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi xóa sản phẩm.");
         }
         return "redirect:/admin/products";
     }

     // Xử lý khôi phục
     @PostMapping("/{maSP}/restore")
     public String restoreProduct(@PathVariable Integer maSP, RedirectAttributes redirectAttributes) {
         try {
             SanPham restoredSanPham = adminProductService.restoreProduct(maSP);
             redirectAttributes.addFlashAttribute("successMessage", "Đã khôi phục sản phẩm '" + restoredSanPham.getTenSP() + "'.");
         } catch (EntityNotFoundException e) {
             redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
         } catch (Exception e) {
            logger.error("Error restoring product: {}", e.getMessage());
             redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi khôi phục sản phẩm.");
         }
         return "redirect:/admin/products";
     }

     // Xử lý xóa vĩnh viễn
     @PostMapping("/{maSP}/delete-permanently")
     public String deleteProductPermanently(@PathVariable Integer maSP, RedirectAttributes redirectAttributes) {
         try {
             adminProductService.deleteProductPermanently(maSP);
             redirectAttributes.addFlashAttribute("successMessage", "Đã xóa vĩnh viễn sản phẩm (Mã: " + maSP + ").");
         } catch (EntityNotFoundException | IllegalStateException e) {
             redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
         } catch (Exception e) {
            logger.error("Error permanently deleting product: {}", e.getMessage());
             redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi xóa vĩnh viễn sản phẩm: " + e.getMessage()); // Hiển thị lỗi rõ hơn
         }
         return "redirect:/admin/products";
     }


    // Helper method để lấy danh sách danh mục active
    private List<DanhMucSanPham> getActiveCategories() {
        return danhMucSanPhamRepository.findByDeletedAtIsNullOrderByTenDMAsc();
    }
    
    // Helper method tạo danh sách số trang để hiển thị thanh phân trang
    private List<Integer> getPageNumbers(int totalPages, int currentPage) {
        if (totalPages > 0) {
            // Hiển thị tối đa 5 số trang gần trang hiện tại
            int start = Math.max(0, currentPage - 2);
            int end = Math.min(totalPages - 1, currentPage + 2);
            
            // Xử lý nếu ở đầu hoặc cuối danh sách
            if (end - start < 4) {
                start = Math.max(0, end - 4);
                end = Math.min(totalPages - 1, start + 4);
            }

            return java.util.stream.IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        }
        return List.of();
    }
}