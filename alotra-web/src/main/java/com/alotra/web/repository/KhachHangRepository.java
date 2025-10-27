package com.alotra.web.repository;

import com.alotra.web.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    // Đếm tổng số khách hàng đang active
    long countByTrangThai(Byte trangThai);

    // Có thể thêm các phương thức khác nếu cần
}