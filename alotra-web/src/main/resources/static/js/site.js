// Navbar shadow when scrolling (guard if navbar not present)
(function () {
  const nav = document.querySelector('.navbar');
  const setStuck = () => {
    if (!nav) return;
    if (window.scrollY > 10) nav.classList.add('stuck');
    else nav.classList.remove('stuck');
  };
  document.addEventListener('scroll', setStuck);
  setStuck();
})();

// Mega menu: hover on desktop, click on mobile
(function () {
  const lg = 992; // Bootstrap lg
  function enableHoverDropdown() {
    document.querySelectorAll('.dropdown-mega').forEach(dd => {
      const link = dd.querySelector('[data-bs-toggle="dropdown"]');
      if (!link) return;

      dd.onmouseenter = dd.onmouseleave = null;

      if (window.innerWidth >= lg) {
        dd.onmouseenter = () => bootstrap.Dropdown.getOrCreateInstance(link).show();
        dd.onmouseleave = () => bootstrap.Dropdown.getOrCreateInstance(link).hide();
      }
    });
  }
  document.addEventListener('DOMContentLoaded', enableHoverDropdown);
  window.addEventListener('resize', enableHoverDropdown);

  // Close dropdown when clicking anchor inside it
  document.addEventListener('click', (e) => {
    const a = e.target.closest('.dropdown-menu a[href^="#"]');
    if (!a) return;
    const wrapper = a.closest('.dropdown');
    const toggler = wrapper?.querySelector('[data-bs-toggle="dropdown"]');
    if (toggler) bootstrap.Dropdown.getOrCreateInstance(toggler).hide();
  });
})();

// NhanVien > SanPham > Form: thêm biến thể (external script to avoid CSP blocking inline JS)
(function () {
  // Polyfill replaceAll
  if (typeof String.prototype.replaceAll !== 'function') {
    // eslint-disable-next-line no-extend-native
    String.prototype.replaceAll = function (search, replacement) {
      return this.split(search).join(replacement);
    };
  }

  const VariantForm = {
    variantIndex: 0,
    getTemplateNode() {
      const divItem = document.querySelector('#variantTemplate .variant-item');
      if (divItem) return { node: divItem, isTemplateTag: false };
      const tplEl = document.getElementById('variantTemplate');
      if (tplEl && tplEl.tagName && tplEl.tagName.toUpperCase() === 'TEMPLATE') {
        const item = tplEl.content?.querySelector('.variant-item');
        if (item) return { node: item, isTemplateTag: true };
      }
      return null;
    },
    init() {
      const container = document.getElementById('variantsContainer');
      const btn = document.getElementById('btnAddVariant');
      if (!container || !btn) return; // not on this page

      // Initialize index from existing variants
      this.variantIndex = container.querySelectorAll('.variant-item').length;

      // Add variant button
      btn.addEventListener('click', (e) => {
        e.preventDefault();
        this.addVariant();
      });

      // Delegate remove click if button has class .btn-remove-variant
      container.addEventListener('click', (e) => {
        const btnRemove = e.target.closest('.btn-remove-variant');
        if (btnRemove) {
          e.preventDefault();
          const item = btnRemove.closest('.variant-item');
          if (item) item.remove();
        }
      });

      // Update size name on change
      container.addEventListener('change', (e) => {
        const select = e.target.closest('select');
        if (!select) return;
        const name = select.getAttribute('name') || '';
        if (!name.startsWith('bienThes')) return;

        const opt = select.options[select.selectedIndex];
        const hidden = select.parentNode.querySelector('input[type="hidden"]');
        if (hidden) hidden.value = opt ? opt.text : '';
        const title = select.closest('.variant-item')?.querySelector('h6');
        if (title) title.textContent = opt && opt.text ? `Size ${opt.text}` : 'Biến thể mới';
      });
    },
    addVariant() {
      const tpl = this.getTemplateNode();
      const container = document.getElementById('variantsContainer');
      if (!tpl || !container) {
        alert('Không tìm thấy mẫu biến thể. Vui lòng tải lại trang.');
        return;
      }
      const node = tpl.node.cloneNode(true);
      node.innerHTML = node.innerHTML.replaceAll('__INDEX__', this.variantIndex);
      // Ensure fields are enabled and associated with the main form
      node.querySelectorAll('input, select, textarea').forEach(el => {
        el.disabled = false;
        el.removeAttribute('data-template');
        el.setAttribute('form', 'productForm');
      });
      container.appendChild(node);
      console.debug('Đã thêm biến thể, index =', this.variantIndex);
      this.variantIndex++;
    }
  };

  document.addEventListener('DOMContentLoaded', () => VariantForm.init());
})();