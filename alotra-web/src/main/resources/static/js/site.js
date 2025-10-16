// Navbar shadow when scrolling
(function () {
  const nav = document.querySelector('.navbar');
  const setStuck = () => window.scrollY > 10 ? nav.classList.add('stuck') : nav.classList.remove('stuck');
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