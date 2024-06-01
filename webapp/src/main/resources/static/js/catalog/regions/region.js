document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (code) => ({
        code: code,
        region: {
            code: null,
            name: null,
            createdAt: null,
            updatedAt: null,
        },
        init() {
            this.loadRegion(this.code);
        },
        loadRegion(code) {
            $.getJSON("/api/catalog/regions/" + code, (resp) => {
                this.region = resp;
            });
        },
    }));
});