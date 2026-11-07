// Applied immediately (before DOMContentLoaded) so the page never flashes
// light mode before switching to dark.
(function () {
    const saved = localStorage.getItem("serviceshub-theme");
    if (saved) {
        document.documentElement.setAttribute("data-theme", saved);
    }
})();

document.addEventListener("DOMContentLoaded", function () {
    const toggle = document.getElementById("theme-toggle");
    if (!toggle) return;

    function currentTheme() {
        const stored = localStorage.getItem("serviceshub-theme");
        if (stored) return stored;
        return window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
    }

    function applyToggleLabel() {
        const isDark = currentTheme() === "dark";
        toggle.textContent = isDark ? "☀️" : "🌙";
        toggle.setAttribute("aria-label", isDark ? "Switch to light mode" : "Switch to dark mode");
    }

    toggle.addEventListener("click", function () {
        const next = currentTheme() === "dark" ? "light" : "dark";
        document.documentElement.setAttribute("data-theme", next);
        localStorage.setItem("serviceshub-theme", next);
        applyToggleLabel();
    });

    applyToggleLabel();
});
