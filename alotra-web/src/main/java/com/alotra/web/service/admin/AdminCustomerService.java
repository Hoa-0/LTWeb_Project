package com.alotra.web.service.admin;

import com.alotra.web.dto.admin.CustomerAdminViewDTO;
import com.alotra.web.entity.KhachHang; // Import KhachHang

import java.util.List;
import java.util.Optional;

public interface AdminCustomerService {
    List<CustomerAdminViewDTO> getAllCustomers();
    KhachHang toggleCustomerStatus(Integer maKH); // Kích hoạt/Vô hiệu hóa
    Optional<KhachHang> getCustomerById(Integer maKH); // Lấy chi tiết (nếu cần sau này)
 // Thêm phương thức xóa vĩnh viễn
    void deleteCustomerPermanently(Integer maKH);
}