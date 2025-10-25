package com.alotra.web.service;

import com.alotra.web.entity.NhanVien;
import com.alotra.web.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class NhanVienService {
    
    private final NhanVienRepository nhanVienRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Xác thực thông tin đăng nhập của nhân viên
     * @param usernameOrEmail tên đăng nhập hoặc email
     * @param rawPassword mật khẩu gốc (chưa mã hóa)
     * @return Optional<NhanVien> nếu thông tin đăng nhập đúng
     */
    public Optional<NhanVien> authenticateNhanVien(String usernameOrEmail, String rawPassword) {
        try {
            log.info("Attempting to authenticate employee with username/email: {}", usernameOrEmail);
            
            // Tìm nhân viên theo username hoặc email
            Optional<NhanVien> nhanVienOpt = nhanVienRepository.findByUsernameOrEmail(usernameOrEmail);
            
            if (nhanVienOpt.isEmpty()) {
                log.warn("Employee not found with username/email: {}", usernameOrEmail);
                return Optional.empty();
            }
            
            NhanVien nhanVien = nhanVienOpt.get();
            log.debug("Found employee: {}, Status: {}, DeletedAt: {}", nhanVien.getUsername(), nhanVien.getTrangThai(), nhanVien.getDeletedAt());
            
            // Kiểm tra trạng thái tài khoản
            if (!nhanVien.isActive()) {
                log.warn("Employee account is inactive: {}", usernameOrEmail);
                return Optional.empty();
            }
            
            // So sánh mật khẩu (trực tiếp theo yêu cầu - không sử dụng hash)
            log.debug("Comparing passwords - Input: '{}', Stored: '{}'", rawPassword, nhanVien.getMatKhauHash());
            if (rawPassword.equals(nhanVien.getMatKhauHash())) {
                log.info("Authentication successful for employee: {}", usernameOrEmail);
                return Optional.of(nhanVien);
            } else {
                log.warn("Invalid password for employee: {}. Expected: '{}', Got: '{}'", 
                    usernameOrEmail, nhanVien.getMatKhauHash(), rawPassword);
                return Optional.empty();
            }
            
        } catch (Exception e) {
            log.error("Error during authentication for employee: {}", usernameOrEmail, e);
            return Optional.empty();
        }
    }
    
    /**
     * Tìm nhân viên theo username
     */
    public Optional<NhanVien> findByUsername(String username) {
        return nhanVienRepository.findByUsernameIgnoreCase(username);
    }
    
    /**
     * Tìm nhân viên theo email
     */
    public Optional<NhanVien> findByEmail(String email) {
        return nhanVienRepository.findByEmailIgnoreCase(email);
    }
    
    /**
     * Tìm nhân viên theo ID
     */
    public Optional<NhanVien> findById(Integer id) {
        return nhanVienRepository.findById(id);
    }
    
    /**
     * Kiểm tra username đã tồn tại chưa
     */
    public boolean existsByUsername(String username) {
        return nhanVienRepository.existsByUsernameIgnoreCase(username);
    }
    
    /**
     * Kiểm tra email đã tồn tại chưa
     */
    public boolean existsByEmail(String email) {
        return nhanVienRepository.existsByEmailIgnoreCase(email);
    }
}