package com.alotra.web.service.admin;

import com.alotra.web.dto.admin.CategoryAdminViewDTO;
import com.alotra.web.entity.DanhMucSanPham;
import java.util.List;
import java.util.Optional;

public interface AdminCategoryService {
    List<CategoryAdminViewDTO> getAllCategories();
    Optional<DanhMucSanPham> getCategoryById(Integer maDM);
    DanhMucSanPham saveCategory(DanhMucSanPham danhMuc);
    void softDeleteCategory(Integer maDM);
    DanhMucSanPham restoreCategory(Integer maDM);
    void deleteCategoryPermanently(Integer maDM); // <-- Thêm mới
}