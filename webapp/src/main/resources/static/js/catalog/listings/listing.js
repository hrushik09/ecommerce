document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (code) => ({
        code: code,
        listing: {
            productCode: null,
            code: null,
            title: null,
            description: null,
            price: null,
            currency: null,
            createdAt: null,
            updatedAt: null,
        },
        init() {
            this.loadListing(this.code);
        },
        loadListing(code) {
            $.getJSON("/api/catalog/listings/" + code, (resp) => {
                this.listing = resp;
            });
        },
    }));
});