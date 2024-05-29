document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (pageNo) => ({
        pageNo: pageNo,
        pageBaseUrl: "/catalog/countries",
        pagedResult: {
            data: []
        },
        init() {
            this.loadCountries(this.pageNo);
        },
        loadCountries(pageNo) {
            $.getJSON("/api/catalog/countries?page=" + pageNo, (resp) => {
                this.pagedResult = resp;
            });
        },
    }));
});