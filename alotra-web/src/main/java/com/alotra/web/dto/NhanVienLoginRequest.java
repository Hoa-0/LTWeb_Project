package com.alotra.web.dto;

import lombok.Data;

@Data
public class NhanVienLoginRequest {
    private String usernameOrEmail;
    private String password;
    
    // Alternative getter for compatibility
    public String getUsername() {
        return usernameOrEmail;
    }
}