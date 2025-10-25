package com.alotra.web.service;

import com.alotra.web.dto.SanPhamDTO;
import com.alotra.web.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface SanPhamService {
    
    // CRUD operations
    Page<SanPham> getAllSanPham(Pageable pageable);
    Page<SanPham> getAllSanPhamByCategory(Integer maDM, Pageable pageable);
    Page<SanPham> searchSanPham(String keyword, Integer maDM, Pageable pageable);
    Optional<SanPham> getSanPhamById(Integer maSP);
    SanPhamDTO getSanPhamDTOById(Integer maSP);
    
    SanPham saveSanPham(SanPhamDTO sanPhamDTO);
    SanPham updateSanPham(Integer maSP, SanPhamDTO sanPhamDTO);
    void deleteSanPham(Integer maSP);
    void softDeleteSanPham(Integer maSP);
    
    // Image upload
    String uploadImage(MultipartFile file);
    void deleteImage(String imageUrl);
    
    // Statistics
    long getTotalSanPham();
    long getTotalSanPhamByCategory(Integer maDM);
    long getTotalActiveSanPham();
    
    // Utility methods
    SanPhamDTO convertToDTO(SanPham sanPham);
    SanPham convertToEntity(SanPhamDTO sanPhamDTO);
}