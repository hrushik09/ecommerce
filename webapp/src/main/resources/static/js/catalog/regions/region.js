document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (code, pageNo) => ({
        code: code,
        pageNo: pageNo,
        region: {
            code: null,
            name: null,
            createdAt: null,
            updatedAt: null,
        },
        pagedResult: {
            data: []
        },
        childBaseUrl: "/catalog/regions/" + code,
        listingBaseUrl: "/catalog/listings",
        init() {
            this.loadRegion(this.code);
            this.loadListings(this.code, this.pageNo);
        },
        loadRegion(code) {
            $.getJSON("/api/catalog/regions/" + code, (resp) => {
                this.region = resp;
            });
        },
        loadListings(code, pageNo) {
            $.getJSON("/api/catalog/listings?regionCode=" + code + "&page=" + pageNo, (resp) => {
                this.pagedResult = resp;
            });
        },
    }));
});