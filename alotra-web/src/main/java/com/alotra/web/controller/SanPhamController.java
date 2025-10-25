package com.alotra.web.controller;

import com.alotra.web.dto.SanPhamDTO;
import com.alotra.web.entity.DanhMucSanPham;
import com.alotra.web.entity.SanPham;
import com.alotra.web.entity.SizeSanPham;
import com.alotra.web.repository.DanhMucSanPhamRepository;
import com.alotra.web.repository.SizeSanPhamRepository;
import com.alotra.web.service.SanPhamService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/NhanVien/sanpham")
@RequiredArgsConstructor
@Slf4j
public class SanPhamController {
    
    private final SanPhamService sanPhamService;
    private final DanhMucSanPhamRepository danhMucRepository;
    private final SizeSanPhamRepository sizeRepository;
    
    /**
     * Danh sách sản phẩm với phân trang và tìm kiếm
     */
    @GetMapping
    public String listSanPham(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "maSP") String sortBy,
                              @RequestParam(defaultValue = "desc") String sortDir,
                              @RequestParam(required = false) String keyword,
                              @RequestParam(required = false) Integer maDM,
                              HttpSession session,
                              Model model) {
        
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }
        
        // Tạo Pageable
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                   Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // Tìm kiếm sản phẩm
        Page<SanPham> sanPhamPage = sanPhamService.searchSanPham(keyword, maDM, pageable);
        
        // Lấy danh sách danh mục cho filter
        List<DanhMucSanPham> danhMucs = danhMucRepository.findAllActive();
        
        // Tính thống kê
        long totalSanPham = sanPhamService.getTotalSanPham();
        long activeSanPham = sanPhamService.getTotalActiveSanPham();
        
        // Thêm vào model
        model.addAttribute("sanPhamPage", sanPhamPage);
        model.addAttribute("danhMucs", danhMucs);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedMaDM", maDM);
        model.addAttribute("totalSanPham", totalSanPham);
        model.addAttribute("activeSanPham", activeSanPham);
        
        // Breadcrumb
        model.addAttribute("pageTitle", "Quản lý Sản phẩm");
        
        return "nhanvien/sanpham/list";
    }
    
    /**
     * Hiển thị form thêm sản phẩm
     */
    @GetMapping("/them")
    public String showAddForm(HttpSession session, Model model) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }
        
        // Tạo DTO mới
        SanPhamDTO sanPhamDTO = new SanPhamDTO();
        
        // Lấy danh sách danh mục và size
        List<DanhMucSanPham> danhMucs = danhMucRepository.findAllActive();
        List<SizeSanPham> sizes = sizeRepository.findAllActive();
        
        model.addAttribute("sanPhamDTO", sanPhamDTO);
        model.addAttribute("danhMucs", danhMucs);
        model.addAttribute("sizes", sizes);
        model.addAttribute("pageTitle", "Thêm Sản phẩm mới");
        model.addAttribute("isEdit", false);
        
        return "nhanvien/sanpham/form";
    }
    
    /**
     * Xử lý thêm sản phẩm
     */
    @PostMapping("/them")
    public String addSanPham(@Valid @ModelAttribute("sanPhamDTO") SanPhamDTO sanPhamDTO,
                             BindingResult bindingResult,
                             HttpSession session,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }
        
        // Kiểm tra validation
        if (bindingResult.hasErrors()) {
            List<DanhMucSanPham> danhMucs = danhMucRepository.findAllActive();
            List<SizeSanPham> sizes = sizeRepository.findAllActive();
            
            model.addAttribute("danhMucs", danhMucs);
            model.addAttribute("sizes", sizes);
            model.addAttribute("pageTitle", "Thêm Sản phẩm mới");
            model.addAttribute("isEdit", false);
            
            return "nhanvien/sanpham/form";
        }
        
        try {
            SanPham savedSanPham = sanPhamService.saveSanPham(sanPhamDTO);
            
            log.info("Product added successfully by employee {}: {}", 
                    session.getAttribute("nhanVienUsername"), savedSanPham.getMaSP());
            
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Thêm sản phẩm '" + savedSanPham.getTenSP() + "' thành công!");
            
            return "redirect:/NhanVien/sanpham";
            
        } catch (Exception e) {
            log.error("Error adding product", e);
            
            List<DanhMucSanPham> danhMucs = danhMucRepository.findAllActive();
            List<SizeSanPham> sizes = sizeRepository.findAllActive();
            
            model.addAttribute("danhMucs", danhMucs);
            model.addAttribute("sizes", sizes);
            model.addAttribute("pageTitle", "Thêm Sản phẩm mới");
            model.addAttribute("isEdit", false);
            model.addAttribute("errorMessage", "Có lỗi khi thêm sản phẩm: " + e.getMessage());
            
            return "nhanvien/sanpham/form";
        }
    }
    
    /**
     * Hiển thị form sửa sản phẩm
     */
    @GetMapping("/sua/{maSP}")
    public String showEditForm(@PathVariable Integer maSP,
                               HttpSession session,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }
        
        // Lấy sản phẩm
        SanPhamDTO sanPhamDTO = sanPhamService.getSanPhamDTOById(maSP);
        if (sanPhamDTO == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm!");
            return "redirect:/NhanVien/sanpham";
        }
        
        // Lấy danh sách danh mục và size
        List<DanhMucSanPham> danhMucs = danhMucRepository.findAllActive();
        List<SizeSanPham> sizes = sizeRepository.findAllActive();
        
        model.addAttribute("sanPhamDTO", sanPhamDTO);
        model.addAttribute("danhMucs", danhMucs);
        model.addAttribute("sizes", sizes);
        model.addAttribute("pageTitle", "Sửa Sản phẩm: " + sanPhamDTO.getTenSP());
        model.addAttribute("isEdit", true);
        
        return "nhanvien/sanpham/form";
    }
    
    /**
     * Xử lý sửa sản phẩm
     */
    @PostMapping("/sua/{maSP}")
    public String updateSanPham(@PathVariable Integer maSP,
                                @Valid @ModelAttribute("sanPhamDTO") SanPhamDTO sanPhamDTO,
                                BindingResult bindingResult,
                                HttpSession session,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }
        
        // Set maSP cho DTO
        sanPhamDTO.setMaSP(maSP);
        
        // Kiểm tra validation
        if (bindingResult.hasErrors()) {
            List<DanhMucSanPham> danhMucs = danhMucRepository.findAllActive();
            List<SizeSanPham> sizes = sizeRepository.findAllActive();
            
            model.addAttribute("danhMucs", danhMucs);
            model.addAttribute("sizes", sizes);
            model.addAttribute("pageTitle", "Sửa Sản phẩm: " + sanPhamDTO.getTenSP());
            model.addAttribute("isEdit", true);
            
            return "nhanvien/sanpham/form";
        }
        
        try {
            SanPham updatedSanPham = sanPhamService.updateSanPham(maSP, sanPhamDTO);
            
            log.info("Product updated successfully by employee {}: {}", 
                    session.getAttribute("nhanVienUsername"), updatedSanPham.getMaSP());
            
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Cập nhật sản phẩm '" + updatedSanPham.getTenSP() + "' thành công!");
            
            return "redirect:/NhanVien/sanpham";
            
        } catch (Exception e) {
            log.error("Error updating product", e);
            
            List<DanhMucSanPham> danhMucs = danhMucRepository.findAllActive();
            List<SizeSanPham> sizes = sizeRepository.findAllActive();
            
            model.addAttribute("danhMucs", danhMucs);
            model.addAttribute("sizes", sizes);
            model.addAttribute("pageTitle", "Sửa Sản phẩm: " + sanPhamDTO.getTenSP());
            model.addAttribute("isEdit", true);
            model.addAttribute("errorMessage", "Có lỗi khi cập nhật sản phẩm: " + e.getMessage());
            
            return "nhanvien/sanpham/form";
        }
    }
    
    /**
     * Xem chi tiết sản phẩm
     */
    @GetMapping("/xem/{maSP}")
    public String viewSanPham(@PathVariable Integer maSP,
                              HttpSession session,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }
        
        // Lấy sản phẩm
        Optional<SanPham> sanPhamOpt = sanPhamService.getSanPhamById(maSP);
        if (sanPhamOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm!");
            return "redirect:/NhanVien/sanpham";
        }
        
        SanPham sanPham = sanPhamOpt.get();
        SanPhamDTO sanPhamDTO = sanPhamService.convertToDTO(sanPham);
        
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("sanPhamDTO", sanPhamDTO);
        model.addAttribute("pageTitle", "Chi tiết: " + sanPham.getTenSP());
        
        return "nhanvien/sanpham/detail";
    }
    
    /**
     * Xóa sản phẩm (soft delete)
     */
    @PostMapping("/xoa/{maSP}")
    public String deleteSanPham(@PathVariable Integer maSP,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }
        
        try {
            Optional<SanPham> sanPhamOpt = sanPhamService.getSanPhamById(maSP);
            if (sanPhamOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm!");
                return "redirect:/NhanVien/sanpham";
            }
            
            String tenSP = sanPhamOpt.get().getTenSP();
            sanPhamService.softDeleteSanPham(maSP);
            
            log.info("Product soft deleted by employee {}: {}", 
                    session.getAttribute("nhanVienUsername"), maSP);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Xóa sản phẩm '" + tenSP + "' thành công!");
            
        } catch (Exception e) {
            log.error("Error deleting product: {}", maSP, e);
            redirectAttributes.addFlashAttribute("errorMessage", 
                    "Có lỗi khi xóa sản phẩm: " + e.getMessage());
        }
        
        return "redirect:/NhanVien/sanpham";
    }
    
    /**
     * Thay đổi trạng thái sản phẩm (bán/ngừng bán)
     */
    @PostMapping("/toggle-status/{maSP}")
    public String toggleStatus(@PathVariable Integer maSP,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        
        // Kiểm tra đăng nhập
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }
        
        try {
            Optional<SanPham> sanPhamOpt = sanPhamService.getSanPhamById(maSP);
            if (sanPhamOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm!");
                return "redirect:/NhanVien/sanpham";
            }
            
            SanPham sanPham = sanPhamOpt.get();
            String tenSP = sanPham.getTenSP();
            
            // Đổi trạng thái
            byte newStatus = (byte) (sanPham.getTrangThai() == 1 ? 0 : 1);
            sanPham.setTrangThai(newStatus);
            
            // Lưu lại
            SanPhamDTO dto = sanPhamService.convertToDTO(sanPham);
            sanPhamService.updateSanPham(maSP, dto);
            
            String statusText = newStatus == 1 ? "đang bán" : "ngừng bán";
            
            log.info("Product status changed by employee {}: {} -> {}", 
                    session.getAttribute("nhanVienUsername"), maSP, statusText);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Thay đổi trạng thái sản phẩm '" + tenSP + "' thành " + statusText + " thành công!");
            
        } catch (Exception e) {
            log.error("Error changing product status: {}", maSP, e);
            redirectAttributes.addFlashAttribute("errorMessage", 
                    "Có lỗi khi thay đổi trạng thái sản phẩm: " + e.getMessage());
        }
        
        return "redirect:/NhanVien/sanpham/xem/" + maSP;
    }
}
