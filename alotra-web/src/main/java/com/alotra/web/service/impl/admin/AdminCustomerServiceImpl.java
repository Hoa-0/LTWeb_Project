package com.alotra.web.service.impl.admin;

import com.alotra.web.dto.admin.CustomerAdminViewDTO;
import com.alotra.web.entity.KhachHang;
import com.alotra.web.repository.KhachHangRepository; // Dùng repo đã tạo ở CP3
import com.alotra.web.service.admin.AdminCustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminCustomerServiceImpl implements AdminCustomerService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public List<CustomerAdminViewDTO> getAllCustomers() {
        // Lấy tất cả khách hàng (không có DeletedAt để lọc)
        return khachHangRepository.findAll().stream()
                .map(CustomerAdminViewDTO::new) // Chuyển sang DTO
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public KhachHang toggleCustomerStatus(Integer maKH) {
        KhachHang khachHang = khachHangRepository.findById(maKH)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khách hàng với Mã: " + maKH));

        // Đảo ngược trạng thái
        byte currentStatus = khachHang.getTrangThai();
        khachHang.setTrangThai(currentStatus == 1 ? (byte) 0 : (byte) 1);

        return khachHangRepository.save(khachHang);
    }

    @Override
    public Optional<KhachHang> getCustomerById(Integer maKH) {
        return khachHangRepository.findById(maKH);
    }
    
 // ===== PHƯƠNG THỨC MỚI: XÓA VĨNH VIỄN KHÁCH HÀNG =====
    @Override
    @Transactional
    public void deleteCustomerPermanently(Integer maKH) {
        KhachHang khachHang = findByIdOrThrow(maKH);
        // Lưu ý: Thường nên có logic kiểm tra an toàn tại đây (ví dụ: kiểm tra nếu KH có đơn hàng thì không xóa)
        
        khachHangRepository.delete(khachHang); // Thực hiện xóa khỏi DB
    }
 // ===== PHƯƠNG THỨC HELPER =====
    // Sếp thêm hoặc sử dụng lại Helper method này để tìm KH hoặc báo lỗi EntityNotFoundException
    private KhachHang findByIdOrThrow(Integer maKH) {
        return khachHangRepository.findById(maKH)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khách hàng với Mã: " + maKH));
    }
}