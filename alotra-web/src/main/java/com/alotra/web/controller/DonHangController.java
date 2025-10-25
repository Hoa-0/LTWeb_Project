package com.alotra.web.controller;

import com.alotra.web.entity.CTDonHang;
import com.alotra.web.entity.CTDonHangTopping;
import com.alotra.web.entity.DonHang;
import com.alotra.web.repository.CTDonHangRepository;
import com.alotra.web.repository.CTDonHangToppingRepository;
import com.alotra.web.repository.DonHangRepository;
import com.alotra.web.service.OrderStatusResolver;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Controller xử lý quản lý đơn hàng cho nhân viên
 */
@Controller
@RequestMapping("/NhanVien/donhang")
@RequiredArgsConstructor
@Slf4j
public class DonHangController {

    private final DonHangRepository donHangRepository;
    private final CTDonHangRepository ctDonHangRepository;
    private final CTDonHangToppingRepository ctDonHangToppingRepository;
    private final OrderStatusResolver statusResolver;

    @GetMapping("")
    public String listDonHang(
            Model model,
            HttpSession session,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String trangThai,
            @RequestParam(required = false) String paymentStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        // Paging & sorting
        Pageable pageable = PageRequest.of(page, Math.max(1, Math.min(size, 200)), Sort.by(Sort.Direction.DESC, "ngayLap"));
        
        // Build dynamic specification. Do NOT default-date-filter unless user provided from/to.
        Specification<DonHang> spec = (root, q, cb) -> cb.conjunction();
        if (from != null && to != null) {
            LocalDateTime start = from.atStartOfDay();
            LocalDateTime end = to.atTime(LocalTime.MAX);
            spec = spec.and((root, q, cb2) -> cb2.between(root.get("ngayLap"), start, end));
        } else if (from != null) {
            LocalDateTime start = from.atStartOfDay();
            spec = spec.and((root, q, cb2) -> cb2.greaterThanOrEqualTo(root.get("ngayLap"), start));
        } else if (to != null) {
            LocalDateTime end = to.atTime(LocalTime.MAX);
            spec = spec.and((root, q, cb2) -> cb2.lessThanOrEqualTo(root.get("ngayLap"), end));
        }
        if (trangThai != null && !trangThai.isBlank()) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("trangThaiDonHang"), trangThai));
        }
        if (paymentStatus != null && !paymentStatus.isBlank()) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("paymentStatus"), paymentStatus));
        }

        Page<DonHang> pageData = donHangRepository.findAll(spec, pageable);

        model.addAttribute("pageTitle", "Quản lý đơn hàng");
        model.addAttribute("orders", pageData);
    model.addAttribute("from", from);
    model.addAttribute("to", to);
        model.addAttribute("trangThai", trangThai);
        model.addAttribute("paymentStatus", paymentStatus);

        // Simple stats for the current filter range
        model.addAttribute("statsTotal", pageData.getTotalElements());
        model.addAttribute("statsDelivered", pageData.getContent().stream().filter(d -> "DaGiao".equalsIgnoreCase(d.getTrangThaiDonHang())).count());
        model.addAttribute("statsProcessing", pageData.getContent().stream().filter(d -> Objects.equals("DangXuLy", d.getTrangThaiDonHang()) || Objects.equals("ChoXuLy", d.getTrangThaiDonHang())).count());
        model.addAttribute("statsCancelled", pageData.getContent().stream().filter(d -> Objects.equals("DaHuy", d.getTrangThaiDonHang())).count());

        return "nhanvien/donhang/list";
    }

    @GetMapping("/chi-tiet/{id}")
    public String chiTietDonHang(@PathVariable("id") Integer id, Model model, HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        DonHang dh = donHangRepository.findById(id).orElse(null);
        if (dh == null) {
            return "redirect:/NhanVien/donhang";
        }

        List<CTDonHang> lines = ctDonHangRepository.findByDonHang_MaDHOrderByMaCTAsc(id);
        Map<Integer, List<CTDonHangTopping>> toppingsByLine = new HashMap<>();
        for (CTDonHang ct : lines) {
            toppingsByLine.put(ct.getMaCT(), ctDonHangToppingRepository.findByIdMaCTOrderByIdMaToppingAsc(ct.getMaCT()));
        }

        model.addAttribute("pageTitle", "Chi tiết đơn hàng #" + id);
        model.addAttribute("order", dh);
        model.addAttribute("lines", lines);
        model.addAttribute("toppingsByLine", toppingsByLine);
        // Flags for template button states across different status vocabularies
        model.addAttribute("isDelivered", statusResolver.isDelivered(dh.getTrangThaiDonHang()));
        model.addAttribute("isCancelled", statusResolver.isCancelled(dh.getTrangThaiDonHang()));
        return "nhanvien/donhang/detail";
    }

    @GetMapping("/in/{id}")
    public String inDon(@PathVariable("id") Integer id, Model model, HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }
        DonHang dh = donHangRepository.findById(id).orElse(null);
        if (dh == null) {
            return "redirect:/NhanVien/donhang";
        }
        List<CTDonHang> lines = ctDonHangRepository.findByDonHang_MaDHOrderByMaCTAsc(id);
        Map<Integer, List<CTDonHangTopping>> toppingsByLine = new HashMap<>();
        for (CTDonHang ct : lines) {
            toppingsByLine.put(ct.getMaCT(), ctDonHangToppingRepository.findByIdMaCTOrderByIdMaToppingAsc(ct.getMaCT()));
        }
        model.addAttribute("order", dh);
        model.addAttribute("lines", lines);
        model.addAttribute("toppingsByLine", toppingsByLine);
        return "nhanvien/donhang/print";
    }

    @PostMapping("/action/{id}")
    public String thucHienHanhDong(
            @PathVariable("id") Integer id,
            @RequestParam("action") String action,
        HttpSession session,
        RedirectAttributes redirect
    ) {
        if (session.getAttribute("loggedInNhanVien") == null) {
            return "redirect:/NhanVien/login";
        }

        Optional<DonHang> opt = donHangRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/NhanVien/donhang";
        }
        DonHang dh = opt.get();

        String current = Objects.toString(dh.getTrangThaiDonHang(), "");
        LocalDateTime now = LocalDateTime.now();

        try {
            switch (action) {
                case "confirm": {
                    // Theo yêu cầu: xác nhận => chuyển sang trạng thái giao thành công (delivered)
                    String next = statusResolver.resolveDeliveredStatus();
                    if (next == null) {
                        redirect.addFlashAttribute("error", "Không tìm thấy trạng thái giao thành công hợp lệ theo cấu hình DB.");
                        return "redirect:/NhanVien/donhang/chi-tiet/" + id;
                    }
                    dh.setTrangThaiDonHang(next);
                    // gán nhân viên xử lý nếu có
                    Object nv = session.getAttribute("nhanVienId");
                    if (nv instanceof Integer nvId) dh.setMaNV(nvId);
                    break;
                }
                case "progress": {
                    String next = resolveNextProgress(current, statusResolver.getAllowedTrangThaiDonHang());
                    if (next == null) {
                        redirect.addFlashAttribute("warn", "Không thể cập nhật tiến trình từ trạng thái hiện tại.");
                        return "redirect:/NhanVien/donhang/chi-tiet/" + id;
                    }
                    dh.setTrangThaiDonHang(next);
                    break;
                }
                case "cancel": {
                    String canceled = statusResolver.resolveCancelledStatus();
                    if (canceled == null) {
                        redirect.addFlashAttribute("error", "Không tìm thấy trạng thái hủy hợp lệ theo cấu hình DB.");
                        return "redirect:/NhanVien/donhang/chi-tiet/" + id;
                    }
                    dh.setTrangThaiDonHang(canceled);
                    break;
                }
                case "markPaid":
                    dh.setPaymentStatus(statusResolver.resolvePaymentStatus(true));
                    dh.setPaidAt(now);
                    break;
                case "markUnpaid":
                    dh.setPaymentStatus(statusResolver.resolvePaymentStatus(false));
                    dh.setPaidAt(null);
                    break;
                default:
                    // no-op
            }

            donHangRepository.saveAndFlush(dh);
            redirect.addFlashAttribute("success", "Cập nhật đơn hàng thành công.");
        } catch (DataIntegrityViolationException ex) {
            log.warn("Update DonHang failed by constraint: {}", ex.getMessage());
            redirect.addFlashAttribute("error", "Cập nhật thất bại do không đúng trạng thái hợp lệ theo CSDL.");
        } catch (Exception e) {
            log.error("Unexpected error updating order {} action {}", id, action, e);
            redirect.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật đơn hàng.");
        }
        return "redirect:/NhanVien/donhang/chi-tiet/" + id;
    }

    private static String pickAllowed(List<String> allowed, List<String> candidates) {
        if (allowed != null && !allowed.isEmpty()) {
            for (String c : candidates) {
                for (String a : allowed) {
                    if (a.equals(c)) return a; // exact
                }
            }
            for (String c : candidates) {
                for (String a : allowed) {
                    if (a.equalsIgnoreCase(c)) return a; // keep DB casing
                }
            }
            return null;
        }
        return null;
    }

    private static String resolveNextProgress(String current, List<String> allowed) {
        if (current == null) current = "";
        String cur = current.trim();

        List<String> processingSyn = List.of("DangXuLy", "DANG_XU_LY", "Processing", "PROCESSING", "Confirmed", "CONFIRMED", "ChoXuLy", "CHO_XU_LY");
        List<String> shippingSyn = List.of("DangGiao", "DANG_GIAO", "Shipping", "SHIPPING", "OnDelivery");
        List<String> deliveredSyn = List.of("DaGiao", "DA_GIAO", "Delivered", "DELIVERED");

        // if processing -> shipping
        if (matchesAny(cur, processingSyn)) {
            return pickAllowed(allowed, shippingSyn);
        }
        // if shipping -> delivered
        if (matchesAny(cur, shippingSyn)) {
            return pickAllowed(allowed, deliveredSyn);
        }
        return null;
    }

    private static boolean matchesAny(String value, List<String> cands) {
        for (String c : cands) {
            if (value.equals(c) || value.equalsIgnoreCase(c)) return true;
        }
        return false;
    }
}