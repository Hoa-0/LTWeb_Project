package com.alotra.web.service.impl.admin;

import com.alotra.web.dto.admin.EmployeeAdminViewDTO;
import com.alotra.web.entity.NhanVien;
import com.alotra.web.repository.NhanVienRepository;
import com.alotra.web.service.admin.AdminEmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime; // Import mới
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class AdminEmployeeServiceImpl implements AdminEmployeeService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public List<EmployeeAdminViewDTO> getAllEmployees() {
        // Lấy tất cả nhân viên (có thể thêm phân trang sau này)
        // Bỏ qua những nhân viên đã bị xóa mềm (deletedAt != null)
        return nhanVienRepository.findAll().stream()
                //.filter(nv -> nv.getDeletedAt() == null) // Lọc bỏ soft-deleted (Giữ nguyên theo code sếp gửi)
                .map(EmployeeAdminViewDTO::new) // Chuyển đổi sang DTO
                .collect(Collectors.toList());
    }

    
    
    
    
    @Override
    @Transactional // Đảm bảo thao tác cập nhật là atomic
    public NhanVien toggleEmployeeStatus(Integer maNV) {
        // Tối ưu: Dùng helper method
        NhanVien nhanVien = findByIdOrThrow(maNV);

        // Đảo ngược trạng thái
        byte currentStatus = nhanVien.getTrangThai();
        nhanVien.setTrangThai(currentStatus == 1 ? (byte) 0 : (byte) 1);

        return nhanVienRepository.save(nhanVien); // Lưu thay đổi
    }

    
    @Override
    public Optional<NhanVien> getEmployeeById(Integer maNV) {
        // Sử dụng JpaRepository để tìm nhân viên theo ID
        return nhanVienRepository.findById(maNV);
        
        // Lưu ý: Nếu muốn ẩn nhân viên đã bị xóa mềm, cần thay đổi logic này
        // Ví dụ: return nhanVienRepository.findById(maNV).filter(nv -> nv.getDeletedAt() == null);
    }
    @Override
    @Transactional
    public NhanVien changeEmployeeRole(Integer maNV, Byte newRole) {
         // Kiểm tra xem newRole có hợp lệ không (1, 2, 3...)
         if (newRole == null || (newRole != 1 && newRole != 2 && newRole != 3)) { // Ví dụ chỉ cho phép vai trò 1, 2, 3
             throw new IllegalArgumentException("Vai trò mới không hợp lệ.");
         }
         
        // Tối ưu: Dùng helper method
        NhanVien nhanVien = findByIdOrThrow(maNV);

        // Cập nhật vai trò mới
        nhanVien.setVaiTro(newRole);

        return nhanVienRepository.save(nhanVien);
    }
    
    
    
    @Override
    @Transactional
    public NhanVien updateEmployeeInfo(NhanVien updatedNhanVien) {
        // Tối ưu: Dùng helper method để kiểm tra tồn tại
        NhanVien existingNhanVien = findByIdOrThrow(updatedNhanVien.getMaNV());

        // Cập nhật các trường cần thiết từ updatedNhanVien sang existingNhanVien
        existingNhanVien.setTenNV(updatedNhanVien.getTenNV());
        existingNhanVien.setEmail(updatedNhanVien.getEmail());
        // KHÔNG CẬP NHẬT: username, password, vaiTro, trangThai (vì đã có hàm riêng)

        // Lưu và trả về
        return nhanVienRepository.save(existingNhanVien);
    }
    
    

    // ===== CÁC PHƯƠNG THỨC MỚI ĐƯỢC THÊM VÀO =====

    @Override
    @Transactional
    public void softDeleteEmployee(Integer maNV) {
        NhanVien nhanVien = findByIdOrThrow(maNV);
        nhanVien.setDeletedAt(LocalDateTime.now());
        nhanVien.setTrangThai((byte) 0); // Nên deactive khi xóa
        nhanVienRepository.save(nhanVien);
    }

    @Override
    @Transactional
    public NhanVien restoreEmployee(Integer maNV) { // <-- Thêm mới
        NhanVien nhanVien = findByIdOrThrow(maNV);
        // Chỉ khôi phục nếu đang bị soft-deleted
        if (nhanVien.getDeletedAt() != null) {
            nhanVien.setDeletedAt(null);
            // Có thể tùy chọn set lại TrangThai = 1 khi khôi phục, hoặc giữ nguyên = 0
            nhanVien.setTrangThai((byte) 1); // Ví dụ: tự động kích hoạt lại
            return nhanVienRepository.save(nhanVien);
        }
        // Nếu không bị xóa thì không làm gì cả, trả về entity hiện tại
        return nhanVien;
    }

    @Override
    @Transactional
    public void deleteEmployeePermanently(Integer maNV) { // <-- Thêm mới
        NhanVien nhanVien = findByIdOrThrow(maNV);
        // Thêm kiểm tra an toàn (ví dụ: chỉ xóa vĩnh viễn nếu đã bị xóa mềm trước đó)
        if (nhanVien.getDeletedAt() == null) {
             throw new IllegalStateException("Nhân viên chưa bị xóa tạm thời, không thể xóa vĩnh viễn.");
        }
        nhanVienRepository.delete(nhanVien); // Thực hiện xóa khỏi DB
    }


    // Helper method
    private NhanVien findByIdOrThrow(Integer maNV) {
        return nhanVienRepository.findById(maNV)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhân viên với Mã: " + maNV));
    }
}