	package com.alotra.service;

	import com.alotra.entity.*;
	import com.alotra.repository.*;
	import org.springframework.stereotype.Service;
	import org.springframework.transaction.annotation.Transactional;

	import java.math.BigDecimal;
	import java.util.*;
	import java.util.stream.Collectors;

	@Service
	public class CartService {
	    private final GioHangRepository cartRepo;
	    private final GioHangCTRepository itemRepo;
	    private final ProductVariantRepository variantRepo;
	    private final ProductRepository productRepo;
	    private final DonHangRepository orderRepo;
	    private final CTDonHangRepository orderLineRepo;
	    private final GioHangCTToppingRepository cartToppingRepo;
	    private final ToppingRepository toppingRepo;
	    private final CTDonHangToppingRepository orderToppingRepo;
	    private final KhuyenMaiSanPhamRepository promoRepo; // new

	    public CartService(GioHangRepository cartRepo, GioHangCTRepository itemRepo,
	                       ProductVariantRepository variantRepo, ProductRepository productRepo,
	                       DonHangRepository orderRepo, CTDonHangRepository orderLineRepo,
	                       GioHangCTToppingRepository cartToppingRepo, ToppingRepository toppingRepo,
	                       CTDonHangToppingRepository orderToppingRepo,
	                       KhuyenMaiSanPhamRepository promoRepo) {
	        this.cartRepo = cartRepo;
	        this.itemRepo = itemRepo;
	        this.variantRepo = variantRepo;
	        this.productRepo = productRepo;
	        this.orderRepo = orderRepo;
	        this.orderLineRepo = orderLineRepo;
	        this.cartToppingRepo = cartToppingRepo;
	        this.toppingRepo = toppingRepo;
	        this.orderToppingRepo = orderToppingRepo;
	        this.promoRepo = promoRepo;
	    }

	    @Transactional
	    public GioHang getOrCreateActiveCart(KhachHang kh) {
	        return cartRepo.findFirstByCustomerAndStatus(kh, "ACTIVE").orElseGet(() -> {
	            GioHang g = new GioHang();
	            g.setCustomer(kh);
	            g.setStatus("ACTIVE");
	            return cartRepo.save(g);
	        });
	    }

	    @Transactional
	    public GioHangCT addItemWithOptions(KhachHang kh, Integer variantId, int qty, Map<Integer,Integer> toppingQty, String note) {
	        if (qty <= 0) qty = 1;
	        GioHang cart = getOrCreateActiveCart(kh);
	        ProductVariant variant = resolveVariant(null, variantId);
	        if (variant == null) throw new IllegalArgumentException("Biến thể không hợp lệ.");
	        // Apply active product promotion to base
	        BigDecimal base = variant.getPrice();
	        Integer percent = (variant.getProduct() != null) ? promoRepo.findActiveMaxDiscountPercentForProduct(variant.getProduct().getId()) : null;
	        BigDecimal unitPrice = applyPercent(base, percent);
	        // Create a fresh line (do not merge when options differ)
	        GioHangCT line = new GioHangCT();
	        line.setCart(cart);
	        line.setVariant(variant);
	        line.setQuantity(qty);
	        line.setUnitPrice(unitPrice);
	        line.setNote(note);
	        // Compute toppings per drink and total
	        BigDecimal toppingPerDrink = BigDecimal.ZERO;
	        if (toppingQty != null) {
	            for (Map.Entry<Integer,Integer> e : toppingQty.entrySet()) {
	                Integer tid = e.getKey();
	                Integer perDrink = e.getValue();
	                if (perDrink == null || perDrink <= 0) continue;
	                Topping t = toppingRepo.findById(tid).orElse(null);
	                if (t == null) continue;
	                BigDecimal up = t.getExtraPrice();
	                toppingPerDrink = toppingPerDrink.add(up.multiply(BigDecimal.valueOf(perDrink)));
	            }
	        }
	        BigDecimal lineTotal = unitPrice.add(toppingPerDrink).multiply(BigDecimal.valueOf(qty));
	        line.setLineTotal(lineTotal);
	        line = itemRepo.save(line);
	        // Persist topping items as total quantity across line (perDrink * qty)
	        if (toppingQty != null) {
	            for (Map.Entry<Integer,Integer> e : toppingQty.entrySet()) {
	                Integer tid = e.getKey();
	                Integer perDrink = e.getValue();
	                if (perDrink == null || perDrink <= 0) continue;
	                Topping t = toppingRepo.findById(tid).orElse(null);
	                if (t == null) continue;
	                GioHangCTTopping ct = new GioHangCTTopping();
	                ct.setCartItem(line);
	                ct.setTopping(t);
	                ct.setQuantity(perDrink * qty);
	                ct.setUnitPrice(t.getExtraPrice());
	                ct.setLineTotal(t.getExtraPrice().multiply(BigDecimal.valueOf(ct.getQuantity())));
	                cartToppingRepo.save(ct);
	            }
	        }
	        return line;
	    }

	    private ProductVariant resolveVariant(Integer productId, Integer variantId) {
	        if (variantId != null) {
	            return variantRepo.findById(variantId).orElse(null);
	        }
	        return null;
	    }

	    private BigDecimal applyPercent(BigDecimal base, Integer percent) {
	        if (base == null) return null;
	        if (percent == null || percent <= 0) return base;
	        java.math.RoundingMode RM = java.math.RoundingMode.HALF_UP;
	        java.math.BigDecimal p = java.math.BigDecimal.valueOf(100 - Math.min(100, percent))
	                .divide(java.math.BigDecimal.valueOf(100), 4, RM);
	        return base.multiply(p).setScale(0, RM);
	    }

	    public List<GioHangCT> listItems(KhachHang kh) {
	        GioHang c = cartRepo.findFirstByCustomerAndStatus(kh, "ACTIVE").orElse(null);
	        if (c == null) return List.of();
	        return itemRepo.findByCart(c);
	    }

	    // --- New: total quantity of all items for badge ---
	    public int getItemCount(KhachHang kh) {
	        try {
	            return listItems(kh).stream().mapToInt(GioHangCT::getQuantity).sum();
	        } catch (Exception e) {
	            return 0;
	        }
	    }

	    @Transactional
	    public void updateQuantity(KhachHang kh, Integer itemId, int qty) {
	        GioHangCT item = itemRepo.findById(itemId).orElseThrow();
	        validateOwnership(kh, item);
	        if (qty <= 0) {
	            itemRepo.delete(item);
	            return;
	        }
	        item.setQuantity(qty);
	        // Recompute line total including toppings linked to this cart item
	        BigDecimal toppingTotal = cartToppingRepo.findAll().stream()
	                .filter(t -> t.getCartItem().getId().equals(item.getId()))
	                .map(GioHangCTTopping::getUnitPrice)
	                .reduce(BigDecimal.ZERO, BigDecimal::add);
	        // toppingTotal above is per-unit sum? We don't have perDrink; recompute from quantities instead
	        toppingTotal = cartToppingRepo.findAll().stream()
	                .filter(t -> t.getCartItem().getId().equals(item.getId()))
	                .map(t -> t.getUnitPrice().multiply(BigDecimal.valueOf(t.getQuantity())))
	                .reduce(BigDecimal.ZERO, BigDecimal::add);
	        // item total = base*qty + toppingTotal
	        BigDecimal newTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(qty)).add(toppingTotal);
	        item.setLineTotal(newTotal);
	        itemRepo.save(item);
	    }

	    @Transactional
	    public void removeItem(KhachHang kh, Integer itemId) {
	        GioHangCT item = itemRepo.findById(itemId).orElseThrow();
	        validateOwnership(kh, item);
	        itemRepo.delete(item);
	    }

	    private void validateOwnership(KhachHang kh, GioHangCT item) {
	        if (!Objects.equals(item.getCart().getCustomer().getId(), kh.getId())) {
	            throw new SecurityException("Không có quyền với mục giỏ hàng này");
	        }
	    }

	    public BigDecimal calcTotal(List<GioHangCT> items) {
	        return items.stream().map(GioHangCT::getLineTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	    }

	    @Transactional
	    public DonHang checkoutWithOptions(KhachHang kh, List<Integer> itemIds, String paymentMethod,
	                                       String note, String receivingMethod,
	                                       String shipName, String shipPhone, String shipAddress) {
	        if (itemIds == null || itemIds.isEmpty()) {
	            throw new IllegalArgumentException("Chưa chọn sản phẩm để đặt hàng");
	        }
	        GioHang activeCart = getOrCreateActiveCart(kh);
	        List<GioHangCT> items = itemRepo.findAllById(new HashSet<>(itemIds)).stream()
	                .filter(it -> it.getCart() != null && Objects.equals(it.getCart().getId(), activeCart.getId()))
	                .collect(Collectors.toList());
	        if (items.isEmpty()) {
	            throw new IllegalArgumentException("Không có sản phẩm hợp lệ để đặt hàng");
	        }
	        DonHang order = new DonHang();
	        order.setCustomer(kh);
	        order.setPaymentMethod(paymentMethod);
	        order.setReceivingMethod(receivingMethod);
	        // Build note and set receiver info when Ship
	        StringBuilder sb = new StringBuilder();
	        if (note != null && !note.isBlank()) sb.append(note.trim());
	        boolean isShip = "Ship".equalsIgnoreCase(receivingMethod);
	        if (isShip) {
	            String recvName = (shipName != null && !shipName.isBlank()) ? shipName.trim() : (kh.getFullName() != null ? kh.getFullName().trim() : null);
	            String recvPhone = (shipPhone != null && !shipPhone.isBlank()) ? shipPhone.trim() : (kh.getPhone() != null ? kh.getPhone().trim() : null);
	            String recvAddr = (shipAddress != null && !shipAddress.isBlank()) ? shipAddress.trim() : null;
	            // Validate minimal required fields for shipping
	            if (recvPhone == null || recvPhone.isBlank() || recvAddr == null || recvAddr.isBlank()) {
	                throw new IllegalArgumentException("Vui lòng nhập đầy đủ SĐT và Địa chỉ khi chọn Ship tận nơi");
	            }
	            order.setReceiverName(recvName);
	            order.setReceiverPhone(recvPhone);
	            order.setShippingAddress(recvAddr);
	            if (sb.length() > 0) sb.append(" | ");
	            sb.append("Ship to: ");
	            if (recvName != null && !recvName.isBlank()) sb.append(recvName).append(", ");
	            sb.append(recvPhone).append(", ").append(recvAddr);
	        } else {
	            // Pickup at store: clear receiver fields
	            order.setReceiverName(null);
	            order.setReceiverPhone(null);
	            order.setShippingAddress(null);
	        }
	        if (sb.length() > 0) order.setNote(sb.toString());
	        // Compute totals
	        BigDecimal tongHang = calcTotal(items);
	        order.setTongHang(tongHang);
	        order.setGiamGiaDon(BigDecimal.ZERO);
	        // Shipping fee rule: 0 for now
	        order.setPhiVanChuyen(BigDecimal.ZERO);
	        order.setTongThanhToan(tongHang.add(order.getPhiVanChuyen()).subtract(order.getGiamGiaDon()));
	        order = orderRepo.save(order);
	        // Persist lines and toppings
	        for (GioHangCT ci : items) {
	            CTDonHang ol = new CTDonHang();
	            ol.setOrder(order);
	            ol.setVariant(ci.getVariant());
	            ol.setQuantity(ci.getQuantity());
	            ol.setUnitPrice(ci.getUnitPrice());
	            ol.setLineDiscount(BigDecimal.ZERO);
	            ol.setLineTotal(ci.getLineTotal());
	            // copy sugar/ice note
	            ol.setNote(ci.getNote());
	            ol = orderLineRepo.save(ol);
	            for (GioHangCTTopping ct : cartToppingRepo.findByCartItem(ci)) {
	                CTDonHangTopping ot = new CTDonHangTopping();
	                ot.setOrderLine(ol);
	                ot.setTopping(ct.getTopping());
	                ot.setQuantity(ct.getQuantity());
	                ot.setUnitPrice(ct.getUnitPrice());
	                ot.setLineTotal(ct.getLineTotal());
	                orderToppingRepo.save(ot);
	            }
	        }
	        // Cleanup cart items
	        for (GioHangCT ci : items) {
	            for (GioHangCTTopping t : cartToppingRepo.findByCartItem(ci)) {
	                cartToppingRepo.delete(t);
	            }
	            itemRepo.delete(ci);
	        }
	        boolean noMoreItems = itemRepo.findByCart(activeCart).isEmpty();
	        if (noMoreItems) {
	            activeCart.setStatus("CHECKED_OUT");
	            cartRepo.save(activeCart);
	            getOrCreateActiveCart(kh);
	        }
	        return order;
	    }

	    // List all active toppings for UI
	    public List<Topping> listActiveToppings() {
	        return toppingRepo.findByDeletedAtIsNull();
	    }

	    // For the cart page: map itemId -> toppings on that item
	    public Map<Integer, List<GioHangCTTopping>> getToppingsForItems(List<GioHangCT> items) {
	        Map<Integer, List<GioHangCTTopping>> map = new HashMap<>();
	        for (GioHangCT it : items) {
	            map.put(it.getId(), cartToppingRepo.findByCartItem(it));
	        }
	        return map;
	    }

	    @Transactional
	    public void updateToppings(KhachHang kh, Integer itemId, Map<Integer,Integer> toppingQtyById) {
	        GioHangCT item = itemRepo.findById(itemId).orElseThrow();
	        validateOwnership(kh, item);
	        // Existing toppings for this line
	        List<GioHangCTTopping> existing = cartToppingRepo.findByCartItem(item);
	        Map<Integer, GioHangCTTopping> existingByTid = existing.stream()
	                .collect(Collectors.toMap(t -> t.getTopping().getId(), t -> t));
	        // Upsert or delete based on incoming map
	        if (toppingQtyById != null) {
	            for (Map.Entry<Integer,Integer> e : toppingQtyById.entrySet()) {
	                Integer tid = e.getKey();
	                Integer qty = e.getValue() == null ? 0 : Math.max(0, e.getValue());
	                Topping topping = toppingRepo.findById(tid).orElse(null);
	                if (topping == null) continue;
	                if (qty == 0) {
	                    GioHangCTTopping exist = existingByTid.get(tid);
	                    if (exist != null) cartToppingRepo.delete(exist);
	                } else {
	                    GioHangCTTopping exist = existingByTid.get(tid);
	                    if (exist == null) {
	                        exist = new GioHangCTTopping();
	                        exist.setCartItem(item);
	                        exist.setTopping(topping);
	                    }
	                    exist.setQuantity(qty);
	                    exist.setUnitPrice(topping.getExtraPrice());
	                    exist.setLineTotal(topping.getExtraPrice().multiply(BigDecimal.valueOf(qty)));
	                    cartToppingRepo.save(exist);
	                }
	            }
	        }
	        recomputeLineTotal(item);
	    }

	    private void recomputeLineTotal(GioHangCT item) {
	        // base*qty + sum(all topping line totals for this item)
	        BigDecimal toppingSum = cartToppingRepo.findByCartItem(item).stream()
	                .map(GioHangCTTopping::getLineTotal)
	                .reduce(BigDecimal.ZERO, BigDecimal::add);
	        BigDecimal base = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
	        item.setLineTotal(base.add(toppingSum));
	        itemRepo.save(item);
	    }

	    // === New: change variant (size) of a cart item ===
	    @Transactional
	    public void changeVariant(KhachHang kh, Integer itemId, Integer newVariantId) {
	        GioHangCT item = itemRepo.findById(itemId).orElseThrow();
	        validateOwnership(kh, item);
	        ProductVariant target = variantRepo.findById(newVariantId).orElse(null);
	        if (target == null || target.getStatus() != null && target.getStatus() == 0) {
	            throw new IllegalArgumentException("Biến thể không hợp lệ hoặc đang ngừng bán");
	        }
	        // Allow changing only within the same product
	        Integer curProductId = item.getVariant().getProduct().getId();
	        Integer targetProductId = target.getProduct().getId();
	        if (!Objects.equals(curProductId, targetProductId)) {
	            throw new IllegalArgumentException("Không thể đổi sang sản phẩm khác");
	        }
	        // Update unit price (apply active promotion) and variant
	        Integer percent = promoRepo.findActiveMaxDiscountPercentForProduct(targetProductId);
	        BigDecimal newUnitPrice = applyPercent(target.getPrice(), percent);
	        item.setVariant(target);
	        item.setUnitPrice(newUnitPrice);
	        // Recompute total
	        recomputeLineTotal(item);
	    }

	    // Expose variant list for a product (used to render size options in cart)
	    public List<ProductVariant> listVariantsForProduct(Product product) {
	        if (product == null) return List.of();
	        return variantRepo.findByProduct(product);
	    }
	}
