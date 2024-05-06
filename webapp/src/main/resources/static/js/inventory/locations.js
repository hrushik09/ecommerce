document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (pageNo) => ({
        pageNo: pageNo,
        locations: {
            data: []
        },
        init() {
            this.loadLocations(this.pageNo);
        },
        loadLocations: (pageNo) => {
            $.getJSON("/api/inventory/locations?page=" + pageNo, (resp) => {
                console.log("locations resp ", resp);
                this.locations = resp;
            });
        },
    }));
});