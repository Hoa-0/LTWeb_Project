package com.alotra.web.security;

import com.alotra.web.entity.NhanVien;
import com.alotra.web.repository.NhanVienRepository; // Giả sử đã có NhanVienRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomEmployeeDetailsService implements UserDetailsService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Tìm kiếm nhân viên theo username hoặc email
        Optional<NhanVien> nhanVienOpt = nhanVienRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (nhanVienOpt.isEmpty()) {
            throw new UsernameNotFoundException("Không tìm thấy nhân viên với username hoặc email: " + usernameOrEmail);
        }

        NhanVien nhanVien = nhanVienOpt.get();

        // Kiểm tra tài khoản có bị xóa mềm hoặc vô hiệu hóa không
        boolean accountNonLocked = nhanVien.getDeletedAt() == null;
        boolean enabled = nhanVien.isActive(); // Dùng helper method isActive() đã có

        // Tạo danh sách quyền
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (nhanVien.isAdmin()) { // Dùng helper method isAdmin() đã có (Kiểm tra VaiTro == 1)
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        // Thêm các quyền khác nếu cần (ví dụ: ROLE_MANAGER, ROLE_STAFF...)
        // if (nhanVien.isManager()) {
        //     authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        // }
        // if (nhanVien.isStaff()) {
        //     authorities.add(new SimpleGrantedAuthority("ROLE_STAFF"));
        // }


        return new User(
                nhanVien.getUsername(), // Phải là username dùng để đăng nhập
                nhanVien.getMatKhauHash(), // Mật khẩu đã hash
                enabled, // Tài khoản có được kích hoạt không (TrangThai == 1)
                true, // accountNonExpired - Tạm thời để true
                true, // credentialsNonExpired - Tạm thời để true
                accountNonLocked, // accountNonLocked - Kiểm tra DeletedAt
                authorities // Danh sách quyền
        );
    }
}