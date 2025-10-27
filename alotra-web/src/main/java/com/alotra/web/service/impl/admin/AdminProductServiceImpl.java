package com.alotra.web.service.impl.admin;

import com.alotra.web.dto.admin.ProductAdminViewDTO;
import com.alotra.web.entity.SanPham;
import com.alotra.web.repository.SanPhamRepository; // Đã tạo ở CP3
import com.alotra.web.service.admin.AdminProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page; // Thêm import Page
import org.springframework.data.domain.Pageable; // Thêm import Pageable
import org.springframework.util.StringUtils; // Cần import StringUtils


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminProductServiceImpl implements AdminProductService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

//    @Override
//    public List<ProductAdminViewDTO> getAllProducts() {
//        // Lấy tất cả sản phẩm, join fetch DanhMucSanPham để tránh N+1 query khi lấy tên DM
//        // Sắp xếp theo MaSP giảm dần để sản phẩm mới lên đầu (tùy chọn)
//        List<SanPham> sanPhams = sanPhamRepository.findAllWithCategoryOrderByMaSPDesc(); // Cần thêm method này vào Repo
//        return sanPhams.stream()
//                .map(ProductAdminViewDTO::new)
//                .collect(Collectors.toList());
//    }
    
    @Override
    public Page<ProductAdminViewDTO> getAllProducts(String searchKeyword, Pageable pageable) {
        Page<SanPham> sanPhamsPage;

        // Nếu có keyword, thực hiện search
        if (StringUtils.hasText(searchKeyword)) {
            // Gọi phương thức search mới trong Repository
            sanPhamsPage = sanPhamRepository.searchByNameWithCategory(searchKeyword.trim(), pageable);
        } else {
            // Nếu không có keyword, lấy tất cả (vẫn dùng phân trang)
            sanPhamsPage = sanPhamRepository.findAllWithCategory(pageable);
        }
        
        // Map Page<SanPham> sang Page<ProductAdminViewDTO>
        return sanPhamsPage.map(ProductAdminViewDTO::new);
    }
    @Override
    public Optional<SanPham> getProductById(Integer maSP) {
        // Có thể cần join fetch DanhMuc khi lấy chi tiết để hiển thị form sửa
        return sanPhamRepository.findById(maSP);
    }

    @Override
    @Transactional
    public SanPham saveProduct(SanPham sanPham) {
        // Nếu là sản phẩm mới (maSP == null) thì set trạng thái mặc định là active
        if (sanPham.getMaSP() == null) {
            sanPham.setTrangThai((byte) 1); // Mặc định active khi thêm mới
            sanPham.setDeletedAt(null);
        } else {
            // Nếu là update, chỉ cập nhật các trường cần thiết từ form
            // Lấy entity gốc để tránh ghi đè các trường không có trong form (như DeletedAt)
            SanPham existingProduct = findByIdOrThrow(sanPham.getMaSP());
            existingProduct.setTenSP(sanPham.getTenSP());
            existingProduct.setMaDM(sanPham.getMaDM());
            existingProduct.setMoTa(sanPham.getMoTa());
            existingProduct.setUrlAnh(sanPham.getUrlAnh());
            // Không cập nhật TrangThai ở đây (dùng toggle)
            sanPham = existingProduct; // Gán lại để save đúng entity
        }
        // Thêm validation logic (vd: check trùng tên, URL ảnh hợp lệ...) nếu cần
        return sanPhamRepository.save(sanPham);
    }


    @Override
    @Transactional
    public SanPham toggleProductStatus(Integer maSP) {
        SanPham sanPham = findByIdOrThrow(maSP);
        byte currentStatus = sanPham.getTrangThai();
        sanPham.setTrangThai(currentStatus == 1 ? (byte) 0 : (byte) 1);
        return sanPhamRepository.save(sanPham);
    }

    @Override
    @Transactional
    public void softDeleteProduct(Integer maSP) {
        SanPham sanPham = findByIdOrThrow(maSP);
        sanPham.setDeletedAt(LocalDateTime.now());
        sanPham.setTrangThai((byte) 0);
        sanPhamRepository.save(sanPham);
    }

    @Override
    @Transactional
    public SanPham restoreProduct(Integer maSP) {
        SanPham sanPham = findByIdOrThrow(maSP);
        if (sanPham.getDeletedAt() != null) {
            sanPham.setDeletedAt(null);
            sanPham.setTrangThai((byte) 1); // Tự động active lại khi khôi phục
            return sanPhamRepository.save(sanPham);
        }
        return sanPham;
    }

    @Override
    @Transactional
    public void deleteProductPermanently(Integer maSP) {
        SanPham sanPham = findByIdOrThrow(maSP);
        if (sanPham.getDeletedAt() == null) {
             throw new IllegalStateException("Sản phẩm chưa bị xóa tạm thời, không thể xóa vĩnh viễn.");
        }
        // Thêm kiểm tra ràng buộc khóa ngoại (ví dụ: Biến thể sản phẩm) trước khi xóa nếu cần
        // if (!sanPham.getBienThes().isEmpty()) {
        //     throw new IllegalStateException("Không thể xóa sản phẩm vì còn biến thể liên quan.");
        // }
        sanPhamRepository.delete(sanPham);
    }

    // Helper method
    private SanPham findByIdOrThrow(Integer maSP) {
        return sanPhamRepository.findById(maSP)
               .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm với Mã: " + maSP));
    }
}