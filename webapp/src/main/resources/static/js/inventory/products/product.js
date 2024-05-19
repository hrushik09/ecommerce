document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (code) => ({
        code: code,
        product: {
            code: null,
            name: null,
            description: null,
            category: null,
            reorderQuantity: null,
            needsRefrigeration: false,
            measurement: {
                packedWeight: {
                    value: null,
                    unit: null,
                },
                packedLength: {
                    value: null,
                    unit: null,
                },
                packedWidth: {
                    value: null,
                    unit: null,
                },
                packedHeight: {
                    value: null,
                    unit: null,
                },
            },
            createdAt: null,
            updatedAt: null,
        },
        init() {
            this.loadProduct(this.code);
        },
        loadProduct(code) {
            $.getJSON("/api/inventory/products/" + code, (resp) => {
                this.product = resp;
            });
        },
    }));
});