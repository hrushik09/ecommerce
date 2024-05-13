document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (code) => ({
        code: code,
        warehouse: {
            code: null,
            name: null,
            isRefrigerated: null,
            createdAt: null,
            updatedAt: null,
        },
        init() {
            this.loadWarehouse(this.code);
        },
        loadWarehouse(code) {
            $.getJSON("/api/inventory/warehouses/" + code, (resp) => {
                this.warehouse = resp;
            });
        },
    }));
});