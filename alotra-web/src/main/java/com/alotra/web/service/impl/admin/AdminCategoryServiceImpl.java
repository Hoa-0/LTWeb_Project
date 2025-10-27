package com.alotra.web.service.impl.admin;

import com.alotra.web.dto.admin.CategoryAdminViewDTO;
import com.alotra.web.entity.DanhMucSanPham;
import com.alotra.web.repository.DanhMucSanPhamRepository;
import com.alotra.web.service.admin.AdminCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

    @Autowired
    private DanhMucSanPhamRepository danhMucRepository;

    @Override
    public List<CategoryAdminViewDTO> getAllCategories() {
        // Sắp xếp theo tên
        return danhMucRepository.findAll().stream()
                // Có thể sắp xếp ở đây nếu repo không sort: .sorted(Comparator.comparing(DanhMucSanPham::getTenDM))
                .map(CategoryAdminViewDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DanhMucSanPham> getCategoryById(Integer maDM) {
        return danhMucRepository.findById(maDM);
    }

    @Override
    @Transactional
    public DanhMucSanPham saveCategory(DanhMucSanPham danhMuc) {
        // Kiểm tra trùng tên (có thể làm ở Controller hoặc Service)
        // Optional<DanhMucSanPham> existing = danhMucRepository.findByTenDMIgnoreCase(danhMuc.getTenDM());
        // if (existing.isPresent() && !existing.get().getMaDM().equals(danhMuc.getMaDM())) {
        //     throw new IllegalArgumentException("Tên danh mục đã tồn tại.");
        // }

        // Nếu là thêm mới, đảm bảo DeletedAt là null
        if (danhMuc.getMaDM() == null) {
            danhMuc.setDeletedAt(null);
        } else {
            // Nếu là update, chỉ cập nhật tên và mô tả
            DanhMucSanPham existingCat = findByIdOrThrow(danhMuc.getMaDM());
            existingCat.setTenDM(danhMuc.getTenDM());
            existingCat.setMoTa(danhMuc.getMoTa());
            danhMuc = existingCat; // Lưu entity gốc đã cập nhật
        }
        return danhMucRepository.save(danhMuc);
    }

    @Override
    @Transactional
    public void softDeleteCategory(Integer maDM) {
        DanhMucSanPham danhMuc = findByIdOrThrow(maDM);
        // Kiểm tra xem có sản phẩm nào thuộc danh mục này không trước khi xóa? (Tùy yêu cầu)
        // if (!danhMuc.getSanPhams().isEmpty()) { // Cần EAGER fetch hoặc query riêng
        //     throw new IllegalStateException("Không thể xóa danh mục vì còn sản phẩm thuộc danh mục này.");
        // }
        danhMuc.setDeletedAt(LocalDateTime.now());
        danhMucRepository.save(danhMuc);
    }

    @Override
    @Transactional
    public DanhMucSanPham restoreCategory(Integer maDM) {
        DanhMucSanPham danhMuc = findByIdOrThrow(maDM);
        if (danhMuc.getDeletedAt() != null) {
            danhMuc.setDeletedAt(null);
            return danhMucRepository.save(danhMuc);
        }
        return danhMuc;
    }

 // ... (getAllCategories, getCategoryById, saveCategory, softDeleteCategory, restoreCategory giữ nguyên) ...

    @Override
    @Transactional
    public void deleteCategoryPermanently(Integer maDM) { // <-- Thêm mới
        DanhMucSanPham danhMuc = findByIdOrThrow(maDM);
        // Kiểm tra an toàn: Chỉ xóa vĩnh viễn nếu đã xóa mềm
        if (danhMuc.getDeletedAt() == null) {
             throw new IllegalStateException("Danh mục chưa bị xóa tạm thời, không thể xóa vĩnh viễn.");
        }
        // !!! Cảnh báo: Cần kiểm tra ràng buộc khóa ngoại (SanPham) trước khi xóa !!!
        // Nếu dùng @OneToMany(mappedBy = "danhMuc") trong DanhMucSanPham và cascade không phải là REMOVE/ALL
        // thì cần đảm bảo không còn SanPham nào tham chiếu đến maDM này.
        // Cách 1: Load danh sách sản phẩm (có thể gây tốn tài nguyên nếu nhiều)
        // danhMucRepository.findById(maDM).ifPresent(dm -> { // Fetch lại để có thể load collection
        //     if (dm.getSanPhams() != null && !dm.getSanPhams().isEmpty()) {
        //         throw new IllegalStateException("Không thể xóa vĩnh viễn danh mục vì còn sản phẩm thuộc danh mục này.");
        //     }
        // });
        // Cách 2: Tạo query trong SanPhamRepository để kiểm tra count
        // if (sanPhamRepository.countByMaDM(maDM) > 0) {
        //      throw new IllegalStateException("Không thể xóa vĩnh viễn danh mục vì còn sản phẩm thuộc danh mục này.");
        // }

        // Nếu kiểm tra OK thì mới xóa
        danhMucRepository.delete(danhMuc);
    }


    
    // Helper method
    private DanhMucSanPham findByIdOrThrow(Integer maDM) {
        return danhMucRepository.findById(maDM)
               .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy danh mục với Mã: " + maDM));
    }
}