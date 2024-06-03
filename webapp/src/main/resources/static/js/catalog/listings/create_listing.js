document.addEventListener("alpine:init", () => {
    Alpine.data("initData", (regionCode) => ({
        regionCode: regionCode,
        listing: {
            productCode: null,
            regionCode: regionCode,
            title: null,
            description: null,
            price: null,
            currency: null,
        },
        products: [],
        createListingErrorDetail: null,
        init() {
            this.getProducts();
        },
        getProducts() {
            $.getJSON("/api/inventory/products/all", (resp) => {
                this.products = resp;
            });
        },
        createListing() {
            $.ajax({
                url: "/api/catalog/listings",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(this.listing),
                success: (response) => {
                    this.createListingErrorDetail = null;
                    window.location = "/catalog/listings/" + response.code;
                },
                error: (err) => {
                    let responseText = JSON.parse(err.responseText);
                    this.createListingErrorDetail = responseText.detail;
                },
            });
        },
    }));
});