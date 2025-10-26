package com.alotra.web.service.impl;

import com.alotra.web.dto.SanPhamDTO;
import com.alotra.web.entity.BienTheSanPham;
import com.alotra.web.entity.DanhMucSanPham;
import com.alotra.web.entity.SanPham;
import com.alotra.web.entity.ProductMedia;
import com.alotra.web.repository.BienTheSanPhamRepository;
import com.alotra.web.repository.DanhMucSanPhamRepository;
import com.alotra.web.repository.SanPhamRepository;
import com.alotra.web.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SanPhamServiceImpl implements SanPhamService {
    
    private final SanPhamRepository sanPhamRepository;
    private final DanhMucSanPhamRepository danhMucRepository;
    private final BienTheSanPhamRepository bienTheRepository;
    @Autowired(required = false)
    private com.alotra.web.service.CloudinaryService cloudinaryService;
    private final com.alotra.web.repository.ProductMediaRepository productMediaRepository;
    
    @Autowired
    private jakarta.persistence.EntityManager entityManager;
    
    @Autowired(required = false)
    private com.alotra.web.repository.KhuyenMaiSanPhamRepository khuyenMaiSanPhamRepository;
    
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
        
        // Validate biến thể: yêu cầu ít nhất 1 biến thể hợp lệ
        if (sanPhamDTO.getBienThes() == null || sanPhamDTO.getBienThes().isEmpty()) {
            throw new RuntimeException("Vui lòng thêm ít nhất một biến thể sản phẩm");
        }

        // Set image URL if provided (không upload, chỉ set path)
        String imageUrl = null;
        if (sanPhamDTO.getAnhSanPham() != null && !sanPhamDTO.getAnhSanPham().isEmpty()) {
            imageUrl = sanPhamDTO.getAnhSanPham(); // Use provided path directly
        }
        
    // Tạo sản phẩm mới
        SanPham sanPham = new SanPham();
        sanPham.setTenSP(sanPhamDTO.getTenSP());
        sanPham.setMoTa(sanPhamDTO.getMoTa());
        sanPham.setMaDM(sanPhamDTO.getMaDM());
        sanPham.setUrlAnh(imageUrl);
    // Mặc định trạng thái = 1 nếu không truyền lên
    sanPham.setTrangThai(safeStatus(sanPhamDTO.getTrangThai(), (byte) 1));
        
        // Lưu sản phẩm
        sanPham = sanPhamRepository.save(sanPham);

        // Ghi media chính (nếu có ảnh)
        if (imageUrl != null && !imageUrl.isEmpty()) {
            ProductMedia media = new ProductMedia();
            media.setProductId(sanPham.getMaSP());
            media.setUrl(imageUrl);
            media.setIsPrimary(true);
            productMediaRepository.save(media);
        }

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
            // Set ảnh mới (không upload, chỉ set path)
            String newImageUrl = sanPhamDTO.getAnhSanPham();
            sanPham.setUrlAnh(newImageUrl);

            // Cập nhật media chính trong bảng ProductMedia
            try {
                productMediaRepository.clearPrimaryForProduct(maSP);
            } catch (Exception ex) {
                log.warn("Unable to clear previous primary media for product {}: {}", maSP, ex.getMessage());
            }
            ProductMedia media = new ProductMedia();
            media.setProductId(maSP);
            media.setUrl(newImageUrl);
            media.setIsPrimary(true);
            productMediaRepository.save(media);
        }
        
        // Cập nhật thông tin sản phẩm
        sanPham.setTenSP(sanPhamDTO.getTenSP());
        sanPham.setMoTa(sanPhamDTO.getMoTa());
        sanPham.setMaDM(sanPhamDTO.getMaDM());
    // Nếu không truyền trạng thái từ form, mặc định là 0 (ngừng bán)
    sanPham.setTrangThai(safeStatus(sanPhamDTO.getTrangThai(), (byte) 0));
        
        // Lưu sản phẩm
        sanPham = sanPhamRepository.save(sanPham);
        
        // Cập nhật biến thể bằng cơ chế upsert để tránh xung đột FK và UNIQUE (MaSP, MaSize)
        if (sanPhamDTO.getBienThes() != null) {
            // Lấy các biến thể hiện có
            List<BienTheSanPham> existing = bienTheRepository.findByMaSP(maSP);
            java.util.Map<Integer, BienTheSanPham> bySize = new java.util.HashMap<>();
            for (BienTheSanPham bt : existing) {
                bySize.put(bt.getMaSize(), bt);
            }

            // Xử lý danh sách mới: cập nhật nếu trùng size, thêm nếu chưa có
            Set<Integer> seenSizes = new HashSet<>();
            if (sanPhamDTO.getBienThes() != null) {
                for (SanPhamDTO.BienTheDTO btDTO : sanPhamDTO.getBienThes()) {
                    if (btDTO == null || btDTO.getMaSize() == null || btDTO.getGiaBan() == null) continue;
                    Integer sizeId = btDTO.getMaSize();
                    if (seenSizes.contains(sizeId)) {
                        // bỏ trùng ở input
                        continue;
                    }
                    seenSizes.add(sizeId);

                    BienTheSanPham target = bySize.get(sizeId);
                    if (target != null) {
                        // update giá/trạng thái
                        target.setGiaBan(btDTO.getGiaBan());
                        target.setTrangThai(safeStatus(btDTO.getTrangThai(), (byte) 0));
                        bienTheRepository.save(target);
                        // đánh dấu đã xử lý
                        bySize.remove(sizeId);
                    } else {
                        // thêm mới
                        BienTheSanPham newBt = new BienTheSanPham();
                        newBt.setMaSP(maSP);
                        newBt.setMaSize(sizeId);
                        newBt.setGiaBan(btDTO.getGiaBan());
                        newBt.setTrangThai(safeStatus(btDTO.getTrangThai(), (byte) 0));
                        bienTheRepository.save(newBt);
                    }
                }
            }

            // Những biến thể còn lại không còn trong input -> không xóa cứng để tránh FK; chuyển sang ngừng bán
            for (BienTheSanPham leftover : bySize.values()) {
                leftover.setTrangThai((byte) 0);
                bienTheRepository.save(leftover);
            }
        }
        
        log.info("Product updated successfully: {}", maSP);
        return sanPham;
    }
    
    @Override
    public void deleteSanPham(Integer maSP) {
        log.info("Hard deleting product with ID: {}", maSP);
        
        Optional<SanPham> sanPhamOpt = getSanPhamById(maSP);
        if (sanPhamOpt.isEmpty()) {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }
        
        SanPham sanPham = sanPhamOpt.get();
        
        try {
            // 1. Kiểm tra xem sản phẩm có đang được sử dụng trong đơn hàng không
            if (isProductUsedInOrders(maSP)) {
                throw new RuntimeException("Không thể xóa sản phẩm này vì đã có trong đơn hàng. Chỉ có thể ngừng bán.");
            }
            
            // 2. Xóa ảnh từ storage
            if (sanPham.getUrlAnh() != null) {
                deleteImage(sanPham.getUrlAnh());
            }
            
            // 3. Xóa các ProductMedia liên quan
            try {
                productMediaRepository.deleteByProductId(maSP);
                log.info("Deleted product media for product: {}", maSP);
            } catch (Exception e) {
                log.warn("Unable to delete product media for product {}: {}", maSP, e.getMessage());
            }
            
            // 4. Xóa các biến thể trước (để tránh foreign key constraint)
            bienTheRepository.deleteByMaSP(maSP);
            log.info("Deleted product variants for product: {}", maSP);
            
            // 5. Xóa khuyến mãi sản phẩm (nếu có)
            try {
                // Giả sử có KhuyenMaiSanPhamRepository
                deletePromotionProducts(maSP);
                log.info("Deleted promotion products for product: {}", maSP);
            } catch (Exception e) {
                log.warn("Unable to delete promotion products for product {}: {}", maSP, e.getMessage());
            }
            
            // 6. Cuối cùng xóa sản phẩm
            sanPhamRepository.delete(sanPham);
            
            log.info("Product hard deleted successfully: {}", maSP);
            
        } catch (Exception e) {
            log.error("Error deleting product {}: {}", maSP, e.getMessage());
            if (e.getMessage().contains("foreign key constraint") || 
                e.getMessage().contains("constraint violation") ||
                e.getMessage().contains("referential integrity")) {
                throw new RuntimeException("Không thể xóa sản phẩm này vì đang được tham chiếu bởi dữ liệu khác. Vui lòng sử dụng 'Ngừng bán' thay vì xóa.");
            } else {
                throw new RuntimeException("Lỗi khi xóa sản phẩm: " + e.getMessage());
            }
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
            // Prefer Cloudinary if configured
            if (cloudinaryService != null && cloudinaryService.isConfigured()) {
                log.info("Uploading product image via Cloudinary");
                return cloudinaryService.uploadImage(file);
            }
            log.info("Cloudinary disabled or not configured; saving image to local '{}' folder", uploadDir);
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
            // Delete from Cloudinary if applicable
            if ((cloudinaryService != null && cloudinaryService.isConfigured()) && imageUrl.contains("res.cloudinary.com")) {
                log.info("Deleting product image from Cloudinary: {}", imageUrl);
                cloudinaryService.deleteByUrl(imageUrl);
                return;
            }
            log.info("Deleting local product image: {}", imageUrl);
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
        // Deduplicate by size to avoid UNIQUE (MaSP, MaSize)
        Set<Integer> seenSizes = new HashSet<>();
        for (SanPhamDTO.BienTheDTO btDTO : bienTheDTOs) {
            if (btDTO.getMaSize() == null || btDTO.getGiaBan() == null) {
                // Bỏ qua biến thể không hợp lệ
                continue;
            }
            if (seenSizes.contains(btDTO.getMaSize())) {
                // Skip duplicate size for the same product
                log.debug("Skip duplicate size {} for product {}", btDTO.getMaSize(), maSP);
                continue;
            }
            seenSizes.add(btDTO.getMaSize());
            BienTheSanPham bienThe = new BienTheSanPham();
            bienThe.setMaSP(maSP);
            bienThe.setMaSize(btDTO.getMaSize());
            bienThe.setGiaBan(btDTO.getGiaBan());
            // Nếu checkbox không tick, binder không gửi tham số -> null. Mặc định 0
            bienThe.setTrangThai(safeStatus(btDTO.getTrangThai(), (byte) 0));
            
            bienTheRepository.save(bienThe);
        }
    }

    /**
     * Trả về trạng thái hợp lệ (0 hoặc 1) khi đầu vào có thể null hoặc khác 0/1.
     */
    private byte safeStatus(Byte input, byte defaultValue) {
        if (input == null) return defaultValue;
        return (input == 1) ? (byte) 1 : (byte) 0;
    }
    
    /**
     * Kiểm tra xem sản phẩm có đang được sử dụng trong đơn hàng không
     */
    private boolean isProductUsedInOrders(Integer maSP) {
        try {
            // Kiểm tra thông qua biến thể sản phẩm trong CTDonHang
            List<BienTheSanPham> bienThes = bienTheRepository.findByMaSP(maSP);
            for (BienTheSanPham bt : bienThes) {
                // Nếu có CTDonHang tham chiếu đến biến thể này thì không được xóa
                if (isVariantUsedInOrders(bt.getMaBT())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.warn("Error checking product usage in orders: {}", e.getMessage());
            // Nếu có lỗi thì coi như đang được sử dụng để an toàn
            return true;
        }
    }
    
    /**
     * Kiểm tra xem biến thể có đang được sử dụng trong đơn hàng không
     */
    private boolean isVariantUsedInOrders(Integer maBT) {
        try {
            // Sử dụng EntityManager để kiểm tra CTDonHang
            Long count = entityManager.createQuery(
                "SELECT COUNT(ct) FROM CTDonHang ct WHERE ct.maBT = :maBT", 
                Long.class)
                .setParameter("maBT", maBT)
                .getSingleResult();
            
            return count > 0;
        } catch (Exception e) {
            log.warn("Error checking variant usage in orders: {}", e.getMessage());
            // Nếu không có entity CTDonHang hoặc có lỗi, coi như không được sử dụng
            // để không chặn việc xóa
            return false;
        }
    }
    
    /**
     * Xóa các khuyến mãi sản phẩm liên quan
     */
    private void deletePromotionProducts(Integer maSP) {
        try {
            if (khuyenMaiSanPhamRepository != null) {
                khuyenMaiSanPhamRepository.deleteByMaSP(maSP);
                log.info("Deleted promotion products for product: {}", maSP);
            } else {
                log.info("KhuyenMaiSanPhamRepository not available, skip deleting promotion products");
            }
        } catch (Exception e) {
            log.warn("Error deleting promotion products: {}", e.getMessage());
            throw e;
        }
    }
}