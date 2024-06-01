document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (code, pageNo) => ({
        code: code,
        pageNo: pageNo,
        country: {
            code: null,
            name: null,
            createdAt: null,
            updatedAt: null,
        },
        pagedResult: {
            data: []
        },
        childBaseUrl: "/catalog/countries/" + code,
        regionBaseUrl: "/catalog/regions",
        init() {
            this.loadCountry(this.code);
            this.loadRegions(this.code, this.pageNo);
        },
        loadCountry(code) {
            $.getJSON("/api/catalog/countries/" + code, (resp) => {
                this.country = resp;
            });
        },
        loadRegions(code, pageNo) {
            $.getJSON("/api/catalog/countries/" + code + "/regions?page=" + pageNo, (resp) => {
                this.pagedResult = resp;
            });
        },
    }));
});