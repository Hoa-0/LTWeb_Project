package com.alotra.web.service.admin;

import com.alotra.web.dto.admin.ProductAdminViewDTO;
import com.alotra.web.entity.SanPham;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminProductService {
//    List<ProductAdminViewDTO> getAllProducts();
 // Thêm Pageable để hỗ trợ phân trang
//	Page<ProductAdminViewDTO> getAllProducts(Pageable pageable);
	
	// Cập nhật: Thêm tham số searchKeyword
	Page<ProductAdminViewDTO> getAllProducts(String searchKeyword, Pageable pageable);
    
    
    Optional<SanPham> getProductById(Integer maSP);
    SanPham saveProduct(SanPham sanPham); // Dùng cho cả Add và Update
    SanPham toggleProductStatus(Integer maSP);
    void softDeleteProduct(Integer maSP);
    SanPham restoreProduct(Integer maSP);
    void deleteProductPermanently(Integer maSP);
}