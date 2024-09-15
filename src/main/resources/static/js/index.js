document.addEventListener("DOMContentLoaded", function () {
    if (window.location.pathname === "/homepage" || window.location.pathname === "/") {
        console.log("Clearing sessionStorage on homepage.");
        sessionStorage.clear();
    }
});
