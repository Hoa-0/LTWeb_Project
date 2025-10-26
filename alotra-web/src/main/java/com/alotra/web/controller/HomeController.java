package com.alotra.web.controller;


import com.alotra.web.dto.ProductDTO; // Thêm import
import com.alotra.web.service.ProductService; // Thêm import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List; // Thêm import

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.alotra.web.dto.ProductDTO;
import com.alotra.web.entity.KhachHang;
import com.alotra.web.service.KhachHangService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import com.alotra.web.repository.SuKienKhuyenMaiRepository;
import com.alotra.web.repository.KhuyenMaiSanPhamRepository;
import com.alotra.web.entity.SuKienKhuyenMai;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.alotra.web.service.OtpService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ResponseBody;

// import org.springframework.web.bind.annotation.ResponseBody; // removed: avoid duplicate API mapping

@Controller
public class HomeController {
	 @Autowired
	    private ProductService productService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OtpService otpService;
    // Promotions
    @Autowired private SuKienKhuyenMaiRepository promoRepo;
    @Autowired private KhuyenMaiSanPhamRepository promoLinkRepo;
    @Autowired private com.alotra.web.service.CategoryService categoryService; // new

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("pageTitle", "AloTra - Trang Chủ");
        // Sau này bạn có thể thêm dữ liệu sản phẩm nổi bật vào đây
     // Lấy danh sách sản phẩm bán chạy từ service
        List<ProductDTO> bestSellers = productService.findBestSellers();

        // Đưa danh sách vào model với tên là "bestSellers" để HTML có thể dùng
        model.addAttribute("bestSellers", bestSellers);

        // Tin tức & Khuyến mãi: lấy tối đa 8 sự kiện đang hoạt động (không bị xóa)
        List<SuKienKhuyenMai> promos = promoRepo.findTop8ByStatusAndDeletedAtIsNullOrderByStartDateDesc(1);
        List<PromotionCard> cards = new ArrayList<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (SuKienKhuyenMai p : promos) {
            // Ưu tiên ảnh riêng của sự kiện; nếu trống thì lấy 1 ảnh SP áp dụng; fallback placeholder
            String eventImg = (p.getImageUrl() != null && !p.getImageUrl().isBlank()) ? p.getImageUrl() : null;
            String fallbackImg = promoLinkRepo.findByPromotion(p).stream()
                    .map(l -> l.getProduct())
                    .filter(pr -> pr != null && pr.getImageUrl() != null && !pr.getImageUrl().isBlank())
                    .map(pr -> pr.getImageUrl())
                    .findFirst()
                    .orElse(null);
            String imageUrl = eventImg != null ? eventImg : (fallbackImg != null ? fallbackImg : "/images/placeholder.png");
            String period = (p.getStartDate() != null ? df.format(p.getStartDate()) : "?") +
                    " - " + (p.getEndDate() != null ? df.format(p.getEndDate()) : "?");
            int views = p.getViews() == null ? 0 : p.getViews();
            cards.add(new PromotionCard(p.getId(), p.getName(), p.getDescription(), imageUrl, period, views));
        }
        model.addAttribute("promotions", cards);
        return "home/index"; // Trả về tên file template (home/index.html)
    }

    // Catalog page: categories + products with client-side AJAX filtering
    @GetMapping("/products")
    public String productsPage(@RequestParam(required = false) Integer categoryId, Model model) {
        model.addAttribute("pageTitle", "Sản Phẩm của AloTra");
        var categories = categoryService.findActive();
        model.addAttribute("categories", categories);
        // Initial list: by selected category when provided, else all
        List<ProductDTO> initial = productService.listByCategory(categoryId);
        model.addAttribute("products", initial);
        model.addAttribute("selectedCategoryId", categoryId);
        return "products/product_list"; // client renders grid and filters
    }

    // Note: JSON API for catalog is provided by CatalogApiController (/api/catalog/products)

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("pageTitle", "Về Chúng Tôi");
        return "about/about"; // Sẽ tạo trang này sau
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        model.addAttribute("pageTitle", "Liên Hệ AloTra");
        return "contact/contact"; // Sẽ tạo trang này sau
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("pageTitle", "Đăng Nhập");
        return "auth/login"; // Sẽ tạo trang này sau
    }

    @GetMapping("/policy")
    public String policyPage(Model model) {
        model.addAttribute("pageTitle", "Chính Sách");
        return "policy/policy"; // Sẽ tạo trang này sau
    }

    // Removed legacy /register and OTP endpoints to use RegistrationController two-step flow

    // Simple view-model for promotions on homepage
    public static class PromotionCard {
        public Integer id;
        public String title;
        public String description;
        public String imageUrl;
        public String periodText;
        public int views;
        public PromotionCard(Integer id, String title, String description, String imageUrl, String periodText, int views) {
            this.id = id; this.title = title; this.description = description; this.imageUrl = imageUrl; this.periodText = periodText; this.views = views;
        }
    }
}