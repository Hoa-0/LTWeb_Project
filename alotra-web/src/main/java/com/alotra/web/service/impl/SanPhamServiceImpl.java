package com.alotra.web.service.impl;

import com.alotra.web.dto.SanPhamDTO;
import com.alotra.web.entity.BienTheSanPham;
import com.alotra.web.entity.DanhMucSanPham;
import com.alotra.web.entity.SanPham;
import com.alotra.web.repository.BienTheSanPhamRepository;
import com.alotra.web.repository.DanhMucSanPhamRepository;
import com.alotra.web.repository.SanPhamRepository;
import com.alotra.web.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SanPhamServiceImpl implements SanPhamService {
    
    private final SanPhamRepository sanPhamRepository;
    private final DanhMucSanPhamRepository danhMucRepository;
    private final BienTheSanPhamRepository bienTheRepository;
    
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;
    
    @Value("${app.upload.url:/uploads}")
    private String uploadUrl;
    
    @Override
    @Transactional(readOnly = true)
    public Page<SanPham> getAllSanPham(Pageable pageable) {
        return sanPhamRepository.findAllActive(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SanPham> getActiveSanPham(Pageable pageable) {
        return sanPhamRepository.findByTrangThaiAndNotDeleted((byte) 1, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SanPham> getActiveSanPhamByCategory(Integer maDM, Pageable pageable) {
        return sanPhamRepository.findActiveByMaDM(maDM, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<SanPham> getAllSanPhamByCategory(Integer maDM, Pageable pageable) {
        return sanPhamRepository.findByMaDMAndNotDeleted(maDM, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<SanPham> searchSanPham(String keyword, Integer maDM, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            if (maDM != null) {
                return sanPhamRepository.findByTenSPContainingIgnoreCaseAndMaDMAndNotDeleted(keyword.trim(), maDM, pageable);
            } else {
                return sanPhamRepository.findByTenSPContainingIgnoreCaseAndNotDeleted(keyword.trim(), pageable);
            }
        } else {
            if (maDM != null) {
                return getAllSanPhamByCategory(maDM, pageable);
            } else {
                return getAllSanPham(pageable);
            }
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SanPham> getSanPhamById(Integer maSP) {
        return sanPhamRepository.findByIdAndNotDeleted(maSP);
    }
    
    @Override
    @Transactional(readOnly = true)
    public SanPhamDTO getSanPhamDTOById(Integer maSP) {
        Optional<SanPham> sanPhamOpt = getSanPhamById(maSP);
        if (sanPhamOpt.isPresent()) {
            return convertToDTO(sanPhamOpt.get());
        }
        return null;
    }
    
    @Override
    public SanPham saveSanPham(SanPhamDTO sanPhamDTO) {
        log.info("Saving new product: {}", sanPhamDTO.getTenSP());
        
        // Validate danh mục
        Optional<DanhMucSanPham> danhMucOpt = danhMucRepository.findByIdAndNotDeleted(sanPhamDTO.getMaDM());
        if (danhMucOpt.isEmpty()) {
            throw new RuntimeException("Danh mục không tồn tại");
        }
        
        // Upload ảnh nếu có
        String imageUrl = null;
        if (sanPhamDTO.getAnhSanPham() != null && !sanPhamDTO.getAnhSanPham().isEmpty()) {
            imageUrl = uploadImage(sanPhamDTO.getAnhSanPham());
        }
        
        // Tạo sản phẩm mới
        SanPham sanPham = new SanPham();
        sanPham.setTenSP(sanPhamDTO.getTenSP());
        sanPham.setMoTa(sanPhamDTO.getMoTa());
        sanPham.setMaDM(sanPhamDTO.getMaDM());
        sanPham.setUrlAnh(imageUrl);
        sanPham.setTrangThai(sanPhamDTO.getTrangThai());
        
        // Lưu sản phẩm
        sanPham = sanPhamRepository.save(sanPham);
        
        // Lưu biến thể
        saveBienThes(sanPham.getMaSP(), sanPhamDTO.getBienThes());
        
        log.info("Product saved successfully with ID: {}", sanPham.getMaSP());
        return sanPham;
    }
    
    @Override
    public SanPham updateSanPham(Integer maSP, SanPhamDTO sanPhamDTO) {
        log.info("Updating product with ID: {}", maSP);
        
        Optional<SanPham> sanPhamOpt = getSanPhamById(maSP);
        if (sanPhamOpt.isEmpty()) {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }
        
        SanPham sanPham = sanPhamOpt.get();
        
        // Validate danh mục
        Optional<DanhMucSanPham> danhMucOpt = danhMucRepository.findByIdAndNotDeleted(sanPhamDTO.getMaDM());
        if (danhMucOpt.isEmpty()) {
            throw new RuntimeException("Danh mục không tồn tại");
        }
        
        // Upload ảnh mới nếu có
        if (sanPhamDTO.getAnhSanPham() != null && !sanPhamDTO.getAnhSanPham().isEmpty()) {
            // Xóa ảnh cũ
            if (sanPham.getUrlAnh() != null) {
                deleteImage(sanPham.getUrlAnh());
            }
            // Upload ảnh mới
            String newImageUrl = uploadImage(sanPhamDTO.getAnhSanPham());
            sanPham.setUrlAnh(newImageUrl);
        }
        
        // Cập nhật thông tin sản phẩm
        sanPham.setTenSP(sanPhamDTO.getTenSP());
        sanPham.setMoTa(sanPhamDTO.getMoTa());
        sanPham.setMaDM(sanPhamDTO.getMaDM());
        sanPham.setTrangThai(sanPhamDTO.getTrangThai());
        
        // Lưu sản phẩm
        sanPham = sanPhamRepository.save(sanPham);
        
        // Cập nhật biến thể (xóa cũ, thêm mới)
        bienTheRepository.deleteByMaSP(maSP);
        saveBienThes(maSP, sanPhamDTO.getBienThes());
        
        log.info("Product updated successfully: {}", maSP);
        return sanPham;
    }
    
    @Override
    public void deleteSanPham(Integer maSP) {
        log.info("Hard deleting product with ID: {}", maSP);
        
        Optional<SanPham> sanPhamOpt = getSanPhamById(maSP);
        if (sanPhamOpt.isPresent()) {
            SanPham sanPham = sanPhamOpt.get();
            
            // Xóa ảnh
            if (sanPham.getUrlAnh() != null) {
                deleteImage(sanPham.getUrlAnh());
            }
            
            // Xóa biến thể
            bienTheRepository.deleteByMaSP(maSP);
            
            // Xóa sản phẩm
            sanPhamRepository.delete(sanPham);
            
            log.info("Product hard deleted successfully: {}", maSP);
        }
    }
    
    @Override
    public void softDeleteSanPham(Integer maSP) {
        log.info("Soft deleting product with ID: {}", maSP);
        
        Optional<SanPham> sanPhamOpt = getSanPhamById(maSP);
        if (sanPhamOpt.isPresent()) {
            SanPham sanPham = sanPhamOpt.get();
            sanPham.softDelete();
            sanPhamRepository.save(sanPham);
            
            log.info("Product soft deleted successfully: {}", maSP);
        }
    }
    
    @Override
    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        
        try {
            // Tạo thư mục upload nếu chưa tồn tại
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Tạo tên file unique
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID().toString() + extension;
            
            // Lưu file
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Trả về URL
            String fileUrl = uploadUrl + "/" + filename;
            log.info("Image uploaded successfully: {}", fileUrl);
            return fileUrl;
            
        } catch (IOException e) {
            log.error("Error uploading image: ", e);
            throw new RuntimeException("Lỗi khi upload ảnh: " + e.getMessage());
        }
    }
    
    @Override
    public void deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }
        
        try {
            // Lấy tên file từ URL
            String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            Path filePath = Paths.get(uploadDir, filename);
            
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                log.info("Image deleted successfully: {}", imageUrl);
            }
        } catch (IOException e) {
            log.error("Error deleting image: {}", imageUrl, e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalSanPham() {
        return sanPhamRepository.countAllActive();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalSanPhamByCategory(Integer maDM) {
        return sanPhamRepository.countByMaDMAndNotDeleted(maDM);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalActiveSanPham() {
        return sanPhamRepository.countByTrangThaiAndNotDeleted((byte) 1);
    }
    
    @Override
    public SanPhamDTO convertToDTO(SanPham sanPham) {
        if (sanPham == null) {
            return null;
        }
        
        SanPhamDTO dto = new SanPhamDTO();
        dto.setMaSP(sanPham.getMaSP());
        dto.setTenSP(sanPham.getTenSP());
        dto.setMoTa(sanPham.getMoTa());
        dto.setMaDM(sanPham.getMaDM());
        dto.setUrlAnh(sanPham.getUrlAnh());
        dto.setTrangThai(sanPham.getTrangThai());
        
        // Lấy tên danh mục
        if (sanPham.getDanhMuc() != null) {
            dto.setTenDanhMuc(sanPham.getDanhMuc().getTenDM());
        }
        
        // Lấy biến thể
        List<BienTheSanPham> bienThes = bienTheRepository.findByMaSP(sanPham.getMaSP());
        List<SanPhamDTO.BienTheDTO> bienTheDTOs = new ArrayList<>();
        
        for (BienTheSanPham bt : bienThes) {
            SanPhamDTO.BienTheDTO btDTO = new SanPhamDTO.BienTheDTO();
            btDTO.setMaBT(bt.getMaBT());
            btDTO.setMaSize(bt.getMaSize());
            btDTO.setGiaBan(bt.getGiaBan());
            btDTO.setTrangThai(bt.getTrangThai());
            
            // Lấy tên size
            if (bt.getSizeSanPham() != null) {
                btDTO.setTenSize(bt.getSizeSanPham().getTenSize());
            }
            
            bienTheDTOs.add(btDTO);
        }
        
        dto.setBienThes(bienTheDTOs);
        return dto;
    }
    
    @Override
    public SanPham convertToEntity(SanPhamDTO sanPhamDTO) {
        if (sanPhamDTO == null) {
            return null;
        }
        
        SanPham sanPham = new SanPham();
        sanPham.setMaSP(sanPhamDTO.getMaSP());
        sanPham.setTenSP(sanPhamDTO.getTenSP());
        sanPham.setMoTa(sanPhamDTO.getMoTa());
        sanPham.setMaDM(sanPhamDTO.getMaDM());
        sanPham.setUrlAnh(sanPhamDTO.getUrlAnh());
        sanPham.setTrangThai(sanPhamDTO.getTrangThai());
        
        return sanPham;
    }
    
    private void saveBienThes(Integer maSP, List<SanPhamDTO.BienTheDTO> bienTheDTOs) {
        if (bienTheDTOs == null || bienTheDTOs.isEmpty()) {
            return;
        }
        
        for (SanPhamDTO.BienTheDTO btDTO : bienTheDTOs) {
            BienTheSanPham bienThe = new BienTheSanPham();
            bienThe.setMaSP(maSP);
            bienThe.setMaSize(btDTO.getMaSize());
            bienThe.setGiaBan(btDTO.getGiaBan());
            bienThe.setTrangThai(btDTO.getTrangThai());
            
            bienTheRepository.save(bienThe);
        }
    }
}