package com.alotra.web.service;

import com.alotra.web.entity.NhanVien;
import com.alotra.web.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NhanVienService {
    
    private final NhanVienRepository nhanVienRepository;
    
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
                // Preserve current hash from DB when not changing password
                NhanVien current = repo.findById(nv.getId()).orElseThrow();
                nv.setPasswordHash(current.getPasswordHash());
            }
        }
        return repo.save(nv);
    }

    public void deleteById(Integer id) { repo.deleteById(id); }

    public NhanVien findByUsername(String u){ return repo.findByUsername(u);}    
    public NhanVien findByEmail(String e){ return repo.findByEmail(e);}    
    public NhanVien findByPhone(String p){ return repo.findByPhone(p);}   

    // Search with optional filters
    public List<NhanVien> search(String kw, Integer role, Integer status) {
        if (kw != null && kw.isBlank()) kw = null;
        return repo.search(kw, role, status);
    }

    // Reset password to a random temporary one; returns plaintext temp password
    public String resetPassword(Integer id) {
        NhanVien nv = repo.findById(id).orElseThrow();
        String temp = generateTempPassword(10);
        nv.setPasswordHash(encoder.encode(temp));
        repo.save(nv);
        return temp;
    }

    private String generateTempPassword(int len) {
        final String dict = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789@#$%"; // avoid confusing chars
        SecureRandom r = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) sb.append(dict.charAt(r.nextInt(dict.length())));
        return sb.toString();
    }

    // Soft delete to trash: mark DeletedAt and lock account
    public void softDeleteToTrash(Integer id) {
        repo.findById(id).ifPresent(nv -> {
            nv.setDeletedAt(LocalDateTime.now());
            nv.setStatus(0);
            repo.save(nv);
        });
    }

    public void restoreFromTrash(Integer id) {
        repo.findById(id).ifPresent(nv -> {
            nv.setDeletedAt(null);
            repo.save(nv);
        });
    }
}