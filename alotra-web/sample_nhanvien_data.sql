-- Dữ liệu mẫu để test đăng nhập nhân viên

-- Thêm nhân viên Admin
INSERT INTO NhanVien (Username, MatKhauHash, Email, TenNV, VaiTro, SoDienThoai, TrangThai, AnhDaiDien, DeletedAt)
VALUES ('admin', '123456', 'admin@alotra.com', N'Nguyễn Văn Admin', 1, '0123456789', 1, NULL, NULL);

-- Thêm nhân viên Manager
INSERT INTO NhanVien (Username, MatKhauHash, Email, TenNV, VaiTro, SoDienThoai, TrangThai, AnhDaiDien, DeletedAt)
VALUES ('manager', '123456', 'manager@alotra.com', N'Trần Thị Manager', 2, '0987654321', 1, NULL, NULL);

-- Thêm nhân viên Staff
INSERT INTO NhanVien (Username, MatKhauHash, Email, TenNV, VaiTro, SoDienThoai, TrangThai, AnhDaiDien, DeletedAt)
VALUES ('staff', '123456', 'staff@alotra.com', N'Lê Văn Staff', 3, '0111222333', 1, NULL, NULL);

-- Thêm nhân viên không hoạt động (để test)
INSERT INTO NhanVien (Username, MatKhauHash, Email, TenNV, VaiTro, SoDienThoai, TrangThai, AnhDaiDien, DeletedAt)
VALUES ('inactive', '123456', 'inactive@alotra.com', N'Phạm Thị Inactive', 3, '0444555666', 0, NULL, NULL);

/*
Thông tin đăng nhập test:
1. Admin: username/email: admin / admin@alotra.com, password: 123456
2. Manager: username/email: manager / manager@alotra.com, password: 123456  
3. Staff: username/email: staff / staff@alotra.com, password: 123456
4. Inactive (không thể đăng nhập): username: inactive, password: 123456
*/