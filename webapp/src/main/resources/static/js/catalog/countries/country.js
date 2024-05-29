document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (code) => ({
        code: code,
        country: {
            code: null,
            name: null,
            createdAt: null,
            updatedAt: null,
        },
        init() {
            this.loadCountry(this.code);
        },
        loadCountry(code) {
            $.getJSON("/api/catalog/countries/" + code, (resp) => {
                this.country = resp;
            });
        },
    }));
});