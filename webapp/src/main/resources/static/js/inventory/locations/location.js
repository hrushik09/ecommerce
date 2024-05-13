document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (code, pageNo) => ({
        code: code,
        pageNo: pageNo,
        location: {
            code: null,
            name: null,
            address: null,
            createdAt: null,
            updatedAt: null,
        },
        pagedResult: {
            data: []
        },
        pageBaseUrl: "/inventory/locations",
        childBaseUrl: "/inventory/locations/" + code,
        warehouseBaseUrl: "/inventory/warehouses",
        init() {
            this.loadLocation(this.code);
            this.loadWarehouses(this.code, this.pageNo);
        },
        loadLocation(code) {
            $.getJSON("/api/inventory/locations/" + code, (resp) => {
                this.location = resp;
            });
        },
        loadWarehouses(code, pageNo) {
            $.getJSON("/api/inventory/locations/" + code + "/warehouses?page=" + pageNo, (resp) => {
                this.pagedResult = resp;
            });
        },
    }));
});