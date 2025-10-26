package com.alotra.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NhanVienLoginRequest {
    
    @NotBlank(message = "Tên đăng nhập hoặc email không được để trống")
    @Size(max = 255, message = "Tên đăng nhập hoặc email không được vượt quá 255 ký tự")
    private String usernameOrEmail;
    
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 255, message = "Mật khẩu phải từ 6 đến 255 ký tự")
    private String password;
    
    private boolean rememberMe = false;
}