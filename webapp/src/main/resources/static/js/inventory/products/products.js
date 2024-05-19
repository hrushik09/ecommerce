document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (pageNo) => ({
        pageNo: pageNo,
        pageBaseUrl: "/inventory/products",
        pagedResult: {
            data: []
        },
        init() {
            this.loadProducts(this.pageNo);
        },
        loadProducts(pageNo) {
            $.getJSON("/api/inventory/products?page=" + pageNo, (resp) => {
                this.pagedResult = resp;
            });
        },
    }));
});