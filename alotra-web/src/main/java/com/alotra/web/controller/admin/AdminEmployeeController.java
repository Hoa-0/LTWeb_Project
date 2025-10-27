package com.alotra.web.controller.admin;

import com.alotra.web.dto.admin.EmployeeAdminViewDTO;
import com.alotra.web.entity.NhanVien;
import com.alotra.web.service.admin.AdminEmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid; // Thêm Valid nếu dùng validation
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Thêm BindingResult nếu dùng validation
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional; // Thêm Optional

@Controller
@RequestMapping("/admin/employees")
public class AdminEmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(AdminEmployeeController.class);

    @Autowired
    private AdminEmployeeService adminEmployeeService;

    // Hiển thị danh sách nhân viên
    @GetMapping
    public String listEmployees(Model model) {
        logger.info(">>> GET /admin/employees - listEmployees method called!");
        List<EmployeeAdminViewDTO> employees = adminEmployeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "admin/employee-list";
    }

    // Hiển thị form sửa thông tin nhân viên
    @GetMapping("/{maNV}/edit")
    public String showEditEmployeeForm(@PathVariable Integer maNV, Model model, RedirectAttributes redirectAttributes) {
        Optional<NhanVien> nhanVienOpt = adminEmployeeService.getEmployeeById(maNV);
        if (nhanVienOpt.isPresent()) {
            model.addAttribute("nhanVien", nhanVienOpt.get());
            model.addAttribute("pageTitle", "Sửa thông tin Nhân viên"); // Thêm tiêu đề
            return "admin/employee-form"; // Trả về view form mới
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy nhân viên với Mã: " + maNV);
            return "redirect:/admin/employees";
        }
    }

    // Xử lý submit form sửa thông tin nhân viên
    @PostMapping("/update")
    public String updateEmployee(@Valid @ModelAttribute("nhanVien") NhanVien nhanVien, // Thêm @Valid nếu có validation
                                 BindingResult bindingResult, // Bắt lỗi validation
                                 RedirectAttributes redirectAttributes, Model model) {

        // Nếu có lỗi validation, quay lại form và hiển thị lỗi
        if (bindingResult.hasErrors()) {
             model.addAttribute("pageTitle", "Sửa thông tin Nhân viên");
            // Có thể thêm model.addAttribute("errorMessage", "Vui lòng kiểm tra lại thông tin nhập.");
            return "admin/employee-form";
        }

        try {
            adminEmployeeService.updateEmployeeInfo(nhanVien);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin nhân viên '" + nhanVien.getTenNV() + "' thành công.");
            return "redirect:/admin/employees";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/employees";
        } catch (Exception e) { // Bắt các lỗi khác, ví dụ trùng email
            logger.error("Error updating employee: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật nhân viên: " + e.getMessage());
             // Thay vì redirect, có thể quay lại form để giữ lại dữ liệu đã nhập
             model.addAttribute("pageTitle", "Sửa thông tin Nhân viên");
             // model.addAttribute("nhanVien", nhanVien); // Giữ lại object nhanVien đã submit
             return "admin/employee-form"; // Quay lại form
        }
    }


    // Xử lý bật/tắt trạng thái (Form POST) - Giữ nguyên
    @PostMapping("/{maNV}/toggle-status")
    public String toggleStatus(@PathVariable Integer maNV, RedirectAttributes redirectAttributes) {
        // ... (code giữ nguyên) ...
        try {
            NhanVien updatedNhanVien = adminEmployeeService.toggleEmployeeStatus(maNV);
            String statusText = updatedNhanVien.getTrangThai() == 1 ? "kích hoạt" : "vô hiệu hóa";
            redirectAttributes.addFlashAttribute("successMessage", "Đã " + statusText + " nhân viên '" + updatedNhanVien.getTenNV() + "'.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            logger.error("Error toggling employee status: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi cập nhật trạng thái.");
        }
        return "redirect:/admin/employees";
    }

    // Xử lý thay đổi vai trò (Form POST) - Giữ nguyên
    @PostMapping("/{maNV}/change-role")
    public String changeRole(@PathVariable Integer maNV, @RequestParam Byte newRole, RedirectAttributes redirectAttributes) {
        // ... (code giữ nguyên) ...
         try {
             NhanVien updatedNhanVien = adminEmployeeService.changeEmployeeRole(maNV, newRole);
             redirectAttributes.addFlashAttribute("successMessage", "Đã đổi vai trò của '" + updatedNhanVien.getTenNV() + "' thành " + updatedNhanVien.getVaiTroText() + ".");
         } catch (EntityNotFoundException | IllegalArgumentException e) {
             redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
         } catch (Exception e) {
            logger.error("Error changing employee role: {}", e.getMessage());
             redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi đổi vai trò.");
         }
        return "redirect:/admin/employees";
    }

     // ===== PHƯƠNG THỨC ĐÃ CẬP NHẬT (TỪ CODE 2) =====
     // Xử lý xóa mềm nhân viên (Form POST)
     @PostMapping("/{maNV}/delete")
     public String deleteEmployee(@PathVariable Integer maNV, RedirectAttributes redirectAttributes) {
         try {
             adminEmployeeService.softDeleteEmployee(maNV);
             redirectAttributes.addFlashAttribute("successMessage", "Đã xóa tạm thời nhân viên (Mã: " + maNV + ").");
         } catch (EntityNotFoundException e) {
             redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
         } catch (Exception e) {
            logger.error("Error soft deleting employee: {}", e.getMessage()); // Log đã cập nhật
             redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi xóa nhân viên.");
         }
         return "redirect:/admin/employees";
     }

     // ===== CÁC PHƯƠNG THỨC MỚI (TỪ CODE 2) =====

     // Xử lý khôi phục nhân viên (Form POST)
     @PostMapping("/{maNV}/restore")
     public String restoreEmployee(@PathVariable Integer maNV, RedirectAttributes redirectAttributes) {
         try {
             NhanVien restoredNhanVien = adminEmployeeService.restoreEmployee(maNV);
             redirectAttributes.addFlashAttribute("successMessage", "Đã khôi phục nhân viên '" + restoredNhanVien.getTenNV() + "'.");
         } catch (EntityNotFoundException e) {
             redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
         } catch (Exception e) {
             logger.error("Error restoring employee: {}", e.getMessage());
             redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi khôi phục nhân viên.");
         }
         return "redirect:/admin/employees";
     }

     // Xử lý xóa vĩnh viễn nhân viên (Form POST)
     @PostMapping("/{maNV}/delete-permanently")
     public String deleteEmployeePermanently(@PathVariable Integer maNV, RedirectAttributes redirectAttributes) {
         try {
             adminEmployeeService.deleteEmployeePermanently(maNV);
             redirectAttributes.addFlashAttribute("successMessage", "Đã xóa vĩnh viễn nhân viên (Mã: " + maNV + ").");
         } catch (EntityNotFoundException | IllegalStateException e) { // Bắt cả lỗi IllegalStateException
             redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
         } catch (Exception e) {
             logger.error("Error permanently deleting employee: {}", e.getMessage());
             redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi xóa vĩnh viễn nhân viên.");
         }
         return "redirect:/admin/employees";
     }
}