document.addEventListener('DOMContentLoaded', () => {
    const sidebar = document.getElementById('sidebar');
    const toggler = document.getElementById('sidebar-toggler');
    const overlay = document.getElementById('overlay');

    if (toggler && sidebar && overlay) {
        toggler.addEventListener('click', () => {
            sidebar.classList.toggle('show');
            overlay.classList.toggle('show');
        });

        overlay.addEventListener('click', () => {
            sidebar.classList.remove('show');
            overlay.classList.remove('show');
        });
    }

    // Close sidebar when clicking a nav link on mobile
    const navLinks = document.querySelectorAll('.sidebar .nav-link');
    navLinks.forEach(link => {
        link.addEventListener('click', () => {
            if (window.innerWidth < 992) {
                sidebar.classList.remove('show');
                overlay.classList.remove('show');
            }
        });
    });

    // Accessibility Logic
    const body = document.body;
    const btnContrast = document.getElementById('toggle-contrast');
    const btnSm = document.getElementById('font-sm');
    const btnMd = document.getElementById('font-md');
    const btnLg = document.getElementById('font-lg');

    // Persistence
    const savedContrast = localStorage.getItem('accessibility-contrast');
    const savedFontSize = localStorage.getItem('accessibility-font');

    if (savedContrast === 'true') body.classList.add('high-contrast');
    if (savedFontSize) body.classList.add(savedFontSize);

    btnContrast?.addEventListener('click', () => {
        body.classList.toggle('high-contrast');
        localStorage.setItem('accessibility-contrast', body.classList.contains('high-contrast'));
    });

    const setFontSize = (size) => {
        body.classList.remove('font-size-md', 'font-size-lg');
        if (size) body.classList.add(size);
        localStorage.setItem('accessibility-font', size);
    };

    btnSm?.addEventListener('click', () => setFontSize(''));
    btnMd?.addEventListener('click', () => setFontSize('font-size-md'));
    btnLg?.addEventListener('click', () => setFontSize('font-size-lg'));
});
