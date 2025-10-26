package com.alotra.web.repository;

import com.alotra.web.entity.DanhMucSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DanhMucSanPhamRepository extends JpaRepository<DanhMucSanPham, Integer> {
    
    @Query("SELECT dm FROM DanhMucSanPham dm WHERE dm.trangThai = 1 ORDER BY dm.tenDM")
    List<DanhMucSanPham> findAllActive();
    
    List<DanhMucSanPham> findByTenDMContainingIgnoreCase(String tenDM);
    
    DanhMucSanPham findByTenDM(String tenDM);
    
    /**
     * Tìm danh mục theo ID và chưa bị xóa
     */
    @Query("SELECT dm FROM DanhMucSanPham dm WHERE dm.maDM = :id AND dm.trangThai = 1")
    Optional<DanhMucSanPham> findByIdAndNotDeleted(@Param("id") Integer id);
}