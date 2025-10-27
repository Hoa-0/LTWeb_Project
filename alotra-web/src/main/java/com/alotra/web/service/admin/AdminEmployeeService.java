package com.alotra.web.service.admin;

import com.alotra.web.dto.admin.EmployeeAdminViewDTO;
import com.alotra.web.entity.NhanVien;

import java.util.List;
import java.util.Optional; // Thêm Optional

public interface AdminEmployeeService {
    List<EmployeeAdminViewDTO> getAllEmployees();
    NhanVien toggleEmployeeStatus(Integer maNV);
    NhanVien changeEmployeeRole(Integer maNV, Byte newRole);
    Optional<NhanVien> getEmployeeById(Integer maNV); // Lấy entity để sửa
    NhanVien updateEmployeeInfo(NhanVien nhanVien); // Nhận entity đã cập nhật từ form
    void softDeleteEmployee(Integer maNV); // Xóa mềm
    NhanVien restoreEmployee(Integer maNV); // <-- Thêm mới
    void deleteEmployeePermanently(Integer maNV); // <-- Thêm mới
}