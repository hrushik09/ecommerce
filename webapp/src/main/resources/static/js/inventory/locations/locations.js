document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (pageNo) => ({
        pageNo: pageNo,
        pageBaseUrl: "/inventory/locations",
        pagedResult: {
            data: []
        },
        init() {
            this.loadLocations(this.pageNo);
        },
        loadLocations(pageNo) {
            $.getJSON("/api/inventory/locations?page=" + pageNo, (resp) => {
                this.pagedResult = resp;
            });
        },
    }));
});