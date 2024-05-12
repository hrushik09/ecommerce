document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (code) => ({
        code: code,
        location: {
            code: null,
            name: null,
            address: null,
            createdAt: null,
            updatedAt: null,
        },
        pageBaseUrl: "/inventory/locations",
        init() {
            this.loadLocation(this.code);
        },
        loadLocation(code) {
            $.getJSON("/api/inventory/locations/" + code, (resp) => {
                this.location = resp;
            });
        },
    }));
});