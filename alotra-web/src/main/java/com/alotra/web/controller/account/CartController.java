package com.alotra.web.controller.account;

import com.alotra.web.entity.BienTheSanPham;
import com.alotra.web.entity.GioHangCT;
import com.alotra.web.entity.KhachHang;
import com.alotra.web.entity.SanPham;
import com.alotra.web.security.KhachHangUserDetails;
import com.alotra.web.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String viewCart(@AuthenticationPrincipal KhachHangUserDetails principal, Model model,
                           @RequestParam(value = "msg", required = false) String msg,
                           @RequestParam(value = "error", required = false) String error) {
        KhachHang kh = principal.getKhachHang();
        List<GioHangCT> items = cartService.listItems(kh);
        model.addAttribute("pageTitle", "Giỏ hàng");
        model.addAttribute("items", items);
        // toppings per item and catalogs for UI
        model.addAttribute("itemToppingsMap", cartService.getToppingsForItems(items));
        model.addAttribute("toppingsCatalog", cartService.listActiveToppings());
        // Build a simple map: itemId -> (toppingId -> qty) for default values
        Map<Integer, Map<Integer,Integer>> qtyMap = new HashMap<>();
        cartService.getToppingsForItems(items).forEach((itemId, list) -> {
            Map<Integer,Integer> inner = new HashMap<>();
            // TODO: Implement proper topping casting when getToppingsForItems is implemented
            // list.forEach(t -> inner.put(t.getTopping().getId(), t.getQuantity()));
            qtyMap.put(itemId, inner);
        });
        model.addAttribute("itemTopQtyMap", qtyMap);
        // New: variants per item to allow changing size
        Map<Integer, List<BienTheSanPham>> itemVariantsMap = new HashMap<>();
        for (GioHangCT it : items) {
            SanPham product = it.getBienTheSanPham() != null ? it.getBienTheSanPham().getSanPham() : null;
            itemVariantsMap.put(it.getId(), cartService.listVariantsForProduct(product));
        }
        model.addAttribute("itemVariantsMap", itemVariantsMap);
        model.addAttribute("total", cartService.calcTotal(items));
        // Backward compatibility: map legacy ?msg= to template's "message"
        if (msg != null) model.addAttribute("message", msg);
        if (error != null) model.addAttribute("error", error);
        return "cart/cart";
    }

    @PostMapping("/update")
    public String updateQty(@AuthenticationPrincipal KhachHangUserDetails principal,
                            @RequestParam("itemId") Integer itemId,
                            @RequestParam("qty") Integer qty,
                            RedirectAttributes ra) {
        try {
            cartService.updateQuantity(principal.getKhachHang(), itemId, qty);
            // Optional: show success
            ra.addFlashAttribute("message", "Đã cập nhật số lượng.");
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String remove(@AuthenticationPrincipal KhachHangUserDetails principal,
                         @PathVariable Integer id,
                         RedirectAttributes ra) {
        try {
            cartService.removeItem(principal.getKhachHang(), id);
            ra.addFlashAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng.");
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/cart";
    }

    @PostMapping("/item/{id}/toppings")
    public String updateItemToppings(@AuthenticationPrincipal KhachHangUserDetails principal,
                                     @PathVariable("id") Integer itemId,
                                     @RequestParam MultiValueMap<String, String> params,
                                     RedirectAttributes ra) {
        try {
            Map<Integer,Integer> map = new HashMap<>();
            for (String key : params.keySet()) {
                if (key.startsWith("toppings[") && key.endsWith("]")) {
                    String idStr = key.substring(9, key.length() - 1);
                    try {
                        Integer tid = Integer.valueOf(idStr);
                        String raw = params.getFirst(key);
                        Integer q;
                        try { q = (raw == null || raw.isBlank()) ? 0 : Integer.valueOf(raw); }
                        catch (NumberFormatException nfe) { q = 0; }
                        // Include even when q == 0 so the service can delete it
                        map.put(tid, Math.max(0, q));
                    } catch (NumberFormatException ignored) {}
                }
            }
            cartService.updateToppings(principal.getKhachHang(), itemId, map);
            ra.addFlashAttribute("message", "Đã cập nhật topping.");
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/cart";
    }

    // New: change size (variant) of a cart item
    @PostMapping("/item/{id}/variant")
    public String changeVariant(@AuthenticationPrincipal KhachHangUserDetails principal,
                                @PathVariable("id") Integer itemId,
                                @RequestParam("variantId") Integer newVariantId,
                                RedirectAttributes ra) {
        try {
            cartService.changeVariant(principal.getKhachHang(), itemId, newVariantId);
            ra.addFlashAttribute("message", "Đã cập nhật kích cỡ sản phẩm.");
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/cart";
    }
}
