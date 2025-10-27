package com.alotra.web.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Lớp này sẽ tự động thêm các thuộc tính chung vào Model
 * cho tất cả các Controller trong gói "com.alotra.web.controller.admin".
 */
@ControllerAdvice(basePackages = "com.alotra.web.controller.admin")
public class GlobalAdminControllerAdvice {

    /**
     * Tự động thêm thuộc tính "requestURI" vào Model cho mọi request.
     * Thuộc tính này chứa đường dẫn URI của request hiện tại.
     *
     * @param request HttpServletRequest được tiêm tự động
     * @return String là URI của request
     */
    @ModelAttribute("requestURI")
    public String addRequestUriToModel(HttpServletRequest request) {
        return request.getRequestURI();
    }
}