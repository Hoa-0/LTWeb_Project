package com.alotra.web.security;

import com.alotra.web.entity.User;
import com.alotra.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // username ở đây chính là email người dùng nhập vào ô "username"
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user với email: " + username));
        return CustomUserDetails.fromUser(u);
    }
}