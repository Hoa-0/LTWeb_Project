package com.alotra.web.controller.admin;

import com.alotra.web.dto.admin.CustomerAdminViewDTO;
import com.alotra.web.entity.KhachHang;
import com.alotra.web.service.admin.AdminCustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/customers")
public class AdminCustomerController {

	private static final Logger logger = LoggerFactory.getLogger(AdminCustomerController.class);

	@Autowired
	private AdminCustomerService adminCustomerService;

	// Hiển thị danh sách khách hàng
	@GetMapping
	public String listCustomers(Model model) {
		logger.info(">>> GET /admin/customers - listCustomers method called!");
		List<CustomerAdminViewDTO> customers = adminCustomerService.getAllCustomers();
		model.addAttribute("customers", customers);
		return "admin/customer-list"; // Trả về view mới
	}

	// Xử lý bật/tắt trạng thái (Form POST)
	@PostMapping("/{maKH}/toggle-status")
	public String toggleStatus(@PathVariable Integer maKH, RedirectAttributes redirectAttributes) {
		try {
			KhachHang updatedKhachHang = adminCustomerService.toggleCustomerStatus(maKH);
			String statusText = updatedKhachHang.getTrangThai() == 1 ? "kích hoạt" : "vô hiệu hóa";
			redirectAttributes.addFlashAttribute("successMessage",
					"Đã " + statusText + " khách hàng '" + updatedKhachHang.getTenKH() + "'.");
		} catch (EntityNotFoundException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		} catch (Exception e) {
			logger.error("Error toggling customer status: {}", e.getMessage());
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi cập nhật trạng thái khách hàng.");
		}
		return "redirect:/admin/customers"; // Redirect về trang danh sách khách hàng
	}

	// Xử lý xóa vĩnh viễn khách hàng (Form POST)
	@PostMapping("/{maKH}/delete-permanently")
	public String deleteCustomerPermanently(@PathVariable Integer maKH, RedirectAttributes redirectAttributes) {
		try {
			adminCustomerService.deleteCustomerPermanently(maKH);
			redirectAttributes.addFlashAttribute("successMessage",
					"Đã xóa vĩnh viễn khách hàng (Mã: " + maKH + ") khỏi hệ thống.");

		} catch (EntityNotFoundException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		} catch (Exception e) {
			// logger.error("Error deleting customer permanently: {}", e.getMessage()); //
			// Thêm log
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi xóa vĩnh viễn khách hàng.");
		}
		return "redirect:/admin/customers";
	}

	// Có thể thêm @GetMapping("/{maKH}") để xem chi tiết nếu cần
}