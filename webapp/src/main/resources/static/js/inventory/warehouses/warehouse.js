document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (code, pageNo) => ({
        code: code,
        pageNo: pageNo,
        warehouse: {
            code: null,
            name: null,
            isRefrigerated: null,
            createdAt: null,
            updatedAt: null,
        },
        pagedResult: {
            data: []
        },
        childBaseUrl: "/inventory/warehouses/" + code,
        init() {
            this.loadWarehouse(this.code);
            this.loadInventoryItems(this.code, this.pageNo);
        },
        loadWarehouse(code) {
            $.getJSON("/api/inventory/warehouses/" + code, (resp) => {
                this.warehouse = resp;
            });
        },
        loadInventoryItems(code, pageNo) {
            $.getJSON("/api/inventory/warehouses/" + code + "/items?page=" + pageNo, (resp) => {
                this.pagedResult = resp;
            });
        }
    }));
});