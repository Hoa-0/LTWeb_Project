package com.alotra.web.repository;

import com.alotra.web.entity.DanhMucSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DanhMucSanPhamRepository extends JpaRepository<DanhMucSanPham, Integer> {
    
    // Tìm tất cả danh mục chưa bị xóa
    @Query("SELECT dm FROM DanhMucSanPham dm WHERE dm.deletedAt IS NULL ORDER BY dm.tenDM")
    List<DanhMucSanPham> findAllActive();
    
    // Tìm danh mục theo ID và chưa bị xóa
    @Query("SELECT dm FROM DanhMucSanPham dm WHERE dm.maDM = :id AND dm.deletedAt IS NULL")
    Optional<DanhMucSanPham> findByIdAndNotDeleted(Integer id);
    
    // Tìm danh mục theo tên
    @Query("SELECT dm FROM DanhMucSanPham dm WHERE LOWER(dm.tenDM) = LOWER(:tenDM) AND dm.deletedAt IS NULL")
    Optional<DanhMucSanPham> findByTenDMIgnoreCaseAndNotDeleted(String tenDM);
    
    // Đếm số danh mục active
    @Query("SELECT COUNT(dm) FROM DanhMucSanPham dm WHERE dm.deletedAt IS NULL")
    long countAllActive();
}